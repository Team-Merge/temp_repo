package com.project_merge.jigu_travel.api.user.service.impl;

import com.project_merge.jigu_travel.api.auth.model.CustomUserDetails;
import com.project_merge.jigu_travel.api.auth.security.jwt.JwtUtil;
import com.project_merge.jigu_travel.api.user.model.User;
import com.project_merge.jigu_travel.api.user.repository.UserRepository;
import com.project_merge.jigu_travel.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return userRepository.findByLoginIdAndDeletedFalse(loginId);
    }

    @Override
    public String getLoginIdFromToken(String token) {
        String loginId = jwtUtil.validateToken(token);

        if (loginId == null) {
            throw new RuntimeException("유효하지 않은 토큰");
        }

        // 사용자 ID로 사용자 정보 조회
        Optional<User> userOpt = userRepository.findByLoginIdAndDeletedFalse(loginId);
        if (userOpt.isPresent()) {
            return userOpt.get().getNickname();
        } else {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }
    }

    // UUID 반환 함수
    public UUID getCurrentUserUUID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                return userDetails.getUser().getUserId();
            }
        }
        throw new IllegalStateException("현재 인증된 사용자가 없거나 CustomUserDetails 타입이 아닙니다.");
    }
}