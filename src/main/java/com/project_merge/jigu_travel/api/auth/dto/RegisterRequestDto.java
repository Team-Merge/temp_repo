package com.project_merge.jigu_travel.api.auth.dto;

import com.project_merge.jigu_travel.api.user.model.Gender;
import com.project_merge.jigu_travel.api.user.model.Location;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;

    private String birthDate; // "YYYY-MM-DD"
    private Gender gender;
    private Location location = Location.KOR;
}
