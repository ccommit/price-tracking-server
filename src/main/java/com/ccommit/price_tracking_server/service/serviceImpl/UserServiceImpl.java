package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponse;
import com.ccommit.price_tracking_server.DTO.UserProfileResponse;
import com.ccommit.price_tracking_server.entity.User;
import com.ccommit.price_tracking_server.enums.UserStatus;
import com.ccommit.price_tracking_server.exception.*;
import com.ccommit.price_tracking_server.mapper.UserMapper;
import com.ccommit.price_tracking_server.repository.UserRepository;
import com.ccommit.price_tracking_server.security.JwtTokenProvider;
import com.ccommit.price_tracking_server.service.UserService;
import com.ccommit.price_tracking_server.utils.BcryptEncrypt;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BcryptEncrypt bcryptEncrypt;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public UserProfileResponse registerUser(UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            log.warn("비밀번호 불일치: username={}, email={}", userDTO.getUsername(), userDTO.getEmail());
            throw new PasswordMismatchException();
        }

        User user = userMapper.convertToEntity(userDTO);
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateEmailException();
        }
        userRepository.save(user);
        log.info("회원 저장 완료: userId={}, username={}", user.getId(), user.getUsername());

        return userMapper.convertToProfile(user);
    }

    @Override
    @Transactional
    public UserLoginResponse loginUser(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (user.getStatus() == UserStatus.INACTIVE) {
            log.warn("로그인 실패: 이상 유저 - email={}", userDTO.getEmail());
            throw new UserAccountDisableException();
        } else if (!bcryptEncrypt.matches(userDTO.getPassword(), user.getPassword())) {
            log.warn("로그인 실패: 비밀번호 불일치 - email={}", userDTO.getEmail());
            throw new InvalidPasswordException();
        }

        log.info("로그인 성공: userId={}, username={}", user.getId(), user.getUsername());

        // JWT 발급
        String accessToken = JwtTokenProvider.generateAccessToken(user.getEmail(), user.getStatus());
        String refreshToken = JwtTokenProvider.generateRefreshToken(user.getId());

        String key = "refresh_token:" + user.getId();
        // Refresh Token을 Redis에 저장 (유효기간 30일)
        long expirationTimeInMillis = 30L * 24 * 60 * 60 * 1000; // 30일 후 밀리초로 계산
        Duration duration = Duration.ofMillis(expirationTimeInMillis);
        redisTemplate.opsForValue().set(key, refreshToken, duration);

        return userMapper.convertToLoginDTO(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUser(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        log.debug("User 조회 완료: email={}", user.getEmail());
        user.updateProfile(userDTO.getPhone(), userDTO.getUsername());

        userRepository.save(user);
        log.info("회원 정보 수정 완료: userId={}, username={}", user.getId(), user.getUsername());

        return userMapper.convertToProfile(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Boolean checkNickname(String nickname) {
        Boolean isExist = userRepository.existsByUsername(nickname);
        if (isExist) {
            throw new UserNameTakenException();
        }
        return true;
    }

    @Override
    public Boolean logoutUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        String key = "refresh_token:" + user.getId();
        Boolean deleted = redisTemplate.delete(key);
        if (!deleted) {
            log.warn("로그아웃 실패: Refresh Token 삭제 실패 - userId={}", user.getId());
            throw new TokenNotFoundException();
        }
        log.info("로그아웃 성공: userId={}, username={}", user.getId(), user.getUsername());
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        log.debug("User 조회 완료: email={}", user.getEmail());

        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        log.info("회원 탈퇴 완료: userId={}, username={}", user.getId(), user.getUsername());
        this.logoutUser(email); // 로그아웃 처리, Redis에서 Refresh Token 삭제
        return true;
    }
}
