package com.project_merge.jigu_travel.api.auth.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/css/") || requestURI.startsWith("/js/") || requestURI.startsWith("/images/") || requestURI.startsWith("/auth/register") || requestURI.startsWith("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        System.out.println("JWT 필터 - 요청 URI: " + requestURI);
        System.out.println("Authorization 헤더 값: " + token);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String loginId = jwtUtil.validateToken(token);

            if (loginId != null) {
                System.out.println("드디어 성공: " + loginId);

                UserDetails userDetails = User.withUsername(loginId)
                        .password("")
                        .roles("USER")
                        .build();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("현재 인증 정보: " + SecurityContextHolder.getContext().getAuthentication());
            } else {
                System.out.println("아니 왜 실패야...살려줘요");
            }
        } else {
            System.out.println("비상! 토큰 없다!! 비상!: " + requestURI);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
