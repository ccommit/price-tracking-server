package com.ccommit.price_tracking_server.service;

import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserProfileResponseDTO;
import com.ccommit.price_tracking_server.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserProfileResponseDTO registerUser(UserDTO userDTO);

    UserLoginResponseDTO loginUser(UserDTO userDTO);

    UserProfileResponseDTO updateUser(String email, UserDTO userDTO);

    User getUserById(Long userId);

    Boolean checkNickname(String nickname);

    Boolean logoutUser(String email);

    Boolean deleteUser(String email);
}
