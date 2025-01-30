package com.project_merge.jigu_travel.api.Place.repository;

import com.project_merge.jigu_travel.api.Place.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    List<UserLocation> findByUserId(String userId);
}
