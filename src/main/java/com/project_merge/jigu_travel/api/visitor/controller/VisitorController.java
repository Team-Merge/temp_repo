package com.project_merge.jigu_travel.api.visitor.controller;

import com.project_merge.jigu_travel.api.visitor.entity.VisitorCount;
import com.project_merge.jigu_travel.api.visitor.service.VisitorService;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    // 방문자 수 증가 API
    @PostMapping("/count")
    public BaseResponse<String> countVisitor(HttpServletRequest request) {
        visitorService.countVisitor(request);
        return new BaseResponse<>(200, "방문자 수 증가 완료", null);
    }

    // 오늘 방문자 수 조회 API
    @GetMapping("/today-count")
    public BaseResponse<Long> getTodayVisitorCount() {
        long count = visitorService.getTodayVisitorCount();
        return new BaseResponse<>(200, "오늘 방문자 수 조회 성공", count);
    }

    // 특정 IP의 오늘 방문 횟수 조회 API
    @GetMapping("/visit-count")
    public BaseResponse<Integer> getVisitCount(HttpServletRequest request) {
        int visitCount = visitorService.getVisitCountByIp(request);
        return new BaseResponse<>(200, "오늘 해당 IP 방문 횟수 조회 성공", visitCount);
    }

    // 특정 날짜 방문자 수 조회 API (IP 필터링 추가)
    @GetMapping("/count-by-date")
    public BaseResponse<Long> getVisitorCountByDate(
            @RequestParam String date,
            @RequestParam(required = false, defaultValue = "") String ip) {

        long count = visitorService.getVisitorCountByDate(date, ip);
        return new BaseResponse<>(200, date + " 방문자 수 조회 성공", count);
    }

    // 특정 날짜 방문 횟수 합산 API
    @GetMapping("/total-visit-count")
    public BaseResponse<Long> getTotalVisitCountByDate(
            @RequestParam String date,
            @RequestParam(required = false, defaultValue = "") String ip) {

        long totalVisitCount = visitorService.getTotalVisitCountByDate(date, ip);
        return new BaseResponse<>(200, date + " 방문 횟수 조회 성공", totalVisitCount);
    }

    // 방문자 통계 조회 (페이지네이션 + 검색 기능 추가)
    @GetMapping("/records")
    public BaseResponse<Page<VisitorCount>> getVisitorRecordsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false, defaultValue = "") String ip) {

        Page<VisitorCount> records = visitorService.getVisitorRecordsWithPagination(page, size, startDate, endDate, ip);
        return new BaseResponse<>(200, "방문자 기록 조회 성공", records);
    }

    // 특정 기간 동안 방문자들의 시간대별 방문 횟수 조회 API
    @GetMapping("/visit-count-by-hour")
    public BaseResponse<Map<Integer, Long>> getVisitCountByHour(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false, defaultValue = "") String ip) {

        Map<Integer, Long> visitCountByHour = visitorService.getVisitCountByHour(startDate, endDate, ip);
        return new BaseResponse<>(200, "시간대별 방문 횟수 조회 성공", visitCountByHour);
    }
}
