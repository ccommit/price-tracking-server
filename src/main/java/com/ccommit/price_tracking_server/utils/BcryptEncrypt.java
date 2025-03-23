package com.ccommit.price_tracking_server.utils;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BcryptEncrypt {
    private final PasswordEncoder passwordEncoder;

    // 비밀번호 암호화
    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    // 비밀번호 일치 여부 확인
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
