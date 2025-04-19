package com.ccommit.price_tracking_server.validation;

import com.ccommit.price_tracking_server.exception.InvalidSignatureException;
import com.ccommit.price_tracking_server.exception.InvalidTokenFormatException;
import com.ccommit.price_tracking_server.exception.TokenExpiredException;
import com.ccommit.price_tracking_server.exception.UnauthorizedException;
import com.ccommit.price_tracking_server.security.SecretKeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenValidator {
    private static final String SECRET_KEY = SecretKeyProvider.getInstance().getSecretKey();

    // JWT 유효성 검사
    public Claims validateAndGetClaims(String token) {
        try {
            // JwtParser 생성
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))) // 서명 키 설정
                    .build()
                    .parseSignedClaims(token) // JWT 토큰 파싱 및 서명 검증
                    .getPayload();

            // 토큰이 만료되지 않았는지 체크
            if (isTokenExpired(claims)) {
                throw new TokenExpiredException();
            }

            return claims;
        } catch (MalformedJwtException e) {
            throw new InvalidTokenFormatException();    // 잘못된 JWT 토큰 형식이 입력된 경우
        } catch (SignatureException e) {
            throw new InvalidSignatureException();  // 서명검증이 실패한 경우
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException();  // JWT 토큰이 없거나 유효하지 않은 경우
        }
    }

    // 만료 시간 체크
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new java.util.Date());
    }
}
