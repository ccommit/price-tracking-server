package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserSignUpDTO;
import com.ccommit.price_tracking_server.entity.User;
import com.ccommit.price_tracking_server.exception.PasswordMismatchException;
import com.ccommit.price_tracking_server.mapper.UserMapper;
import com.ccommit.price_tracking_server.repository.UserRepository;
import com.ccommit.price_tracking_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserSignUpDTO registerUser(UserDTO userDTO) {
        try {
            log.info("회원가입 시도: username={}, email={}", userDTO.getUsername(), userDTO.getEmail());
            if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                log.warn("비밀번호 불일치: username={}, email={}", userDTO.getUsername(), userDTO.getEmail());
                throw new PasswordMismatchException();
            }

            User user = userMapper.convertToEntity(userDTO);
            log.debug("User 변환 완료: {}", user);

            userRepository.save(user);
            log.info("회원 저장 완료: userId={}, username={}", user.getId(), user.getUsername());

            UserSignUpDTO userSignUpDTO = userMapper.convertToSignUpDTO(user);
            return userSignUpDTO;
        } catch (PasswordMismatchException ex) {
            log.error("비밀번호 불일치 예외 발생: username={}, message={}", userDTO.getUsername(), ex.getMessage());
            throw ex; // 예외를 다시 던져서 상위 레이어에서 처리
        } catch (Exception ex) {
            log.error("회원가입 처리 중 오류 발생: username={}, message={}", userDTO.getUsername(), ex.getMessage(), ex);
            throw new RuntimeException("회원가입 중 오류가 발생했습니다.", ex);
        }
    }
}
