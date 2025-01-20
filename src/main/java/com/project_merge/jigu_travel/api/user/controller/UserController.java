package com.project_merge.jigu_travel.api.user.controller;

import com.project_merge.jigu_travel.api.user.dto.UserDto;
import com.project_merge.jigu_travel.api.user.model.User;
import com.project_merge.jigu_travel.api.user.repository.UserRepository;
import com.project_merge.jigu_travel.exception.CustomException;
import com.project_merge.jigu_travel.exception.ErrorCode;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserDto>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse<>(401, "인증되지 않은 사용자", null));
        }

        String loginId = userDetails.getUsername();
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        UserDto userDto = UserDto.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .location(user.getLocation())
                .role(user.getRole())
                .isAdmin(user.isAdmin())
                .build();

        return ResponseEntity.ok(new BaseResponse<>(200, "사용자 정보 조회 성공", userDto));
    }
}
