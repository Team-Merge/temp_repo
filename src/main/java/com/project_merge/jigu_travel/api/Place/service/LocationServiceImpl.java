package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.Place.entity.UserLocation;
import com.project_merge.jigu_travel.api.Place.repository.UserLocationRepository;
import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final Map<String, List<LocationRequestDto>> userLocation = new HashMap<>();
    private final UserLocationRepository userLocationRepository;

    @Override
    public void saveUserLocation(String userId, LocationRequestDto locationRequestDto) {
        userLocation.computeIfAbsent(userId, k -> new ArrayList<>()).add(locationRequestDto);
    }

    // 여행 종료 시, HashMap에 저장된 위치 데이터를 DB로 저장 후 삭제
    @Override
    public void saveAllUserLocationToDB(String userId) {
        List<LocationRequestDto> locations = userLocation.get(userId);

        if (locations != null && !locations.isEmpty()) {
            UserLocation locationEntity = new UserLocation();
            locationEntity.setUserId(userId);
            locationEntity.setLocations(locations); // JSON 변환하여 저장
            userLocationRepository.save(locationEntity);

            // HashMap에서 삭제 (메모리 정리)
            userLocation.remove(userId);
        }
    }
}
