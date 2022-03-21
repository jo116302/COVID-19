package com.COVID19.controller.api;

import com.COVID19.constant.ErrorCode;
import com.COVID19.constant.EventStatus;
import com.COVID19.dto.EventDTO;
import com.COVID19.dto.EventResponse;
import com.COVID19.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[API] [View] 컨트롤러 - 이벤트")
@WebMvcTest(APIEventController.class)
public class APIEventControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    /*
     * @MockBean을 사용하지 않을 경우 실제 Controller에 존재하는 실제 Bean을 주입하도록 하면(Controller와 Service단을 실행) 통합테스트라고 봐야한다.
     * 단위테스트를 수행하고 싶은 경우 @MockBean을 사용하여 서비스단의 처리 과정은 제외한 입력과 출력의 형태로만 확인할 수 있다.
     */
    @MockBean private EventService eventService;

    public APIEventControllerTest (
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
    ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[API] [GET] 이벤트 리스트 조회 + 검색 파라미터")
    @Test
    void givenParameters_whenRequestingEvents_thenReturnsListOfEventsInStandardResponse() throws Exception {

        // Given
        // S:Mocking 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventService.getEvents(any(), any(), any(), any(), any())).willReturn(List.of(createEventDTO()));
        // E:Mocking 사용한 테스트 구간

        // When & Then
        mvc.perform(
                get("/api/events")
                        .queryParam("placeId", "1")
                        .queryParam("eventName", "운동")
                        .queryParam("eventStatus", EventStatus.OPENED.name())
                        .queryParam("eventStartDatetime", "2022-01-01T00:00:00")
                        .queryParam("eventEndDatetime", "2022-01-01T00:00:00")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeId").value(1L))
                .andExpect(jsonPath("$.data[0].eventName").value("오후 운동"))
                .andExpect(jsonPath("$.data[0].eventStatus").value(EventStatus.OPENED.name()))
                .andExpect(jsonPath("$.data[0].eventStartDatetime").value(
                        LocalDateTime
                                .of(2022, 1, 1, 13, 15, 0)
                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                ))
                .andExpect(jsonPath("$.data[0].eventEndDatetime").value(
                        LocalDateTime
                                .of(2022, 1, 1, 13, 15, 0)
                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                ))
                .andExpect(jsonPath("$.data[0].currentNumberOfPeople").value(30))
                .andExpect(jsonPath("$.data[0].capacity").value(50))
                .andExpect(jsonPath("$.data[0].memo").value("마스크 꼭 착용하세요"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        // S:Mocking 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventService).should().getEvents(any(), any(), any(), any(), any());
        //verify(eventRepository).findEvents(null,null,null,null,null);
        // E:Mocking 사용한 테스트 구간
    }

    @DisplayName("[API] [GET] 이벤트 리스트 조회 - 잘못된 검색 파라미터")
    @Test
    void givenWrongParameters_whenRequestingEvents_thenReturnsFailedStandardResponse() throws Exception {

    }

    @DisplayName("[API][POST] 이벤트 생성")
    @Test
    void givenEvent_whenCreatingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        EventResponse eventResponse = EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2022, 1, 1, 13, 15, 0),
                LocalDateTime.of(2022, 1, 1, 13, 15, 0),
                30,
                50,
                "마스크 꼭 착용하세요"
        );

        mvc.perform(
                post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(eventResponse))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][GET] 단일 이벤트 조회 - 이벤트 있는 경우, 이벤트 데이터를 담은 표준 API 출력")
    @Test
    void givenEventId_whenRequestingExistentEvent_thenReturnsEventInStandardResponse() throws Exception {
        // Given
        long eventId = 1L;

        // When & Then
        mvc.perform(get("/api/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.placeId").value(1L))
                .andExpect(jsonPath("$.data.eventName").value("오후 운동"))
                .andExpect(jsonPath("$.data.eventStatus").value(EventStatus.OPENED.name()))
                .andExpect(jsonPath("$.data.eventStartDatetime").value(LocalDateTime
                        .of(2022, 1, 1, 13, 15, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data.eventEndDatetime").value(LocalDateTime
                        .of(2022, 1, 1, 13, 15, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data.currentNumberOfPeople").value(30))
                .andExpect(jsonPath("$.data.capacity").value(50))
                .andExpect(jsonPath("$.data.memo").value("마스크 꼭 착용하세요"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][GET] 단일 이벤트 조회 - 이벤트 없는 경우, 빈 표준 API 출력")
    @Test
    void givenEventId_whenRequestingNonexistentEvent_thenReturnsEmptyStandardResponse() throws Exception {
        // Given
        long eventId = 2L;

        // When & Then
        mvc.perform(get("/api/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][PUT] 이벤트 변경")
    @Test
    void givenEvent_whenModifyingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        long eventId = 1L;
        EventResponse eventResponse = EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        );

        // When & Then
        mvc.perform(
                put("/api/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(eventResponse))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][DELETE] 이벤트 삭제")
    @Test
    void givenEvent_whenDeletingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        long eventId = 1L;

        // When & Then
        mvc.perform(delete("/api/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    /*
     * 테스트 더미를 만드는 메소드 - 테스트에 필요한 메서드만 지정 가능
     */

    private EventDTO createEventDTO() {
        return EventDTO.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(
                        2022, 1, 1, 13, 15, 0
                ),
                LocalDateTime.of(
                        2022, 1, 1, 13, 15, 0
                ),
                30,
                50,
                "마스크 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
