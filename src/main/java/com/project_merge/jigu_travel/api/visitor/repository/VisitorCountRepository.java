package com.project_merge.jigu_travel.api.visitor.repository;

import com.project_merge.jigu_travel.api.visitor.entity.VisitorCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface VisitorCountRepository extends JpaRepository<VisitorCount, Long> {

    // 특정 날짜에 특정 IP로 방문한 기록이 있는지 조회
    Optional<VisitorCount> findByIpAndVisitDate(String ip, LocalDate visitDate);

    // 특정 날짜 방문자 수 조회
    long countByVisitDate(LocalDate visitDate);

    List<VisitorCount> findAll();

}
