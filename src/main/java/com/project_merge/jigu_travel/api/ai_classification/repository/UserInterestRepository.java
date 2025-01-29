//package com.project_merge.jigu_travel.api.ai_classification.repository;
//
//import com.project_merge.jigu_travel.api.ai_classification.entity.UserInterest;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
//    List<UserInterest> findByUserId(UUID userId);
//}


package com.project_merge.jigu_travel.api.ai_classification.repository;

import com.project_merge.jigu_travel.api.ai_classification.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    Optional<UserInterest> findByUserId(UUID userId);
}