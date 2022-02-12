package com.COVID19.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/* Controller layer 로 Bean 에 등록 */
@Controller
public class BaseController implements ErrorController {

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
    public String root() {
        return "index";
    }

    /*
     * Error Custom Page 설정
     * 오류가 발생하면 커스텀 매핑이 필요하며, 설정이 필요하다.
     *
     * 절차는 다음과 같다.
     * 1) application.properties 에서 'server.error.whitelabel.enabled=false' 을 설정하여 에러 경로를 만든다.
     * 2) Error 커스텀 페이지가 페이지가 있는 Controller 에서 'ErrorController'를 구현한다.
     */
    @RequestMapping("/error")
    public String error() {
        return "error";
    }
}
