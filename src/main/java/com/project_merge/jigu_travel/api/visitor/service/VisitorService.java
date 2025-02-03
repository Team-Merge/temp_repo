package com.project_merge.jigu_travel.api.visitor.service;

import com.project_merge.jigu_travel.api.visitor.entity.VisitorCount;
import com.project_merge.jigu_travel.api.visitor.repository.VisitorCountRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorCountRepository visitorCountRepository;

    // 방문자 수 증가 (IP별 방문 횟수도 저장)
    public void countVisitor(HttpServletRequest request) {
        String visitorIp = getClientIp(request);
        LocalDate today = LocalDate.now();

        // 해당 IP의 오늘 방문 기록을 조회
        Optional<VisitorCount> existingVisit = visitorCountRepository.findByIpAndVisitDate(visitorIp, today);

        if (existingVisit.isPresent()) {
            // 기존 방문 기록이 있으면 visitCount 증가
            VisitorCount visitor = existingVisit.get();
            visitor.setVisitCount(visitor.getVisitCount() + 1);
            visitorCountRepository.save(visitor);
            System.out.println("기존 방문자: " + visitorIp + " | 방문 횟수: " + visitor.getVisitCount());
        } else {
            // 새로운 방문 기록 저장 (최초 방문)
            VisitorCount visitor = VisitorCount.builder()
                    .ip(visitorIp)
                    .visitDate(today)
                    .visitCount(1)
                    .build();
            visitorCountRepository.save(visitor);
            System.out.println("신규 방문자: " + visitorIp);
        }
    }

    // 오늘 방문자 수 조회
    public long getTodayVisitorCount() {
        return visitorCountRepository.countByVisitDate(LocalDate.now());
    }

    // 특정 날짜 방문자 수 조회
    public long getVisitorCountByDate(String date, String ip) {
        LocalDate visitDate = LocalDate.parse(date);

        if (ip != null && !ip.isEmpty()) {
            // 특정 IP 방문자 수 조회
            return visitorCountRepository.countVisitorByDateAndIp(visitDate, ip);
        } else {
            // 전체 방문자 수 조회
            return visitorCountRepository.countVisitorByDate(visitDate);
        }
    }

    // 특정 IP의 오늘 방문 횟수 조회
    public int getVisitCountByIp(HttpServletRequest request) {
        String visitorIp = getClientIp(request);
        LocalDate today = LocalDate.now();

        return visitorCountRepository.findByIpAndVisitDate(visitorIp, today)
                .map(VisitorCount::getVisitCount)
                .orElse(0);
    }

    // 클라이언트 IP 가져오기
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public List<VisitorCount> getAllVisitorRecords() {
        return visitorCountRepository.findAll();
    }

    // 특정 날짜의 모든 방문 횟수 합산
    public long getTotalVisitCountByDate(String date, String ip) {
        LocalDate visitDate = LocalDate.parse(date);

        if (ip != null && !ip.isEmpty()) {
            // 특정 IP의 방문 횟수 합산
            return visitorCountRepository.sumVisitCountByDateAndIp(visitDate, ip);
        } else {
            // 전체 방문 횟수 합산
            return visitorCountRepository.sumVisitCountByDate(visitDate);
        }
    }

    // 방문자 전체 기록 조회 (페이지네이션 및 검색 가능)
    public Page<VisitorCount> getVisitorRecordsWithPagination(
            int page, int size, String startDate, String endDate, String ip) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Pageable pageable = PageRequest.of(page, size);

        // IP 검색 여부에 따라 다르게 처리
        if (ip != null && !ip.isEmpty()) {
            return visitorCountRepository.findByVisitDateBetweenAndIpContaining(start, end, ip, pageable);
        } else {
            return visitorCountRepository.findByVisitDateBetween(start, end, pageable);
        }
    }

    public Map<Integer, Long> getVisitCountByHour(String startDate, String endDate, String ip) {
        LocalDateTime startDateTime = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate).atTime(23, 59, 59);

        Map<Integer, Long> visitCountByHour = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            visitCountByHour.put(i, 0L);
        }

        List<VisitorCount> records;
        if (ip != null && !ip.isEmpty()) {
            // 특정 IP의 방문 기록 조회
            records = visitorCountRepository.findByCreatedAtBetweenAndIp(startDateTime, endDateTime, ip);
        } else {
            // 모든 방문 기록 조회
            records = visitorCountRepository.findByCreatedAtBetween(startDateTime, endDateTime);
        }

        for (VisitorCount record : records) {
            int hour = record.getCreatedAt().getHour();
            visitCountByHour.put(hour, visitCountByHour.get(hour) + 1);
        }

        return visitCountByHour;
    }


}
