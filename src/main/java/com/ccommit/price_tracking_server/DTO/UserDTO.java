package com.ccommit.price_tracking_server.DTO;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(groups = {SignUp.class})
    @Size(min = 4, max = 20, groups = {SignUp.class})
    @NotBlank(groups = {SignUp.class})
    private String username;

    @NotNull(groups = {SignUp.class, Login.class})
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", groups = {SignUp.class, Login.class})
    @NotBlank(groups = {SignUp.class, Login.class})
    private String email;

    @NotNull(groups = {SignUp.class, Login.class})
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?/\\\\|-]).{8,}$", groups = {SignUp.class, Login.class})
    @NotBlank(groups = {SignUp.class, Login.class})
    private String password;

    @NotNull(groups = {SignUp.class})
    @Size(min = 8, max = 100, groups = {SignUp.class})
    @NotBlank(groups = {SignUp.class})
    private String confirmPassword;

    @NotNull(groups = {SignUp.class})
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", groups = {SignUp.class})
    @NotBlank(groups = {SignUp.class})
    private String phone;

    public interface SignUp {
    }

    public interface Login {
    }
}
