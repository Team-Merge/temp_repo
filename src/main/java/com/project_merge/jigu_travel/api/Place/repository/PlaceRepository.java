package com.project_merge.jigu_travel.api.Place.repository;

import com.project_merge.jigu_travel.api.Place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    // 반경 내 명소 검색 (Haversine Formula 활용)
    @Query("SELECT p FROM Place p WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:longitude)) " +
            "+ sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radius " +
            "AND p.deleted = false")
    List<Place> findNearbyPlace(double latitude, double longitude, double radius);

    @Query("SELECT p FROM Place p WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:longitude)) " +
            "+ sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radius " +
            "AND p.deleted = false")
    Page<Place> findNearbyALLPlaces(double latitude, double longitude, double radius, Pageable pageable);
}
