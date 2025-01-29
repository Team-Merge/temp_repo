//package com.project_merge.jigu_travel.api.ai_classification.service;
//
//import com.project_merge.jigu_travel.api.ai_classification.dto.RecommendationRequestDto;
//import com.project_merge.jigu_travel.api.ai_classification.dto.RecommendationResponseDto;
//import com.project_merge.jigu_travel.api.ai_classification.entity.UserInterest;
//import com.project_merge.jigu_travel.api.ai_classification.repository.UserInterestRepository;
//import com.project_merge.jigu_travel.api.ai_classification.dto.RecommendationResponseDto.RecommendationData;
//import com.project_merge.jigu_travel.api.user.service.impl.UserServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class UserInterestService {
//    private final RestTemplate restTemplate;
//    private final UserInterestRepository userInterestRepository;
//    private final UserServiceImpl userService;
//
//    public RecommendationData fetchAndSaveUserInterest(RecommendationRequestDto requestDto) {
//        UUID userId = userService.getCurrentUserUUID();
//        String fastApiUrl = "http://localhost:8000/recommend_category"; // FastAPI 엔드포인트
//
//        // ✅ FastAPI에 보낼 JSON 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<RecommendationRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);
//
//        // ✅ FastAPI로 POST 요청
//        ResponseEntity<RecommendationResponseDto> response = restTemplate.postForEntity(
//                fastApiUrl, requestEntity, RecommendationResponseDto.class
//        );
//
//        RecommendationResponseDto responseDto = response.getBody();
//        if (responseDto == null || responseDto.getData() == null) {
//            throw new RuntimeException("🚨 FastAPI 응답이 비어 있습니다.");
//        }
//
//        // 🔹 추천된 관심사 저장
//        var recommendations = responseDto.getData().getTop2Recommendations();
//        if (recommendations.size() >= 2) {
//            UserInterest userInterest = UserInterest.builder()
//                    .userId(userId)
//                    .interest(recommendations.get(0))
//                    .interest2(recommendations.get(1))
//                    .build();
//
//            userInterestRepository.save(userInterest);
//        }
//
//        System.out.println("🔹 FastAPI 응답 (DTO): " + responseDto);
//        return responseDto.getData(); // ✅ 중첩된 `data`만 반환
//    }
//}

package com.project_merge.jigu_travel.api.ai_classification.service;

import com.project_merge.jigu_travel.api.ai_classification.dto.RecommendationRequestDto;
import com.project_merge.jigu_travel.api.ai_classification.dto.RecommendationResponseDto;
import com.project_merge.jigu_travel.api.ai_classification.entity.UserInterest;
import com.project_merge.jigu_travel.api.ai_classification.repository.UserInterestRepository;
import com.project_merge.jigu_travel.api.ai_classification.dto.RecommendationResponseDto.RecommendationData;
import com.project_merge.jigu_travel.api.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInterestService {
    private final RestTemplate restTemplate;
    private final UserInterestRepository userInterestRepository;
    private final UserServiceImpl userService;

    @Transactional
    public RecommendationData fetchAndSaveUserInterest(RecommendationRequestDto requestDto) {
        UUID userId = userService.getCurrentUserUUID();
        String fastApiUrl = "http://localhost:8000/recommend_category"; // FastAPI 엔드포인트

        // ✅ FastAPI에 보낼 JSON 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RecommendationRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        // ✅ FastAPI로 POST 요청
        ResponseEntity<RecommendationResponseDto> response = restTemplate.postForEntity(
                fastApiUrl, requestEntity, RecommendationResponseDto.class
        );

        RecommendationResponseDto responseDto = response.getBody();
        if (responseDto == null || responseDto.getData() == null) {
            throw new RuntimeException("🚨 FastAPI 응답이 비어 있습니다.");
        }

        // 🔹 기존 관심사가 있는지 확인
        Optional<UserInterest> existingInterest = userInterestRepository.findByUserId(userId);

        var recommendations = responseDto.getData().getTop2Recommendations();
        if (recommendations.size() >= 2) {
            if (existingInterest.isPresent()) {
                // ✅ 기존 관심사가 있으면 업데이트
                UserInterest userInterest = existingInterest.get();
                userInterest.setInterest(recommendations.get(0));
                userInterest.setInterest2(recommendations.get(1));
                userInterestRepository.save(userInterest);
            } else {
                // ✅ 기존 관심사가 없으면 새로 저장
                UserInterest newUserInterest = UserInterest.builder()
                        .userId(userId)
                        .interest(recommendations.get(0))
                        .interest2(recommendations.get(1))
                        .build();
                userInterestRepository.save(newUserInterest);
            }
        }

        System.out.println("🔹 FastAPI 응답 (DTO): " + responseDto);
        return responseDto.getData(); // ✅ 중첩된 `data`만 반환
    }
}
