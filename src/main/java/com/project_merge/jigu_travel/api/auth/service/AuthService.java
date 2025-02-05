package com.project_merge.jigu_travel.api.auth.service;

import com.project_merge.jigu_travel.api.auth.dto.LoginRequestDto;
import com.project_merge.jigu_travel.api.auth.dto.LoginResponseDto;
import com.project_merge.jigu_travel.api.auth.dto.PasswordResetDto;
import com.project_merge.jigu_travel.api.auth.dto.RegisterRequestDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequest);
    void register(RegisterRequestDto registerRequest);
    void logout(String loginId);
    String refreshAccessToken(String refreshToken);
    void requestPasswordResetByEmail(String email);
    void resetPassword(PasswordResetDto passwordResetDto);
}
