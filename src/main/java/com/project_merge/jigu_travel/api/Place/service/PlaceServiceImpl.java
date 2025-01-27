package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.Place.entity.Place;
import com.project_merge.jigu_travel.api.Place.repository.PlaceRepository;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.PlaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Override
    public List<PlaceResponseDto> findNearbyPlace(double latitude, double longitude, double radius) {
        List<Place> places = placeRepository.findNearbyPlace(latitude, longitude, radius);
        return places.stream()
                .map(this::toPlaceResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlaceResponseDto findPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .map(this::toPlaceResponseDto)
                .orElse(null);
    }

    private PlaceResponseDto toPlaceResponseDto(Place place) {
        return PlaceResponseDto.builder()
                .placeId(place.getPlaceId().intValue())
                .type(place.getType())
                .name(place.getName())
                .tel(place.getTel())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .address(place.getAddress())
                .deleted(place.isDeleted())
                .createdAt(place.getCreatedAt())
                .updatedAt(place.getUpdatedAt())
                .build();
    }
}
