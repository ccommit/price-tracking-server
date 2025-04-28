package com.ccommit.price_tracking_server.mapper;


import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserProfileResponseDTO;
import com.ccommit.price_tracking_server.entity.User;
import com.ccommit.price_tracking_server.utils.BcryptEncrypt;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    private final BcryptEncrypt bcryptEncrypt;

    public User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(bcryptEncrypt.encrypt(userDTO.getPassword()));
        return user;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public UserProfileResponseDTO convertToProfileDTO(User user) {
        UserProfileResponseDTO userProfileResponseDTO = modelMapper.map(user, UserProfileResponseDTO.class);
        return userProfileResponseDTO;
    }

    public UserLoginResponseDTO convertToLoginDTO(String accessToken, String refreshToken) {
        UserLoginResponseDTO userLoginResponseDTO = UserLoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return userLoginResponseDTO;
    }
}
