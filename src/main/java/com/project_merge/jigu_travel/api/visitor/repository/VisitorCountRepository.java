package com.project_merge.jigu_travel.api.visitor.repository;

import com.project_merge.jigu_travel.api.visitor.entity.VisitorCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface VisitorCountRepository extends JpaRepository<VisitorCount, Long> {


    // 특정 날짜의 전체 방문자 수 조회
    @Query("SELECT COUNT(DISTINCT v.ip) FROM VisitorCount v WHERE v.visitDate = :visitDate")
    long countVisitorByDate(LocalDate visitDate);

    // 특정 날짜의 특정 IP 방문자 수 조회
    @Query("SELECT COUNT(DISTINCT v.ip) FROM VisitorCount v WHERE v.visitDate = :visitDate AND v.ip = :ip")
    long countVisitorByDateAndIp(LocalDate visitDate, String ip);

    // 특정 날짜에 특정 IP로 방문한 기록이 있는지 조회
    Optional<VisitorCount> findByIpAndVisitDate(String ip, LocalDate visitDate);

    // 특정 날짜 방문자 수 조회
    long countByVisitDate(LocalDate visitDate);

    List<VisitorCount> findAll();

    // 특정 날짜의 전체 방문 횟수 합산
    @Query("SELECT COALESCE(SUM(v.visitCount), 0) FROM VisitorCount v WHERE v.visitDate = :visitDate")
    long sumVisitCountByDate(LocalDate visitDate);


    @Query("SELECT COALESCE(SUM(v.visitCount), 0) FROM VisitorCount v WHERE v.visitDate = :visitDate AND v.ip = :ip")
    long sumVisitCountByDateAndIp(LocalDate visitDate, String ip);

    // 날짜 범위 및 IP로 방문 기록 조회 (페이지네이션 적용)
    Page<VisitorCount> findByVisitDateBetweenAndIpContaining(
            LocalDate startDate, LocalDate endDate, String ip, Pageable pageable);

    // 날짜 범위만으로 조회 (IP 없이)
    Page<VisitorCount> findByVisitDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 특정 기간의 방문 기록 조회
    List<VisitorCount> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<VisitorCount> findByCreatedAtBetweenAndIp(LocalDateTime startDate, LocalDateTime endDate, String ip);

}
