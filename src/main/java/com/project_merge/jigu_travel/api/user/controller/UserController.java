package com.project_merge.jigu_travel.api.user.controller;

import com.project_merge.jigu_travel.api.user.dto.UserDto;
import com.project_merge.jigu_travel.api.user.model.Role;
import com.project_merge.jigu_travel.api.user.model.User;
import com.project_merge.jigu_travel.api.user.repository.UserRepository;
import com.project_merge.jigu_travel.exception.CustomException;
import com.project_merge.jigu_travel.exception.ErrorCode;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

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

    /** 관리자 여부 조회 */
    @GetMapping("/is-admin")
    public ResponseEntity<BaseResponse<Boolean>> checkAdmin(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse<>(401, "인증되지 않은 사용자", false));
        }

        String loginId = userDetails.getUsername();
        User user = userRepository.findByLoginIdAndDeletedFalse(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return ResponseEntity.ok(new BaseResponse<>(200, "관리자 여부 조회 성공", user.getIsAdmin()));
    }

    /** 전체 사용자 목록 조회 */
    @GetMapping("/all")
    public ResponseEntity<BaseResponse<Page<UserDto>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> users = userRepository.findAll(pageable)
                .map(user -> UserDto.builder()
                        .userId(user.getUserId())
                        .loginId(user.getLoginId())
                        .nickname(user.getNickname())
                        .birthDate(user.getBirthDate())
                        .gender(user.getGender())
                        .location(user.getLocation())
                        .role(user.getRole())
                        .build());

        return ResponseEntity.ok(new BaseResponse<>(200, "전체 사용자 조회 성공", users));
    }


    @PostMapping("/set-admin")
    public ResponseEntity<BaseResponse<String>> setAdmin(@RequestParam UUID userId, @RequestParam String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        try {
            Role newRole = Role.valueOf(role);  // 올바른 ENUM 값인지 체크
            user.setRole(newRole);
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(400, "잘못된 role 값입니다.", null));
        }

        String message = role.equals("ROLE_ADMIN") ? "관리자 권한 부여 완료" : "일반 사용자로 변경 완료";
        return ResponseEntity.ok(new BaseResponse<>(200, message, null));
    }


}
