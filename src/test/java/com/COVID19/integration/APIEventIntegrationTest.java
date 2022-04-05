package com.COVID19.integration;

import com.COVID19.constant.ErrorCode;
import com.COVID19.constant.EventStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/*
 * 다른 테스트들이 단위테스트였다면, 해당 클래스는 통합테스트를 작성 예제를 다룬 내용이다.
 *
 * @AutoConfigureMockMvc
 *   - @SpringBootTest에서는 별도로 MockMvc 의존성 주입이 불가능하기 때문에 이를 보완하기 위해서 사용되는 어노테이션이다.
 */

@AutoConfigureMockMvc
@SpringBootTest
public class APIEventIntegrationTest {

    @Autowired private MockMvc mvc;

    @Test
    void test() throws Exception {

        // When & Then
        mvc.perform(
                get("/api/events")
                        .queryParam("placeId", "1")
                        .queryParam("eventName", "오후 운동")
                        .queryParam("eventStatus", EventStatus.OPENED.name())
                        .queryParam("eventStartDatetime", "2022-01-01T00:00:00")
                        .queryParam("eventEndDatetime", "2022-01-01T00:00:00")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andDo(print());
    }
}
