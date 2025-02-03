package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.websocket.dto.responseDto.PlaceResponseDto;
import com.project_merge.jigu_travel.global.common.PlaceType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlaceService {
    List<PlaceResponseDto> findNearbyPlace(double latitude, double longitude, double radius, List<String> types);

    PlaceResponseDto findPlaceById(Long placeId);

    // CSV 업로드 기능 추가
    void uploadPlacesFromCsv(MultipartFile file);

    List<PlaceResponseDto> findNearbyALLPlaces(double latitude, double longitude, double radius, int page, int size);

}
