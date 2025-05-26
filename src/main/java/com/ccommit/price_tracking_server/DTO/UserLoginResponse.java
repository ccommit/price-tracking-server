package com.ccommit.price_tracking_server.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;
}