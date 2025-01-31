package com.project_merge.jigu_travel.api.Place.controller;

import com.project_merge.jigu_travel.api.Place.service.PlaceService;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.PlaceResponseDto;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    // 근처 명소 검색
    @GetMapping("/nearby-places")
    public ResponseEntity<BaseResponse<List<PlaceResponseDto>>> getNearbyPlace(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "1.0") double radius) {

        List<PlaceResponseDto> places = placeService.findNearbyPlace(latitude, longitude, radius);
        if (places.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(200, "주변 명소가 없습니다.", places));
        }
        return ResponseEntity.ok(new BaseResponse<>(200, "주변 명소 검색 성공", places));
    }

    // 명소 상세 조회
    @GetMapping("/{placeId}")
    public ResponseEntity<BaseResponse<PlaceResponseDto>> getPlaceById(@PathVariable Long placeId) {
        PlaceResponseDto place = placeService.findPlaceById(placeId);
        if (place == null) {
            return ResponseEntity.status(404).body(new BaseResponse<>(404, "명소를 찾을 수 없습니다.", null));
        }
        return ResponseEntity.ok(new BaseResponse<>(200, "명소 상세 조회 성공", place));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        placeService.uploadPlacesFromCsv(file);
        return ResponseEntity.ok("CSV 파일이 성공적으로 업로드되었습니다.");
    }
}
