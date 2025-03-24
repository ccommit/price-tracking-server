package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.entity.User;
import com.ccommit.price_tracking_server.exception.UnauthorizedException;
import com.ccommit.price_tracking_server.security.JwtTokenProvider;
import com.ccommit.price_tracking_server.service.TokenService;
import com.ccommit.price_tracking_server.service.UserService;
import com.ccommit.price_tracking_server.validation.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtTokenValidator jwtTokenValidator;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public String refreshAccessToken(String refreshToken) {
        try {
            Claims claims = jwtTokenValidator.validateAndGetClaims("REFRESH",refreshToken);
            Long userId = Long.parseLong(claims.getSubject());  // refresh token에서 userId 추출

            String storedRefreshToken = redisTemplate.opsForValue().get("refresh_token:" + userId);

            if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)) {
                User user = userService.getUserById(userId);
                String newAccessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getStatus());
                return newAccessToken;
            } else {
                throw new UnauthorizedException();
            }

        } catch (Exception e) {
            throw new UnauthorizedException();
        }
    }

}
