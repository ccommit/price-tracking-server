package com.ccommit.price_tracking_server.security;

import com.ccommit.price_tracking_server.enums.UserStatus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = SecretKeyProvider.getInstance().getSecretKey(); // JWT 서명에 사용할 비밀 키
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30; // 1개월

    // Access Token 생성 메서드
    public static String generateAccessToken(String email, Enum<UserStatus> roles) {
        return Jwts.builder().subject(email).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))  // 만료 시간 설정
                .claim("roles", roles.name())
                .claim("token_type", "ACCESS")
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))  // 서명
                .compact();
    }

    // Refresh Token 생성 메서드
    public static String generateRefreshToken(Long userId) {
        return Jwts.builder().subject(userId.toString()).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .claim("token_type", "REFRESH")
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))  // 서명
                .compact();
    }
}
