package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserSignUpDTO;
import com.ccommit.price_tracking_server.entity.User;
import com.ccommit.price_tracking_server.mapper.UserMapper;
import com.ccommit.price_tracking_server.repository.UserRepository;
import com.ccommit.price_tracking_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserSignUpDTO registerUser(UserDTO userDTO) {
        User user = userMapper.convertToEntity(userDTO);
        userRepository.save(user);
        UserSignUpDTO userSignUpDTO = userMapper.convertToSignUpDTO(user);
        return userSignUpDTO;
    }
}
