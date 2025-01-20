package com.project_merge.jigu_travel.web.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login";
    }
}