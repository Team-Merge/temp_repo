package com.project_merge.jigu_travel.web.user.controller;

import com.project_merge.jigu_travel.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.UUID;

@Controller
public class UserInfoController {

    @Autowired
    private UserService userService;

    @GetMapping("/user-info")
    public String userInfo(Model model) {
        try {
            UUID userId = userService.getCurrentUserUUID();
            System.out.println("현재 로그인한 사용자 UUID: " + userId);
            model.addAttribute("userId", userId);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            model.addAttribute("userId", "손님");
        }

        return "user-info";  // 'user-info' 뷰로 이동
    }
}