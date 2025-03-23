package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserProfileResponseDTO;
import com.ccommit.price_tracking_server.entity.User;
import com.ccommit.price_tracking_server.enums.UserStatus;
import com.ccommit.price_tracking_server.exception.InvalidPasswordException;
import com.ccommit.price_tracking_server.exception.PasswordMismatchException;
import com.ccommit.price_tracking_server.exception.UserAccountDisableException;
import com.ccommit.price_tracking_server.exception.UserNotFoundException;
import com.ccommit.price_tracking_server.mapper.UserMapper;
import com.ccommit.price_tracking_server.repository.UserRepository;
import com.ccommit.price_tracking_server.service.UserService;
import com.ccommit.price_tracking_server.utils.BcryptEncrypt;
import com.ccommit.price_tracking_server.utils.JwtTokenProvider;
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
    public UserProfileResponseDTO registerUser(UserDTO userDTO) {
        log.info("회원가입 시도: username={}, email={}", userDTO.getUsername(), userDTO.getEmail());
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            log.warn("비밀번호 불일치: username={}, email={}", userDTO.getUsername(), userDTO.getEmail());
            throw new PasswordMismatchException();
        }

        User user = userMapper.convertToEntity(userDTO);
        log.debug("User 변환 완료: {}", user);

        userRepository.save(user);
        log.info("회원 저장 완료: userId={}, username={}", user.getId(), user.getUsername());

        return userMapper.convertToProfileDTO(user);
    }

    @Override
    public UserLoginResponseDTO loginUser(UserDTO userDTO) {
        log.info("로그인 시도: email={}", userDTO.getEmail());

        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(UserNotFoundException::new);
        log.debug("User 조회 완료: email={}", user.getEmail());

        if (user.getStatus() == UserStatus.INACTIVE) {
            log.warn("로그인 실패: 이상 유저 - email={}", userDTO.getEmail());
            throw new UserAccountDisableException();
        } else if (!bcryptEncrypt.matches(userDTO.getPassword(), user.getPassword())) {
            log.warn("로그인 실패: 비밀번호 불일치 - email={}", userDTO.getEmail());
            throw new InvalidPasswordException();
        }

        log.info("로그인 성공: userId={}, username={}", user.getId(), user.getUsername());

        // JWT 발급
        String accessToken = JwtTokenProvider.generateAccessToken(user.getId(), user.getStatus());
        String refreshToken = JwtTokenProvider.generateRefreshToken(user.getId());

        String key = "refresh_token:" + user.getId();
        // Refresh Token을 Redis에 저장 (유효기간 7일)
        long expirationTimeInMillis = 7L * 24 * 60 * 60 * 1000; // 7일 후 밀리초로 계산
        Duration duration = Duration.ofMillis(expirationTimeInMillis);
        redisTemplate.opsForValue().set(key, refreshToken, duration);

        return userMapper.convertToLoginDTO(accessToken, refreshToken);
    }

}
