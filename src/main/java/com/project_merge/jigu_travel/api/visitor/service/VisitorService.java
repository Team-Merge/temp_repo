package com.project_merge.jigu_travel.api.visitor.service;

import com.project_merge.jigu_travel.api.visitor.entity.VisitorCount;
import com.project_merge.jigu_travel.api.visitor.repository.VisitorCountRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

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
    public long getVisitorCountByDate(LocalDate date) {
        return visitorCountRepository.countByVisitDate(date);
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

}
