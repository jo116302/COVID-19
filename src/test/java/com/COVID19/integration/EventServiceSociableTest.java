package com.COVID19.integration;

import com.COVID19.dto.EventDTO;
import com.COVID19.service.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
public class EventServiceSociableTest {

    @Autowired private EventService sut;

    @DisplayName("검색 조건 없이 이벤트 검색하면, 전체 결과를 출력하여 보여준다.")
    @Test
    void giveNothing_whenSearchingEvent_thenReturnEntireEventList() {
//https://sg-moomin.tistory.com/entry/available-expected-at-least-1-bean-which-qualifies-as-autowire-candidate-Dependency-annotations-%EC%98%A4%EB%A5%98-%ED%95%B4%EA%B2%B0%ED%95%B4%EB%B3%B4%EA%B8%B0

        // Given

        // When
        List<EventDTO> list = sut.getEvents(
                null, null, null, null, null
        );

        // Then
        assertThat(list).hasSize(2);
    }
}
