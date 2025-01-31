package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.Place.entity.UserLocation;
import com.project_merge.jigu_travel.api.Place.repository.UserLocationRepository;
import com.project_merge.jigu_travel.api.user.service.UserService;
import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final Map<UUID, List<LocationRequestDto>> userLocation = new HashMap<>();
    private final UserLocationRepository userLocationRepository;
    private final UserService userService;

    @Override
    public void saveUserLocation(LocationRequestDto locationRequestDto) {
        UUID userId = userService.getCurrentUserUUID();
        userLocation.computeIfAbsent(userId, k -> new ArrayList<>()).add(locationRequestDto);
    }

    // 여행 종료 시, HashMap에 저장된 위치 데이터를 DB로 저장 후 삭제
    @Override
    public void saveAllUserLocationToDB() {
        UUID userId = userService.getCurrentUserUUID();
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

    // 마지막 저장된 위치 반환
    public LocationRequestDto getLastUserLocation() {
        UUID userId = userService.getCurrentUserUUID();
        List<LocationRequestDto> locations = userLocation.get(userId);
        if (locations != null && !locations.isEmpty()) {
            return locations.get(locations.size() - 1);
        }
        return null;
    }
}
