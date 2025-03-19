package com.ccommit.price_tracking_server.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(groups = {SignUp.class})
    @Size(min = 3, max = 50)
    private String username;

    @NotNull(groups = {SignUp.class, Login.class})
    @Email
    private String email;

    @NotNull(groups = {SignUp.class, Login.class})
    @Size(min = 8, max = 100)
    private String password;

    @NotNull(groups = {SignUp.class})
    @Size(min = 8, max = 100)
    private String confirmPassword;

    @NotNull(groups = {SignUp.class})
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    private String phone;

    public interface SignUp {
    }

    public interface Login {
    }
}
