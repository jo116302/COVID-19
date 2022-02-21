package com.COVID19.controller.api;

import com.COVID19.constant.ErrorCode;
import com.COVID19.dto.APIErrorResponse;
import com.COVID19.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIEventController {

    @GetMapping("/events")
    public List<String> getEvents() {
        // 예외처리를 하기위한 테스트 케이스
        // throw new HttpRequestMethodNotSupportedException("405 Error Test");

        return List.of("event1", "event2");
    }

    //@PostMapping("/place/{placeId}/events")
    @PostMapping("/events")
    public Boolean createEvent() {
        // 예외처리를 하기위한 테스트 케이스
        // throw new GeneralException("장군님");

        return true;
    }

    @GetMapping("/events/{eventId}")
    public String getEvent(@PathVariable Integer eventId) {
        // 예외처리를 하기위한 테스트 케이스
        // throw new RuntimeException("런타임 에러");

        return "eventId : "+eventId;
    }

    @PutMapping("/events/{eventId}")
    public Boolean modifyEvent(@PathVariable Integer eventId) {
        return false;
    }

    @DeleteMapping("/events/{eventId}")
    public Boolean removeEvent(@PathVariable Integer eventId) {
        return true;
    }

    /* // general 메소드 예외처리를 BaseExceptionHandler.java 로 이동
    @ExceptionHandler
    public ResponseEntity<APIErrorResponse> general(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false, errorCode, errorCode.getMessage(e)
                ));
    }
    */
}
