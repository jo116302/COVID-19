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
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;

/*
 * @RestControllerAdvice(annotations = RestController.class)로 설정함으로서 RestController 어노테이션 사용하는 경우에만 처리가능하다.
 * 
 * ResponseEntityExceptionHandler 상속하는 이유
 *   - Spring MVC 내부에서 발생하는 예상하지 못하는 예외를 처리에 도움을 준다
 */

@RestControllerAdvice(annotations = RestController.class)
public class APIExceptionhandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {

        // S:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드
        /*
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request
        );
        */
        // E:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드

        return getInternalResponseEntity(e, ErrorCode.VALIDATION_ERROR, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    /* 
     * 예상되어진 예외 전역(글로벌) 처리
     * 
     * handleExceptionInternal 와 ResponseEntity을 통합하려면 handleExceptionInternal의 리턴타입에 맞춰 데이터 타입을 APIErrorResponse에서 Object로 변경
     * ConstraintViolationException Class는 validation 체크를 진행하다 오류코드로 넘어온다
     *
     * @ExceptionHandler
     *   - 일반적으로 테스트 수행시 Controller Test 작성할 때 함께 테스트 작성하게 된다.
     *   - 입출력을 제대로 확인해보고 싶은 경우 직접 호출해서 입력 출력을 확인하게 된다. (APIExceptionhandlerTest.java)
     */
    @ExceptionHandler
    // public ResponseEntity<APIErrorResponse> general(GeneralException e) {
    public ResponseEntity<Object> general(ConstraintViolationException e, WebRequest request) {

        // S:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드
        /*
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request
        );
        */
        // E:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드

        return getInternalResponseEntity(e, ErrorCode.VALIDATION_ERROR, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

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

        // S:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드
        // handleExceptionInternal 와 통합하면서 주석처리로 추가된 내용
        /*
        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request
        );
        */
        // E:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드

        return getInternalResponseEntity(e, errorCode, HttpHeaders.EMPTY, status, request);
    }

    /* 예상치 못한 예외 전역(글로벌) 처리 */
    @ExceptionHandler
    // public ResponseEntity<APIErrorResponse> exception(Exception e) {
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {

        /* // handleExceptionInternal 와 통합하면서 주석처리
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false, errorCode, errorCode.getMessage(e)
                ));
         */

        // S:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드
        // handleExceptionInternal 와 통합하면서 주석처리로 추가된 내용
        /*
        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request
        );
        */
        // E:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드

        return getInternalResponseEntity(e, ErrorCode.INTERNAL_ERROR, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.SPRING_BAD_REQUEST : ErrorCode.SPRING_INTERNAL_ERROR;

        // S:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드
        /*
        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                headers,
                status,
                request
        );
        */
        // E:Return 값의 반복(보일러코드)으로 인한 리팩토링 전 코드

        return getInternalResponseEntity(e, errorCode, headers, status, request);
    }

    /*
     * Return 값에서 반복적으로 동일한 코드(보일러코드)를 제거하기 위해 getInternalResponseEntity() 생성
     */
    private ResponseEntity<Object> getInternalResponseEntity(Exception e, ErrorCode errorCode, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                headers,
                status,
                request
        );
    }
}
