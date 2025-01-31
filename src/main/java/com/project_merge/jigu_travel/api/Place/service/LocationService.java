package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;

public interface LocationService {
    void saveUserLocation(LocationRequestDto locationRequestDto);
    void saveAllUserLocationToDB();
    LocationRequestDto getLastUserLocation();
}
