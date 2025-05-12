package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String showHomePage() {
        return "forward:/welcome.html";
    }

    @GetMapping("/test")
    @ResponseBody
    public String hello() {
        return "Spring Boot is working!";
    }
}
