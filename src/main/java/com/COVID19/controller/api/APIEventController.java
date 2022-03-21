package com.COVID19.controller.api;

import com.COVID19.constant.EventStatus;
import com.COVID19.dto.APIDataResponse;
import com.COVID19.dto.EventRequest;
import com.COVID19.dto.EventResponse;
import com.COVID19.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class APIEventController {

    private final EventService eventService;

    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents(
            // S:APIEventControllerTest에서 Mock으로 테스트 작성하면서 사용되는 파라미터
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime
            // E:APIEventControllerTest에서 Mock으로 테스트 작성하면서 사용되는 파라미터
    ) {
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


        // S:입출력 테스트 (APIEventController.java Test) - APIEventControllerTest에서 Mock으로 테스트 작성하면서 필요 없어진 코드
        /*
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

        */
        // E:입출력 테스트 (APIEventController.java Test) - APIEventControllerTest에서 Mock으로 테스트 작성하면서 필요 없어진 코드

        // S:APIEventControllerTest에서 Mock으로 테스트 작성하면서 사용되는 코드
        /*
         * List의 제네릭과 eventService.getEvent()의 데이터 타입이 일치하지 않는 형태로 작성되어있다.
         * 이를 해결하기위해서는 데이터 타입과 List의 제네릭과 일치 시켜줄 필요성이 있다.
         * 이럴 경우 제네릭과 데이터 타입을 맞추기 위한 코드작성은 EventResponse에서 작성하게 된다.
         * 컨트롤에서 작성하기에는 컨트롤의 기능 범위가 광범위해지는 것을 피하기 위함이며,
         * EventResponse는 파라미터 값이 어떻든 원하는 결과 값과 데이터 형태를 제공하는 것이 적합하기 때문이다.
         * EventResponse record의 from 메서드로 작성됐다.
         */
        List<EventResponse> response = eventService.getEvents(
                placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime
        ).stream().map(EventResponse::from).toList();

        return APIDataResponse.of(response);
        // E:APIEventControllerTest에서 Mock으로 테스트 작성하면서 사용되는 코드
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
