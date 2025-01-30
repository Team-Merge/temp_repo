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
        System.out.println("테스트 사용자");
        if (userDetails == null) {
            System.out.println("테스트 사용자2");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse<>(401, "인증되지 않은 사용자", null));
        }

        String loginId = userDetails.getUsername();
        User user = userRepository.findByLoginIdAndDeletedFalse(loginId)
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

    @GetMapping("/check-nickname")
    public ResponseEntity<BaseResponse<Boolean>> checkNickname(@RequestParam String nickname) {
        boolean exists = userRepository.existsByNickname(nickname);
        if (exists) {
            return ResponseEntity.ok(new BaseResponse<>(409, "이미 사용 중인 닉네임입니다.", false));
        }
        return ResponseEntity.ok(new BaseResponse<>(200, "사용 가능한 닉네임입니다.", true));
    }

    @GetMapping("/check-loginId")
    public ResponseEntity<BaseResponse<Boolean>> checkLoginId(@RequestParam String loginId) {
        boolean exists = userRepository.existsByLoginId(loginId);
        if (exists) {
            return ResponseEntity.ok(new BaseResponse<>(409, "이미 사용 중인 아이디입니다.", false));
        }
        return ResponseEntity.ok(new BaseResponse<>(200, "사용 가능한 아이디입니다.", true));
    }
}
