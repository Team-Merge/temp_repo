package com.project_merge.jigu_travel.api.location.service;

import com.project_merge.jigu_travel.api.location.model.Place;
import com.project_merge.jigu_travel.api.location.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final PlaceRepository placeRepository;

    /**
     * 사용자의 현재 위치를 기반으로 반경 내 명소를 검색합니다.
     * @param latitude 위도
     * @param longitude 경도
     * @param radius 반경 (km 단위)
     * @return 근처 명소 리스트
     */
    public List<Place> findNearbyPlaces(double latitude, double longitude, double radius) {
        return placeRepository.findNearbyPlaces(latitude, longitude, radius);
    }
}
