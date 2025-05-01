package com.ccommit.price_tracking_server.service;

import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponse;
import com.ccommit.price_tracking_server.DTO.UserProfileResponse;
import com.ccommit.price_tracking_server.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserProfileResponse registerUser(UserDTO userDTO);

    UserLoginResponse loginUser(UserDTO userDTO);

    UserProfileResponse updateUser(String email, UserDTO userDTO);

    User getUserById(Long userId);

    Boolean checkNickname(String nickname);

    Boolean logoutUser(String email);

    Boolean deleteUser(String email);
}
