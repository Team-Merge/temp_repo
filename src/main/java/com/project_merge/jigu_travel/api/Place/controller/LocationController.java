package com.project_merge.jigu_travel.api.Place.controller;

import com.project_merge.jigu_travel.api.Place.model.Place;
import com.project_merge.jigu_travel.api.Place.service.LocationService;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController {

    private final Map<String, Map<String, Double>> userLocations = new HashMap<>();
    private final LocationService locationService;

     // 위치 데이터 저장
    @PostMapping("/user-location")
    public ResponseEntity<BaseResponse<String>> saveUserLocation(
            @RequestHeader(value = "Authorization", required = false) String userId,
            @RequestBody Map<String, Double> locationData) {
        if (userId == null || userId.isEmpty()) {
            userId = "anonymous";
        }

        Double latitude = locationData.get("latitude");
        Double longitude = locationData.get("longitude");

        if (latitude == null || longitude == null) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(400, "위치 데이터가 누락되었습니다.", null));
        }

        userLocations.put(userId, Map.of("latitude", latitude, "longitude", longitude));
        return ResponseEntity.ok(new BaseResponse<>(200, "위치 저장 성공", "Location saved for user: " + userId));
    }

    //위치 데이터 반환
    @GetMapping("/user-location")
    public ResponseEntity<BaseResponse<Map<String, Double>>> getUserLocation(
            @RequestHeader(value = "Authorization", required = false) String userId) {
        if (userId == null || userId.isEmpty()) {
            userId = "anonymous";
        }

        if (!userLocations.containsKey(userId)) {
            return ResponseEntity.status(404).body(new BaseResponse<>(404, "위치 데이터가 없습니다.", null));
        }

        return ResponseEntity.ok(new BaseResponse<>(200, "위치 데이터 반환 성공", userLocations.get(userId)));
    }

    // 근처 명소 검색
    @GetMapping("/nearby-places")
    public ResponseEntity<BaseResponse<List<Place>>> getNearbyPlaces(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "1.0") double radius) {
        List<Place> places = locationService.findNearbyPlaces(latitude, longitude, radius);
        if (places.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(200, "주변 명소가 없습니다.", places));
        }
        return ResponseEntity.ok(new BaseResponse<>(200, "주변 명소 검색 성공", places));
    }

    // 명소 상세 조회
    @GetMapping("/place/{placeId}")
    public ResponseEntity<BaseResponse<Place>> getPlaceById(@PathVariable Long placeId) {
        Place place = locationService.findPlaceById(placeId)
                .orElse(null); // Optional에서 안전하게 값을 꺼냄

        if (place == null) {
            return ResponseEntity.status(404).body(new BaseResponse<>(404, "명소를 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok(new BaseResponse<>(200, "명소 상세 조회 성공", place));
    }
}
