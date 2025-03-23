package com.ccommit.price_tracking_server.service;

import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserProfileResponseDTO;

public interface UserService {
    UserProfileResponseDTO registerUser(UserDTO userDTO);

    UserLoginResponseDTO loginUser(UserDTO userDTO);
}
