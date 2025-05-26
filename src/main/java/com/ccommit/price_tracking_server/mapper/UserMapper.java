package com.ccommit.price_tracking_server.mapper;


import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponse;
import com.ccommit.price_tracking_server.DTO.UserProfileResponse;
import com.ccommit.price_tracking_server.entity.User;
import com.ccommit.price_tracking_server.enums.UserStatus;
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
        String encryptedPassword = bcryptEncrypt.encrypt(userDTO.getPassword());
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(encryptedPassword)
                .phone(userDTO.getPhone())
                .status(UserStatus.ACTIVE)
                .emailVerified(false)
                .build();
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
