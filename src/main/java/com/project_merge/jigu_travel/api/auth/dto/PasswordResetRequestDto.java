package com.project_merge.jigu_travel.api.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetRequestDto {

    private String email; // 사용자의 이메일
}