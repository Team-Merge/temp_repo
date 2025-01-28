package com.project_merge.jigu_travel.web.ai_guide.controller;

import com.project_merge.jigu_travel.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  // HTML 페이지를 반환하는 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/ai-guide")
public class AiGuideController {

    private final UserService userService;
    // ai-guide.html을 반환하는 GET 요청
    @GetMapping("")
    public String aiGuidePage(Model model) {


        return "ai_guide";  // ai-guide.html 페이지를 반환
    }


}
