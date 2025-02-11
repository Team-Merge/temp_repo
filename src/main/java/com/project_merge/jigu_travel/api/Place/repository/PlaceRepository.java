package com.project_merge.jigu_travel.api.Place.repository;

import com.project_merge.jigu_travel.api.Place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    // 반경 내 모든 명소 검색 (Haversine Formula 활용)
    @Query("SELECT DISTINCT p FROM Place p WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:longitude)) " +
            "+ sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radius " +
            "AND p.deleted = false")
    List<Place> findNearbyPlace(double latitude, double longitude, double radius);

    // 특정 PlaceType만 조회 (카테고리 필터 적용)
    @Query("SELECT DISTINCT p FROM Place p WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:longitude)) " +
            "+ sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radius " +
            "AND p.deleted = false " +
            "AND (" +
            "p.types LIKE CONCAT('%', :types1, '%') " + //개별
            "OR p.types LIKE CONCAT('%', :types2, '%') " +  //개별
            "OR p.types LIKE CONCAT('%', :combinedTypes, '%')" + //다중
            ")")
    List<Place> findNearbyPlaceByTypes(
            double latitude,
            double longitude,
            double radius,
            @Param("types1") String types1,
            @Param("types2") String types2,
            @Param("combinedTypes") String combinedTypes);

    // 삭제되지 않은 장소만 조회
    Page<Place> findByDeletedFalse(Pageable pageable);

    // 삭제 여부와 관계없이 반경 내 장소 조회
    @Query("SELECT p FROM Place p WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radius")
    Page<Place> findNearbyALLPlacesIncludingDeleted(double latitude, double longitude, double radius, Pageable pageable);

    // 삭제되지 않은 장소만 조회
    @Query("SELECT p FROM Place p WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radius " +
            "AND p.deleted = false")
    Page<Place> findNearbyALLPlaces(double latitude, double longitude, double radius, Pageable pageable);

    @Query("SELECT p FROM Place p WHERE p.name = :name AND p.address = :address ORDER BY p.updatedAt DESC LIMIT 1")
    Optional<Place> findByNameAndAddress(@Param("name") String name, @Param("address") String address);

    // 삭제되지 않은 장소만 조회
    @Query("SELECT p FROM Place p WHERE p.deleted = false")
    List<Place> findAllActivePlaces();

    // 삭제된 장소만 조회하는 페이징 쿼리
    @Query("SELECT p FROM Place p WHERE p.deleted = true")
    Page<Place> findDeletedPlaces(Pageable pageable);

    Page<Place> findByNameContainingIgnoreCaseAndDeletedFalse(String name, Pageable pageable);
    Page<Place> findByTypesContainingIgnoreCaseAndDeletedFalse(String type, Pageable pageable);
    Page<Place> findByAddressContainingIgnoreCaseAndDeletedFalse(String address, Pageable pageable);

    Page<Place> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Place> findByTypesContainingIgnoreCase(String type, Pageable pageable);
    Page<Place> findByAddressContainingIgnoreCase(String address, Pageable pageable);

    @Query(value = """
        SELECT 
            SUM(CASE WHEN p.types LIKE '%식도락_여행%' THEN 1 ELSE 0 END) AS foodTravel,
            SUM(CASE WHEN p.types LIKE '%오락_체험_여행%' THEN 1 ELSE 0 END) AS entertainmentTravel,
            SUM(CASE WHEN p.types LIKE '%힐링_여행%' THEN 1 ELSE 0 END) AS healingTravel,
            SUM(CASE WHEN p.types LIKE '%역사_문화_여행%' THEN 1 ELSE 0 END) AS historyTravel,
            SUM(CASE WHEN p.types LIKE '%쇼핑_여행%' THEN 1 ELSE 0 END) AS shoppingTravel,
            SUM(CASE WHEN p.types LIKE '%캠핑_글램핑_여행%' THEN 1 ELSE 0 END) AS campingTravel
        FROM place p
        WHERE p.deleted = false
    """, nativeQuery = true)
    List<Object[]> countPlacesByCategory();




}
