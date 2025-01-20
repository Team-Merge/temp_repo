package com.project_merge.jigu_travel.web.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/auth/register")
    public String registerPage() {
        return "register";  // `register.html` 뷰 반환
    }
}
