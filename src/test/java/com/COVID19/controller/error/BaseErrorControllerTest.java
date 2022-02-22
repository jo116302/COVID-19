package com.COVID19.controller.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BaseErrorController.class)
class BaseErrorControllerTest {

    private final MockMvc mvc;

    public BaseErrorControllerTest (@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[View] [GET] 에러 페이지 - 페이지 없음")
    @Test
    void givenWrongUri_whenRequestingPage_thenReturns404ErrorPage() throws Exception {
        // Given

        // When & Then 호출과 결과를 한번에 수행하는 방법 - 주로 사용하며 분리는 특정 목적이 있을 때만 사용

        mvc.perform(get("/wrong-uri"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}