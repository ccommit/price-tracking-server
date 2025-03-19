package com.ccommit.price_tracking_server.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDTO {

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @Email
    private String email;
}

