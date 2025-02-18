package com.project_merge.jigu_travel.api.auth.service.impl;

import com.project_merge.jigu_travel.api.auth.dto.LoginRequestDto;
import com.project_merge.jigu_travel.api.auth.dto.LoginResponseDto;
import com.project_merge.jigu_travel.api.auth.dto.PasswordResetDto;
import com.project_merge.jigu_travel.api.auth.dto.RegisterRequestDto;
import com.project_merge.jigu_travel.api.auth.model.Auth;
import com.project_merge.jigu_travel.api.auth.model.AuthType;
import com.project_merge.jigu_travel.api.auth.repository.AuthRepository;
import com.project_merge.jigu_travel.api.auth.security.jwt.JwtUtil;
import com.project_merge.jigu_travel.api.auth.service.AuthService;
import com.project_merge.jigu_travel.api.auth.service.EmailService;
import com.project_merge.jigu_travel.api.user.model.Location;
import com.project_merge.jigu_travel.api.user.model.Role;
import com.project_merge.jigu_travel.api.user.model.User;
import com.project_merge.jigu_travel.api.user.repository.UserRepository;
import com.project_merge.jigu_travel.exception.CustomException;
import com.project_merge.jigu_travel.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional  // 트랜잭션 추가
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByLoginIdAndDeletedFalse(loginRequest.getLoginId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 기존 토큰 삭제 후 새 토큰 저장 (트랜잭션 내에서 실행됨)
        authRepository.deleteByUser(user);

        // Access Token & Refresh Token 생성
        String accessToken = jwtUtil.generateToken(user.getLoginId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getLoginId());

        authRepository.save(Auth.builder()
                .user(user)
                .authType(AuthType.LOCAL)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .createdAt(LocalDateTime.now())
                .build()
        );

        return new LoginResponseDto(accessToken, refreshToken, user.getNickname());
    }

    @Transactional
    @Override
    public void register(RegisterRequestDto request) {
        if (userRepository.findByLoginIdAndDeletedFalse(request.getLoginId()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_LOGIN_ID);
        }

        Location locationValue = (request.getLocation() != null) ? request.getLocation() : Location.KOR;

        User user = User.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .gender(request.getGender())
                .birthDate(request.getBirthDate())
                .location(locationValue)
                .role(Role.ROLE_USER)
                .email(request.getEmail())
                .build();

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void logout(String token) {
        String loginId = jwtUtil.validateToken(token);

        if (loginId == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        User user = userRepository.findByLoginIdAndDeletedFalse(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Auth auth = authRepository.findByAccessToken(token)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        // 사용자와 관련된 토큰 삭제 (DB에서 삭제)
        authRepository.invalidateAccessToken(token);
        authRepository.invalidateRefreshToken(auth.getRefreshToken());
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String loginId = jwtUtil.validateToken(refreshToken);
        if (loginId == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Auth auth = authRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        String newAccessToken = jwtUtil.generateToken(loginId);

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        auth.setAccessToken(newAccessToken);
        auth.setUpdatedAt(now);
        authRepository.save(auth);

        return newAccessToken;
    }


    /** 이메일을 이용한 비밀번호 찾기**/
    @Override
    @Transactional
    public void requestPasswordResetByEmail(String email) {
        User user = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 새로운 비밀번호 재설정 토큰 생성
        String resetToken = jwtUtil.generatePasswordResetToken(user.getLoginId());

        // 기존 비밀번호 재설정 토큰 삭제 후 새로 저장
        authRepository.deleteByUser(user);
        authRepository.save(Auth.builder()
                .user(user)
                .authType(AuthType.LOCAL)
                .accessToken(resetToken)
                .refreshToken("passwordReset")
                .createdAt(LocalDateTime.now())
                .build()
        );

        // 이메일 전송
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    /** 비밀번호 재설정 **/
    @Override
    @Transactional
    public void resetPassword(PasswordResetDto passwordResetDto) {
        String loginId = jwtUtil.validateToken(passwordResetDto.getToken());
        if (loginId == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        User user = userRepository.findByLoginIdAndDeletedFalse(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 변경 후 저장
        user.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        userRepository.save(user);

        // 기존 비밀번호 재설정 토큰 삭제
        authRepository.deleteByUser(user);
    }



}
