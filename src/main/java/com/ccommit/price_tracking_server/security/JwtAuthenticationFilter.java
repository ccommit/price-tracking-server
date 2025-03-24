package com.ccommit.price_tracking_server.security;

import com.ccommit.price_tracking_server.enums.UserStatus;
import com.ccommit.price_tracking_server.service.TokenService;
import com.ccommit.price_tracking_server.validation.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenValidator jwtTokenValidator;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().equals("/refresh-token")) {
            handleRefreshToken(request, response);
            return;  // Refresh Token 처리 후 다음 필터로 넘어가지 않음
        }
        // Authorization 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        // "Bearer "가 있는지 체크
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 부분을 제거하고 token 추출
            String token = authorizationHeader.replace("Bearer ", "");

            try {
                // JWT 유효성 검사 및 claims 추출
                Claims claims = jwtTokenValidator.validateAndGetClaims("ACCESS",token);

                String email = claims.getSubject();
                Enum<UserStatus> role = getRoleFromToken(claims);

                // 권한 설정 (roles를 authorities로 변환)
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));

                // 인증 객체 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.error("JWT 인증 실패: " + e.getMessage());
            }
        }

        // 필터 체인의 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }

    private UserStatus getRoleFromToken(Claims claims) {
        String role = claims.get("roles", String.class);  // roles는 String 타입으로 저장
        return UserStatus.valueOf(role.toUpperCase());
    }

    private void handleRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader("Authorization").replace("Bearer ", "");

        String newAccessToken = tokenService.refreshAccessToken(refreshToken);
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
