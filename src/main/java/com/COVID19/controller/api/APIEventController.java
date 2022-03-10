package com.COVID19.controller.api;

import com.COVID19.constant.ErrorCode;
import com.COVID19.constant.EventStatus;
import com.COVID19.dto.APIDataResponse;
import com.COVID19.dto.APIErrorResponse;
import com.COVID19.dto.EventRequest;
import com.COVID19.dto.EventResponse;
import com.COVID19.exception.GeneralException;
import com.COVID19.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class APIEventController {

    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents() {
        // 예외처리를 하기위한 테스트 케이스
        // throw new HttpRequestMethodNotSupportedException("405 Error Test");

        /*
        List<EventResponse> eventResponses = eventService.getEvents(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime
        ).stream().map(EventResponse::from).toList();

        return APIDataResponse.of(eventResponses);
        */


        // 입출력 테스트 (APIEventController.java Test)
        return APIDataResponse.of(List.of(
                EventResponse.of(
                        1L,
                        "오후 운동",
                        EventStatus.OPENED,
                        LocalDateTime.of(2022, 1, 1, 13,15, 0),
                        LocalDateTime.of(2022, 1, 1, 13, 15, 0),
                        30,
                        50,
                        "마스크 꼭 착용하세요"
                )));
    }

    //@PostMapping("/place/{placeId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public APIDataResponse<Void> createEvent(
            @RequestBody EventRequest eventRequest
            ) {
        // 예외처리를 하기위한 테스트 케이스
        // throw new GeneralException("장군님");

        return APIDataResponse.empty();
    }

    @GetMapping("/events/{eventId}")
    public APIDataResponse<EventResponse> getEvent(@PathVariable Long eventId) {
        // 예외처리를 하기위한 테스트 케이스
        // throw new RuntimeException("런타임 에러");
        // return "eventId : "+eventId;

        if (eventId.equals(2L)) {
            return APIDataResponse.empty();
        }

        // 입출력 테스트 (APIEventController.java Test)
        return APIDataResponse.of(EventResponse.of(
                        1L,
                        "오후 운동",
                        EventStatus.OPENED,
                        LocalDateTime.of(2022, 1, 1, 13,15, 0),
                        LocalDateTime.of(2022, 1, 1, 13, 15, 0),
                        30,
                        50,
                        "마스크 꼭 착용하세요"
                ));
    }

    @PutMapping("/events/{eventId}")
    public APIDataResponse<Void> modifyEvent(
            @PathVariable Integer eventId,
            @RequestBody EventRequest eventRequest
            ) {
        return APIDataResponse.empty();
    }

    @DeleteMapping("/events/{eventId}")
    public APIDataResponse<Void> removeEvent(@PathVariable Integer eventId) {
        return APIDataResponse.empty();
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
