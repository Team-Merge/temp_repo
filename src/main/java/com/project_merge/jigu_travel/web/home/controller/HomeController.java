package com.project_merge.jigu_travel.web.home.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.project_merge.jigu_travel.api.user.service.UserService;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        System.out.println("`/home` 컨트롤러 메서드 실행됨!");

        String username = "손님";  // 기본값: 손님

        // SecurityContextHolder에서 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("홈 컨트롤러에서 인증 정보: " + authentication);

        if (authentication != null && authentication.isAuthenticated()) {
            // principal이 UserDetails 타입인 경우 사용자 정보를 가져옴
            Object principal = authentication.getPrincipal();

            System.out.println("principal 객체: " + principal);
            System.out.println("principal의 클래스: " + principal.getClass());

            if (principal instanceof UserDetails) {
                // UserDetails에서 사용자 이름을 추출
                username = ((UserDetails) principal).getUsername();
                System.out.println("홈 컨트롤러에서 받은 사용자 이름: " + username);

                // 사용자 정보를 모델에 추가
                String loginId = ((UserDetails) principal).getUsername();
                String nickname = userService.getLoginIdFromToken(loginId);  // 로그인 ID로 닉네임을 가져옴
                System.out.println("사용자 닉네임: " + nickname);

                // 닉네임을 username에 저장
                username = nickname;  // 또는 username = loginId;
            } else {
                System.out.println("인증된 사용자가 UserDetails 타입이 아닙니다: " + principal);
            }
        } else {
            System.out.println("SecurityContextHolder에 인증 정보가 없습니다.");
        }

        // 사용자 정보를 모델에 추가
        model.addAttribute("username", username);
        return "home";  // 'home' 뷰로 반환
    }
}
