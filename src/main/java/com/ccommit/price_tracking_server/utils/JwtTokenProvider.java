package com.ccommit.price_tracking_server.utils;

import com.ccommit.price_tracking_server.enums.UserStatus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtTokenProvider {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY"); // JWT 서명에 사용할 비밀 키
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    // Access Token 생성 메서드
    public static String generateAccessToken(Long userId, Enum<UserStatus> roles) {
        return Jwts.builder().subject(userId.toString()).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))  // 만료 시간 설정
                .claim("roles", roles.name()).signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))  // 서명
                .compact();
    }

    // Refresh Token 생성 메서드
    public static String generateRefreshToken(Long userId) {
        return Jwts.builder().subject(userId.toString()).issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))  // 서명
                .compact();
    }
    //TODO : 추후 구현
//    // JWT 토큰을 검증하고 userId를 추출하는 메서드
//    public static String extractUserId(String token) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token)
//                    .getBody();  // Claims 객체 추출
//
//            return claims.getSubject();  // Subject 부분에서 userId 반환
//        } catch (ExpiredJwtException e) {
//            throw new RuntimeException("JWT Token이 만료되었습니다.", e);
//        } catch (Exception e) {
//            throw new RuntimeException("JWT Token 검증에 실패했습니다.", e);
//        }
//    }
}
