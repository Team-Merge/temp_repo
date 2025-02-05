package com.project_merge.jigu_travel.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetDto {

    @NotBlank(message = "토큰은 필수 입력값입니다.")
    private String token;

    @NotBlank(message = "새로운 비밀번호는 필수 입력값입니다.")
    private String newPassword;
}