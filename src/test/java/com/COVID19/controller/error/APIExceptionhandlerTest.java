package com.COVID19.controller.error;

import com.COVID19.constant.ErrorCode;
import com.COVID19.dto.APIErrorResponse;
import com.COVID19.exception.GeneralException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class APIExceptionhandlerTest {

    private APIExceptionhandler sut;
    private WebRequest webRequest;

    /*
     * MockHttpServletRequest
     *   - ControllerTest의 MockMvc와 관련되어있는 객체이다.
     *   - MockMvc에서 사용되는 객체로 직접 호출하여 사용하는 형태로 이용가능하다.
     */
    @BeforeEach
    void setUp() {
        sut = new APIExceptionhandler();
        webRequest = new DispatcherServletWebRequest(new MockHttpServletRequest());
    }

    @DisplayName("검증 모듈 - 응답 데이터 정의")
    @Test
    void givenException_whenCallingValidation_thenReturnsEntity () {
        // Given
        ConstraintViolationException e = new ConstraintViolationException(Set.of(

        ));

        // When
        ResponseEntity<Object> response = sut.validation(e, webRequest);

        // Then
        Assertions.assertThat(response)
                .hasFieldOrPropertyWithValue("body", APIErrorResponse.of(false, ErrorCode.VALIDATION_ERROR, e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST);
    }

    @DisplayName("프로젝트 일반 모듈 - 응답 데이터 정의")
    @Test
    void givenGenerationException_whenCallingValidation_thenReturnsEntity () {
        // Given
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        GeneralException e = new GeneralException(errorCode);

        // When
        ResponseEntity<Object> response = sut.general(e, webRequest);


        // Then
        Assertions.assertThat(response)
                .hasFieldOrPropertyWithValue("body", APIErrorResponse.of(false, errorCode, e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DisplayName("기타(전체) 오류 - 응답 데이터 정의")
    @Test
    void givenOtherException_whenCallingValidation_thenReturnsEntity () {
        // Given
        Exception e = new Exception();

        // When
        ResponseEntity<Object> response = sut.exception(e, webRequest);


        // Then
        Assertions.assertThat(response)
                .hasFieldOrPropertyWithValue("body", APIErrorResponse.of(false, ErrorCode.INTERNAL_ERROR, e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}