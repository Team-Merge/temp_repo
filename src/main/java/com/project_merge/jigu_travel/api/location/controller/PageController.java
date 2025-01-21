package com.project_merge.jigu_travel.api.location.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/location")
    public String locationPage() {
        return "location"; // templates/location.html 반환
    }
}
