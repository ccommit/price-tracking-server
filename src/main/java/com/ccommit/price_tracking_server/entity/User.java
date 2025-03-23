package com.ccommit.price_tracking_server.entity;

import com.ccommit.price_tracking_server.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")  // 테이블명 지정 (선택사항)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가 키
    @Column(name = "id")
    private Long id;

    @Size(min = 3, max = 50)
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Email
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Size(min = 8, max = 100)
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "status", nullable = false)
    @Builder.Default
    private Enum<UserStatus> status = UserStatus.ACTIVE;

    @Column(name = "email_verified", nullable = false)
    @Builder.Default
    private Boolean email_verified = false;

}
