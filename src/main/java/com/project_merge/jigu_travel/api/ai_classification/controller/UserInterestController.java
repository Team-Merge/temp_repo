package com.project_merge.jigu_travel.api.ai_classification.controller;

import com.project_merge.jigu_travel.api.ai_classification.dto.RecommendationRequestDto;
import com.project_merge.jigu_travel.api.ai_classification.dto.RecommendationResponseDto.RecommendationData;
import com.project_merge.jigu_travel.api.ai_classification.service.UserInterestService;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.project_merge.jigu_travel.api.user.service.impl.UserServiceImpl;
import com.project_merge.jigu_travel.api.ai_classification.repository.UserInterestRepository;
import com.project_merge.jigu_travel.api.ai_classification.entity.UserInterest;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/ai/ai_classification")
@RequiredArgsConstructor
public class UserInterestController {
    private final UserInterestService userInterestService;

    // 사용자 관심사 데이터를 받아 저장, 추천 데이터 반환
    @PostMapping("/fetch")
    public ResponseEntity<BaseResponse<RecommendationData>> fetchAndSaveUserInterest(
            @RequestBody RecommendationRequestDto requestDto) {

        try {
            RecommendationData response = userInterestService.fetchAndSaveUserInterest(requestDto);

            // `RecommendationData`만 감싸서 정상 응답 반환
            return ResponseEntity.ok(new BaseResponse<>(200, "SUCCESS", response));

        } catch (IllegalArgumentException e) {
            // 잘못된 입력값 예외 처리
            return ResponseEntity.badRequest().body(new BaseResponse<>(400, "잘못된 요청: " + e.getMessage(), null));

        } catch (RuntimeException e) {
            // 서버 내부 오류 처리
            return ResponseEntity.internalServerError().body(new BaseResponse<>(500, "서버 오류 발생: " + e.getMessage(), null));
        }
    }

    private final UserInterestRepository userInterestRepository;
    private final UserServiceImpl userService; // 현재 로그인한 사용자 정보 가져오기 위한 서비스

    // 현재 로그인한 사용자의 관심사 존재 여부 확인
    @GetMapping("/exists")
    public ResponseEntity<BaseResponse<Boolean>> checkUserInterest(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse<>(401, "인증되지 않은 사용자", false));
        }

        UUID userId = userService.getCurrentUserUUID();
        boolean exists = userInterestRepository.findByUserId(userId).isPresent();

        return ResponseEntity.ok(new BaseResponse<>(200, exists ? "관심사가 존재합니다." : "관심사가 없습니다.", exists));
    }

    // 현재 로그인한 사용자의 관심사 정보를 조회
    @GetMapping("/get-user-interest")
    public ResponseEntity<BaseResponse<UserInterest>> getUserInterest(@AuthenticationPrincipal UserDetails userDetails) {
        return Optional.ofNullable(userDetails)
                .map(details -> userInterestRepository.findByUserId(userService.getCurrentUserUUID())
                        .map(interest -> ResponseEntity.ok(new BaseResponse<>(200, "사용자의 관심사를 조회했습니다.", interest)))
                        .orElseGet(() -> ResponseEntity.ok(new BaseResponse<>(200, "사용자의 관심사가 없습니다.", null))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new BaseResponse<>(401, "인증되지 않은 사용자", null)));
    }

}

