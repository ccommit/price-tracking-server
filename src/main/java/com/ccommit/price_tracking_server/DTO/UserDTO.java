package com.ccommit.price_tracking_server.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Size(min = 4, max = 20, groups = {SignUp.class, Update.class})
    @NotBlank(groups = {SignUp.class, Update.class})
    private String username;

    @Email(regexp = "^[A-Za-z0-9+_.-]{5,}@[A-Za-z0-9.-]{3,}\\.[A-Za-z]{2,}$",
            groups = {SignUp.class, Login.class})
    @NotBlank(groups = {SignUp.class, Login.class})
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?/\\\\|-]).{8,}$",
            groups = {SignUp.class, Login.class})
    @NotBlank(groups = {SignUp.class, Login.class})
    private String password;

    @Size(min = 8, max = 100, groups = {SignUp.class})
    @NotBlank(groups = {SignUp.class})
    private String confirmPassword;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", groups = {SignUp.class, Update.class})
    @NotBlank(groups = {SignUp.class, Update.class})
    private String phone;

    public interface SignUp {
    }

    public interface Login {
    }

    public interface Update {
    }
}
