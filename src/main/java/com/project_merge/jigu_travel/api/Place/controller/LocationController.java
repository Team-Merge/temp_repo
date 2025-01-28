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

    // 위치 데이터 반환
    @GetMapping("/user-location")
    public ResponseEntity<BaseResponse<List<LocationRequestDto>>> getUserLocation(
            @RequestHeader(value = "Authorization", required = false) String userId) {
        if (userId == null || userId.isEmpty()) {
            userId = "anonymous";
        }

        List<LocationRequestDto> location = locationService.getUserLocation(userId);

        if (location.isEmpty()) {
            return ResponseEntity.status(404).body(new BaseResponse<>(404, "위치 데이터가 없습니다.", null));
        }

        return ResponseEntity.ok(new BaseResponse<>(200, "위치 데이터 반환 성공", location));
    }
}
