package com.project_merge.jigu_travel.api.visitor.controller;

import com.project_merge.jigu_travel.api.visitor.entity.VisitorCount;
import com.project_merge.jigu_travel.api.visitor.service.VisitorService;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

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

    // 특정 날짜 방문자 수 조회 API
    @GetMapping("/count-by-date")
    public BaseResponse<Long> getVisitorCountByDate(@RequestParam String date) {
        long count = visitorService.getVisitorCountByDate(LocalDate.parse(date));
        return new BaseResponse<>(200, date + " 방문자 수 조회 성공", count);
    }

    // 방문자 전체 기록 조회 API (관리자용)
    @GetMapping("/records")
    public BaseResponse<List<VisitorCount>> getVisitorRecords() {
        List<VisitorCount> records = visitorService.getAllVisitorRecords();
        return new BaseResponse<>(200, "방문자 전체 기록 조회 성공", records);
    }

}
