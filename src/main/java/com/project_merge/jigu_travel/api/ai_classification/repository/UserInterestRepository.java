package com.project_merge.jigu_travel.api.ai_classification.repository;

import com.project_merge.jigu_travel.api.ai_classification.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    Optional<UserInterest> findByUserId(UUID userId);
}