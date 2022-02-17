package com.COVID19.controller;

import com.COVID19.domain.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/* Controller layer 로 Bean 에 등록 */
@ControllerAdvice(basePackageClasses = Admin.class)
@Controller
public class BaseController {

    /*
     * 1. Jsp 경로 설정 방법
     * Jsp 경로에 대한 설정
     * JspTemplateAvailabilityProvider class 파일에서 확인 가능(src/main/webapp)
     *
     * jsp가 아닌 html을 바라보도록 설정하기 위해서는 application.properties에 등록이 필요
     *
     * 2. template의 기능 일부분을 빌려와 사용하는 방법
     * view의 경로 잡는 복잡한 과정을 회피하기 위하여 thymeleaf를 사용한다. gradle 설정에서 확인 가능하다.
     *  (현재 2번 방법을 사용)
     *
     * 3. WebmvcAutoConfiguration.java의 welcomePageHandlerMapping 메소드의 기본기능을 사용
     * 해당 기능을 시용하기 위해서는 '/' 경로를 작성하지 않고 해당 파일을 src/main/resource/static 경로에 해당 root 페이지가 존재할 때 맵핑
     */

    @GetMapping("/")
    public String root() throws Exception {
        throw new Exception("테스트");
        //return "index";
    }

}
