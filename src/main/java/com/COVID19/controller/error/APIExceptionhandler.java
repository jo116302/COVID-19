package com.COVID19.controller.error;

import com.COVID19.constant.ErrorCode;
import com.COVID19.dto.APIErrorResponse;
import com.COVID19.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*
 * @RestControllerAdvice(annotations = RestController.class)로 설정함으로서 RestController 어노테이션 사용하는 경우에만 처리가능하다.
 * 
 * ResponseEntityExceptionHandler 상속하는 이유
 *   - Spring MVC 내부에서 발생하는 예상하지 못하는 예외를 처리에 도움을 준다
 */

@RestControllerAdvice(annotations = RestController.class)
public class APIExceptionhandler extends ResponseEntityExceptionHandler {

    /* 
     * 예상되어진 예외 전역(글로벌) 처리
     * 
     * handleExceptionInternal 와 ResponseEntity을 통합하려면 handleExceptionInternal의 리턴타입에 맞춰 데이터 타입을 APIErrorResponse에서 Object로 변경
     */
    @ExceptionHandler
    // public ResponseEntity<APIErrorResponse> general(GeneralException e) {
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        
        /* // handleExceptionInternal 와 통합하면서 주석처리
        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false, errorCode, errorCode.getMessage(e)
                ));
         */

        // handleExceptionInternal 와 통합하면서 주석처리로 추가된 내용
        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request
        );
    }

    /* 예상치 못한 예외 전역(글로벌) 처리 */
    @ExceptionHandler
    // public ResponseEntity<APIErrorResponse> exception(Exception e) {
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        /* // handleExceptionInternal 와 통합하면서 주석처리
        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false, errorCode, errorCode.getMessage(e)
                ));
         */

        // handleExceptionInternal 와 통합하면서 주석처리로 추가된 내용
        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.SPRING_BAD_REQUEST : ErrorCode.SPRING_INTERNAL_ERROR;

        return super.handleExceptionInternal(
                ex,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(ex)),
                headers,
                status,
                request
        );
    }
}
