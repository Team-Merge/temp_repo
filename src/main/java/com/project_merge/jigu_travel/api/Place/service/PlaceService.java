package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.Place.dto.requestDto.PlaceUpdateRequestDto;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.PlaceResponseDto;
import com.project_merge.jigu_travel.global.common.PlaceType;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlaceService {
    List<PlaceResponseDto> findNearbyPlace(double latitude, double longitude, double radius, List<String> types);

    PlaceResponseDto findPlaceById(Long placeId);

    // CSV 업로드 기능 추가
    void uploadPlacesFromCsv(MultipartFile file);

    Page<PlaceResponseDto> findAllPlaces(boolean includeDeleted, int page, int size, String searchQuery, String searchBy);

    Page<PlaceResponseDto> findNearbyALLPlaces(double latitude, double longitude, double radius, int page, int size, boolean includeDeleted);

    PlaceResponseDto updatePlace(Long placeId, PlaceUpdateRequestDto updateDto);

    Boolean toggleDeletePlace(Long placeId);

    boolean permanentlyDeletePlace(Long placeId);

    Page<PlaceResponseDto> findDeletedPlaces(int page, int size);

}