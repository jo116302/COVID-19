package com.COVID19.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller + ResponseBody
 * - ResponseBody : Http Body에 Controller 데이터 값을 담을 수 있도록 지원해주는 기능을 수행
 */
@RestController
@RequestMapping("/api")
public class APIAuthController {

    @GetMapping("/sign-up")
    public String signUp() {
        return "don.";
    }

    @GetMapping("/login")
    public String login() {
        return "don.";
    }
}
