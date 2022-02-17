package com.COVID19.controller.error;

import com.COVID19.constant.ErrorCode;
import com.COVID19.dto.APIErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class BaseErrorController implements ErrorController {

    /*
     * Error Custom Page 설정
     * 오류가 발생하면 커스텀 매핑이 필요하며, 설정이 필요하다.
     *
     * 절차는 다음과 같다.
     * 1) application.properties 에서 'server.error.whitelabel.enabled=false' 을 설정하여 에러 경로를 만든다.
     * 2) Error 커스텀 페이지가 페이지가 있는 Controller 에서 'ErrorController'를 구현한다.
     */

    // View 형태로 전달
    /*
     * @RequestMapping의 속성 produces = MediaType.TEXT_HTML_VALUE 을 사용하면서 text/html을 Accept 헤더로 요청된 사항만 처리하게 된다.
     */
    @RequestMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorhtml(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                        "statusCode",status.value(),
                        "errorCode", errorCode,
                        "message", errorCode.getMessage(status.getReasonPhrase())
                ),
                status
                );
    }


    /* JSON 형태로 전달 */
    @RequestMapping("/error")
    public ResponseEntity<APIErrorResponse> error(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false,
                        errorCode
                ));
    }
}
