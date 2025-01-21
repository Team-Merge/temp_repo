package com.project_merge.jigu_travel.api.auth.security.jwt;

import com.project_merge.jigu_travel.api.auth.model.Auth;
import com.project_merge.jigu_travel.api.auth.repository.AuthRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessExpirationTime;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpirationTime;

    private final AuthRepository authRepository;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Access Token 생성
    public String generateToken(String loginId) {
        return Jwts.builder()
                .subject(loginId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(String loginId) {
        return Jwts.builder()
                .subject(loginId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    // 토큰 검증 및 DB에서 유효성 확인
    public String validateToken(String token) {
        try {
            System.out.println("토큰 검증 시작: " + token);

            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey()) // JWT 서명 검증
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String loginId = claims.getSubject();

            if (isTokenExpired(token)) {
                throw new RuntimeException("Access Token이 만료되었습니다.");
            }

            // DB에서 토큰 유효성 확인 (로그아웃된 사용자는 토큰 무효)
            Auth auth = authRepository.findByAccessToken(token)
                    .orElseThrow(() -> new RuntimeException("토큰이 유효하지 않습니다. 로그아웃되었거나 만료됨."));

            System.out.println("검증된 사용자 ID: " + loginId);
            return loginId;
        } catch (Exception e) {
            System.out.println("토큰 검증 실패: " + e.getMessage());
            return null;
        }
    }

    // Refresh Token 검증 메서드 추가
    public boolean validateRefreshToken(String refreshToken) {
        try {
            // JWT 서명 검증 및 클레임 파싱
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey()) // JWT 서명 검증
                    .build()
                    .parseSignedClaims(refreshToken)
                    .getPayload();

            // 만료 여부 확인
            if (claims.getExpiration().before(new Date())) {
                System.out.println("Refresh Token이 만료됨");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println("Refresh Token 검증 실패: " + e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey()) // JWT 서명 검증
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiration = claims.getExpiration();
            return expiration.before(new Date()); // 현재 시간과 비교하여 만료 여부 반환
        } catch (Exception e) {
            System.out.println("토큰 만료 검증 실패: " + e.getMessage());
            return true; // 예외 발생 시 만료된 것으로 처리
        }
    }
}
