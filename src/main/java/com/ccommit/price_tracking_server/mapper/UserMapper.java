package com.ccommit.price_tracking_server.mapper;


import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserSignUpDTO;
import com.ccommit.price_tracking_server.entity.User;
import com.ccommit.price_tracking_server.utils.Sha256Encrypt;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    public User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(Sha256Encrypt.encrypt(userDTO.getPassword()));
        return user;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
    public UserSignUpDTO convertToSignUpDTO(User user) {
        UserSignUpDTO userSignUpDTO = modelMapper.map(user, UserSignUpDTO.class);
        return userSignUpDTO;
    }
}
