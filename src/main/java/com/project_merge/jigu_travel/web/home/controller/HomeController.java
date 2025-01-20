package com.project_merge.jigu_travel.web.home.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        System.out.println("`/home` 컨트롤러 메서드 실행됨!");

        String username = "손님";  // 기본값: 손님

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
                System.out.println("홈 컨트롤러에서 받은 사용자 이름: " + username);
            } else {
                System.out.println("인증된 사용자가 UserDetails 타입이 아닙니다: " + principal);
            }
        } else {
            System.out.println("ecurityContextHolder에 인증 정보가 없습니다.");
        }

        model.addAttribute("username", username);
        return "home";
    }
}
