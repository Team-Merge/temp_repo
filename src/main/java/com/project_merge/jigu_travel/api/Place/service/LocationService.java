package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;

import java.util.List;

public interface LocationService {
    void saveUserLocation(String userId, LocationRequestDto locationRequestDto);
    List<LocationRequestDto> getUserLocation(String userId);
}
