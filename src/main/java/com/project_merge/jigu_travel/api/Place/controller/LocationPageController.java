package com.project_merge.jigu_travel.api.Place.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/location")
public class LocationPageController {

    @GetMapping
    public String showMapPage() {
        return "map"; // map.html 템플릿 반환
    }
}