package com.project_merge.jigu_travel.api.auth.service.impl;

import com.project_merge.jigu_travel.api.auth.dto.LoginRequestDto;
import com.project_merge.jigu_travel.api.auth.dto.LoginResponseDto;
import com.project_merge.jigu_travel.api.auth.dto.RegisterRequestDto;
import com.project_merge.jigu_travel.api.auth.model.Auth;
import com.project_merge.jigu_travel.api.auth.model.AuthType;
import com.project_merge.jigu_travel.api.auth.repository.AuthRepository;
import com.project_merge.jigu_travel.api.auth.security.jwt.JwtUtil;
import com.project_merge.jigu_travel.api.auth.service.AuthService;
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

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional  // 트랜잭션 추가
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByLoginId(loginRequest.getLoginId())
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
        if (userRepository.findByLoginId(request.getLoginId()).isPresent()) {
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

        System.out.println("로그아웃 요청한 사용자 ID: " + loginId);

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Auth auth = authRepository.findByAccessToken(token)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        System.out.println("로그아웃 완료 - 사용자: " + user.getLoginId());

        // 🔥 사용자와 관련된 토큰 삭제 (DB에서 삭제)
        authRepository.invalidateAccessToken(token);
    }
}
