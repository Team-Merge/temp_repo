package com.project_merge.jigu_travel.api.auth.controller;

import com.project_merge.jigu_travel.api.auth.dto.*;
import com.project_merge.jigu_travel.api.auth.model.Auth;
import com.project_merge.jigu_travel.api.auth.service.AuthService;
import com.project_merge.jigu_travel.exception.CustomException;
import com.project_merge.jigu_travel.exception.ErrorCode;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project_merge.jigu_travel.api.auth.repository.AuthRepository;
import com.project_merge.jigu_travel.api.auth.security.jwt.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;

    @Value("${jwt.refresh-expiration}")
    private int refreshexpiration;

    /** ✅ 로그인 - RefreshToken을 쿠키에 저장 */
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
        try {
            LoginResponseDto loginResponse = authService.login(loginRequest);

            // ✅ Refresh Token을 HttpOnly 쿠키에 저장
            Cookie refreshTokenCookie = new Cookie("refreshToken", loginResponse.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(refreshexpiration);

            response.addCookie(refreshTokenCookie);

            System.out.println("✅ 로그인 성공, 새 Access Token: " + loginResponse.getAccessToken());

            return ResponseEntity.ok(new BaseResponse<>(200, "로그인 성공", loginResponse));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(400, e.getMessage(), null));
        }
    }

    /** ✅ 회원가입 */
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Void>> register(@RequestBody RegisterRequestDto registerRequest) {
        try {
            authService.register(registerRequest);
            return ResponseEntity.ok(new BaseResponse<>(200, "회원가입 성공", null));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(400, e.getMessage(), null));
        }
    }

    /** ✅ 로그아웃 - RefreshToken 쿠키 삭제 + AccessToken 무효화 */
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(
            HttpServletRequest request,
            HttpServletResponse response) {

        System.out.println("로그아웃 요청 수신");

        try {
            String accessToken = request.getHeader("Authorization");
            if (accessToken != null && accessToken.startsWith("Bearer ")) {
                accessToken = accessToken.substring(7);
                authService.logout(accessToken);
            }

            // ✅ Refresh Token 쿠키 삭제
            Cookie refreshTokenCookie = new Cookie("refreshToken", null);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(0);
            response.addCookie(refreshTokenCookie);

            return ResponseEntity.ok(new BaseResponse<>(200, "로그아웃 성공", null));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(400, e.getMessage(), null));
        }
    }

    /** ✅ Refresh Token을 사용하여 Access Token 갱신 */
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<RefreshTokenResponseDto>> refreshAccessToken(HttpServletRequest request) {
        System.out.println("🔄 Refresh Token 요청 수신");

        try {
            // ✅ 1. 쿠키에서 Refresh Token 가져오기
            Cookie[] cookies = request.getCookies();
            String refreshToken = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refreshToken".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                    }
                }
            }

            if (refreshToken == null) {
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            }

            // ✅ 2. Refresh Token 검증 (토큰 서명 & 만료 여부 확인)
            if (!jwtUtil.validateRefreshToken(refreshToken)) {
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            }

            // ✅ 3. DB에서 Refresh Token이 저장되어 있는지 확인
            Auth auth = authRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

            // ✅ 4. 새 Access Token 발급
            String loginId = auth.getUser().getLoginId();
            String newAccessToken = jwtUtil.generateToken(loginId);

            // ✅ 5. Access Token을 DB에 업데이트
            auth.setAccessToken(newAccessToken);
            authRepository.save(auth); // ✅ DB에 저장

            RefreshTokenResponseDto responseDto = new RefreshTokenResponseDto(newAccessToken);

            System.out.println("✅ 새로운 Access Token 발급 및 DB 저장 완료: " + newAccessToken);

            return ResponseEntity.ok(new BaseResponse<>(200, "새로운 Access Token 발급 성공", responseDto));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse<>(401, e.getMessage(), null));
        }
    }


}
