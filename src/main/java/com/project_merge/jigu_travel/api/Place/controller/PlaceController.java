package com.project_merge.jigu_travel.api.Place.controller;

import com.project_merge.jigu_travel.api.Place.dto.requestDto.PlaceUpdateRequestDto;
import com.project_merge.jigu_travel.api.Place.service.PlaceService;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.PlaceResponseDto;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import com.project_merge.jigu_travel.global.common.PlaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            @RequestParam(defaultValue = "1.0") double radius,
            @RequestParam(required = false) List<String> types,
            @RequestHeader("Authorization") String accessToken) {

        List<PlaceResponseDto> places = placeService.findNearbyPlace(latitude, longitude, radius, types);
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
    public ResponseEntity<BaseResponse<String>> uploadCsv(@RequestParam("file") MultipartFile file) {
        placeService.uploadPlacesFromCsv(file);
        return ResponseEntity.ok(new BaseResponse<>(200, "CSV 파일이 성공적으로 업로드되었습니다.", null));
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getAllPlaces(
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false, defaultValue = "3.0") Double radius,
            @RequestParam(required = false) Boolean includeDeleted,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String searchBy
    ) {
        boolean includeDeletedFinal = includeDeleted != null ? includeDeleted : false;

        Page<PlaceResponseDto> places;
        if (latitude == null || longitude == null || radius == null) {
            places = placeService.findAllPlaces(includeDeletedFinal, page, size, searchQuery, searchBy);
        } else {
            places = placeService.findNearbyALLPlaces(latitude, longitude, radius, page, size, includeDeletedFinal);
        }

        // totalPages 포함하여 응답 반환
        Map<String, Object> response = new HashMap<>();
        response.put("content", places.getContent());  // 장소 리스트
        response.put("totalPages", places.getTotalPages()); // 전체 페이지 수 포함

        return ResponseEntity.ok(new BaseResponse<>(200, "장소 조회 성공", response));
    }



    @PatchMapping("/update/{placeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<PlaceResponseDto>> updatePlace(
            @PathVariable Long placeId,
            @RequestBody PlaceUpdateRequestDto updateDto) {

        PlaceResponseDto updatedPlace = placeService.updatePlace(placeId, updateDto);
        return ResponseEntity.ok(new BaseResponse<>(200, "장소 정보 업데이트 성공", updatedPlace));
    }

    @DeleteMapping("/delete/{placeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> toggleDeletePlace(@PathVariable Long placeId) {
        Boolean isDeleted = placeService.toggleDeletePlace(placeId);

        if (isDeleted == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(404, "해당 장소를 찾을 수 없습니다.", null));
        }

        if (isDeleted) {
            return ResponseEntity.ok(new BaseResponse<>(200, "장소 삭제 성공", null));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(200, "장소 복구 성공", null));
        }
    }

    @DeleteMapping("/permanent-delete/{placeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> permanentlyDeletePlace(@PathVariable Long placeId) {
        boolean isDeleted = placeService.permanentlyDeletePlace(placeId);

        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(404, "해당 장소를 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok(new BaseResponse<>(200, "장소가 완전히 삭제되었습니다.", null));
    }


    @GetMapping("/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getDeletedPlaces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PlaceResponseDto> places = placeService.findDeletedPlaces(page, size);

        // 응답 데이터에 totalPages 포함
        Map<String, Object> response = new HashMap<>();
        response.put("content", places.getContent());  // 삭제된 장소 리스트
        response.put("totalPages", places.getTotalPages()); // 전체 페이지 수

        return ResponseEntity.ok(new BaseResponse<>(200, "삭제된 장소 조회 성공", response));
    }

}