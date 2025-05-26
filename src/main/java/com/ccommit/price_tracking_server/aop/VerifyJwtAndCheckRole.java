package com.ccommit.price_tracking_server.aop;

import com.ccommit.price_tracking_server.DTO.CommonResponse;
import com.ccommit.price_tracking_server.aop.annotation.CheckToken;
import com.ccommit.price_tracking_server.entity.User;
import com.ccommit.price_tracking_server.enums.SuccessDetailMessage;
import com.ccommit.price_tracking_server.enums.UserStatus;
import com.ccommit.price_tracking_server.exception.InvalidRefreshTokenException;
import com.ccommit.price_tracking_server.exception.RoleAccessDeniedException;
import com.ccommit.price_tracking_server.exception.UnauthorizedException;
import com.ccommit.price_tracking_server.security.JwtTokenProvider;
import com.ccommit.price_tracking_server.service.UserService;
import com.ccommit.price_tracking_server.validation.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class VerifyJwtAndCheckRole {
    private final JwtTokenValidator jwtTokenValidator;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserService userService;
    private final HttpServletRequest request;
    public static final String TOKEN_TYPE_REFRESH = "REFRESH";
    public static final String CLAIM_KEY_TOKEN_TYPE = "token_type";
    public static final String ROLES = "roles";

    @Around("@annotation(com.ccommit.price_tracking_server.annotation.CheckToken) && @annotation(checkToken)")
    public Object verifyJwtAndCheckRole(ProceedingJoinPoint joinPoint, CheckToken checkToken) throws Throwable {
        String token = extractJwtToken();
        if (token == null) {
            throw new UnauthorizedException();
        }

        Claims claims = validateAndExtractClaims(token);
        if(claims.get(CLAIM_KEY_TOKEN_TYPE, String.class).equals(TOKEN_TYPE_REFRESH) && isTokenInRedis(claims, token)) {
            User user = userService.getUserById(Long.parseLong(claims.getSubject()));
            String newAccessToken = JwtTokenProvider.generateAccessToken(user.getEmail(), user.getStatus());
            SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_TOKEN_REFRESHED;
            return ResponseEntity.ok(new CommonResponse<>(message.name(), message.getMessage(), newAccessToken));
        }
        String email = claims.getSubject();
        UserStatus userStatus = UserStatus.valueOf(claims.get(ROLES, String.class).toUpperCase());

        if (!isUserAuthorized(userStatus, checkToken.roles())) {
            throw new RoleAccessDeniedException();
        }

        Object[] args = joinPoint.getArgs();
        args[0] = email;
        return joinPoint.proceed(args);
    }

    private String extractJwtToken() {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }

    private Claims validateAndExtractClaims(String token) {
        return jwtTokenValidator.validateAndGetClaims(token);
    }

    private boolean isUserAuthorized(UserStatus userRole, UserStatus[] allowedRoles) {
        return allowedRoles.length == 0 || Arrays.asList(allowedRoles).contains(userRole);
    }

    private boolean isTokenInRedis(Claims claims, String token) {
        String redisToken = redisTemplate.opsForValue().get("refresh_token:" + claims.getSubject());
        if (redisToken == null) {
            throw new InvalidRefreshTokenException();
        }
        return redisToken.equals(token);
    }
}
