package com.spring.springboard.config;

import com.spring.springboard.domain.user.enums.UserRole;
import com.spring.springboard.domain.user.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization 헤더 확인
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 Bearer 토큰이 아닌 경우 필터 통과
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT 토큰 추출 및 검증
        String jwt = jwtUtil.substringToken(authorizationHeader);
        Claims claims = null;

        try {
            claims = jwtUtil.extractToken(jwt);
        } catch (Exception ex) {
            JwtExceptionHandler.handleJwtException(response, ex);
            return;
        }

        String email = claims.get("email", String.class);
        Long userId = Long.parseLong(claims.getSubject());
        UserRole userRole = UserRole.of(claims.get("userRole", String.class));

        // 사용자 정보 HttpServletRequest 에 저장
        request.setAttribute("userId", userId);
        request.setAttribute("email", email);
        request.setAttribute("userRole", userRole);

        // SecurityContextHolder 에 인증 정보 저장
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 필터 체인 통과
        filterChain.doFilter(request, response);
    }
}
