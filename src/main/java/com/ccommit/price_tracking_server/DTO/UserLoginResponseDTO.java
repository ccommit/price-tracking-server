package com.ccommit.price_tracking_server.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDTO {
    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;
}