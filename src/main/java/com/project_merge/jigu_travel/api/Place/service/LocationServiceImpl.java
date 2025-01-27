package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationServiceImpl implements LocationService {

    private final Map<String, List<LocationRequestDto>> userLocation = new HashMap<>();

    @Override
    public void saveUserLocation(String userId, LocationRequestDto locationRequestDto) {
        userLocation.computeIfAbsent(userId, k -> new ArrayList<>()).add(locationRequestDto);
    }

    @Override
    public List<LocationRequestDto> getUserLocation(String userId) {
        return userLocation.getOrDefault(userId, new ArrayList<>());
    }
}
