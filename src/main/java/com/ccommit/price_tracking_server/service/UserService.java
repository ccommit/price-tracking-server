package com.ccommit.price_tracking_server.service;

import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserSignUpDTO;

public interface UserService {
    UserSignUpDTO registerUser(UserDTO userDTO);

}
