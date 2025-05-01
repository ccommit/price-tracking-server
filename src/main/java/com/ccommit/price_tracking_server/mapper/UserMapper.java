package com.ccommit.price_tracking_server.mapper;


import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponse;
import com.ccommit.price_tracking_server.DTO.UserProfileResponse;
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
        return modelMapper.map(user, UserDTO.class);
    }

    public UserProfileResponse convertToProfile(User user) {
        return modelMapper.map(user, UserProfileResponse.class);
    }

    public UserLoginResponse convertToLoginDTO(String accessToken, String refreshToken) {
        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
