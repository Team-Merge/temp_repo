package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.websocket.dto.responseDto.PlaceResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlaceService {
    List<PlaceResponseDto> findNearbyPlace(double latitude, double longitude, double radius);

    PlaceResponseDto findPlaceById(Long placeId);

    // CSV 업로드 기능 추가
    void uploadPlacesFromCsv(MultipartFile file);
}
