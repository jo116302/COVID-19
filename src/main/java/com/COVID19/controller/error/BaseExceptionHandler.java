package com.COVID19.controller.error;

import com.COVID19.constant.ErrorCode;
import com.COVID19.dto.APIErrorResponse;
import com.COVID19.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
public class BaseExceptionHandler {

    /* 예상되어진 예외 전역(글로벌) 처리 */
    @ExceptionHandler
    public ModelAndView general(GeneralException e, HttpServletResponse response) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                        "statusCode",status.value(),
                        "errorCode", errorCode,
                        "message", errorCode.getMessage(e)
                ),
                status
        );
    }

    /* 예상치 못한 예외 전역(글로벌) 처리 */
    @ExceptionHandler
    public ModelAndView exception(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                        "statusCode",status.value(),
                        "errorCode", errorCode,
                        "message", errorCode.getMessage(e)
                ),
                status
        );
    }
}
