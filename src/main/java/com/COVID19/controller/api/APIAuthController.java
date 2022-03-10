package com.COVID19.controller.api;

import com.COVID19.dto.APIDataResponse;
import com.COVID19.dto.AdminRequest;
import com.COVID19.dto.EventResponse;
import com.COVID19.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Controller + ResponseBody
 * - ResponseBody : Http Body에 Controller 데이터 값을 담을 수 있도록 지원해주는 기능을 수행
 */
@RestController
@RequestMapping("/api")
public class APIAuthController {

    @PostMapping("/sign-up")
    public APIDataResponse<String> signUp(
            @RequestBody AdminRequest adminRequest
            ) {
        return APIDataResponse.empty();
    }

    @PostMapping("/login")
    public APIDataResponse<String> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return APIDataResponse.empty();
    }
}
