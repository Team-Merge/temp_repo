package com.project_merge.jigu_travel.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;  // 리프레시 토큰은 나중에 추가 가능
    private String nickname;
}
