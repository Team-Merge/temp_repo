package com.project_merge.jigu_travel.api.Place.controller;

import com.project_merge.jigu_travel.api.Place.service.LocationService;
import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    // 위치 데이터 저장
    @PostMapping("/user-location")
    public ResponseEntity<BaseResponse<String>> saveUserLocation(
            @RequestHeader(value = "Authorization", required = false) String userId,
            @RequestBody LocationRequestDto locationRequestDto) {
        if (userId == null || userId.isEmpty()) {
            userId = "anonymous";
        }

        Double latitude = locationRequestDto.getLatitude();
        Double longitude = locationRequestDto.getLongitude();

        if (latitude == null || longitude == null) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(400, "위치 데이터가 누락되었습니다.", null));
        }

        locationService.saveUserLocation(userId, locationRequestDto);

        return ResponseEntity.ok(new BaseResponse<>(200, "위치 저장 성공", "Location saved for user: " + userId));
    }

    // 여행 종료 시 HashMap 데이터를 DB로 저장하고 메모리에서 삭제
    @PostMapping("/end-travel")
    public ResponseEntity<BaseResponse<String>> endTravel(
            @RequestHeader(value = "Authorization", required = false) String userId) {
        if (userId == null || userId.isEmpty()) {
            userId = "anonymous";
        }

        locationService.saveAllUserLocationToDB(userId);
        return ResponseEntity.ok(new BaseResponse<>(200, "여행 데이터 저장 완료", "All location data saved to DB for user: " + userId));
    }
}
