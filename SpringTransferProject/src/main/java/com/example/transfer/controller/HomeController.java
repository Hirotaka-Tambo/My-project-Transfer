package com.example.transfer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // templates/index.html を表示(テスト用)
        return "index";
    }
}