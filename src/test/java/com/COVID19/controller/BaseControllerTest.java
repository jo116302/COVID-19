package com.COVID19.controller;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BaseController.class)
class BaseControllerTest {

    /*
     * JUnit 테스트가 실행될 때마다 직전에 수행
     */
    /*
    @BeforeEach
    void setUp() {
    }
    */

    /*
     * JUnit 테스트가 실행될 때마다 직후에 수행
     */
    /*
    @AfterEach
    void tearDown() {
    }
    */

    // @AutoConfigureMockMvc 을 사용했기 때문에 @Autowired 로 자동으로 주입이 가능하다.
    @Autowired
    private MockMvc mvc;

    /*
     * 테스트하는 Controller의 메소드 명이나 파라미터명으로하는 것은 추천하지 않는다.
     * 해당 메소드명이 수정된다면 매칭이 안이루어지기 때문에 다른 사람이 봤을 때 찾기 힘들다.
     *
     * 원하는 테스트의 과정을 테스트 메소드 명으로 작성하기도한다.
     * ex) givenNothing_whenRequestingRootPage_thenReturnsIndexPage()
     * 
     * 또한, @DisplayName 를 사용해도 좋다.
     */
    @DisplayName("[View] [GET] 기본 페이지 요청")
    @Test
    void givenNothing_whenRequestingRootPage_thenReturnsIndexPage() throws Exception {
        // Given

        // When & Then 호출과 결과를 한번에 수행하는 방법 - 주로 사용하며 분리는 특정 목적이 있을 때만 사용
        /*
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.TEXT_HTML))  - ContentType으로 Text_html로 반드시 일치
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))  // ContentType으로 Text_html 이 존재하는지 체크
                .andExpect(content().string(containsString("This is root Page.")))  // string은 body를 검사
                .andExpect(view().name("index"))
                .andDo(print());
         */


        // 호출과 결과를 분리하는 방법
        // When
        ResultActions result = mvc.perform(get("/"));

        // Then
        result
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.TEXT_HTML))  - ContentType으로 Text_html로 반드시 일치
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))  // ContentType으로 Text_html 이 존재하는지 체크
                .andExpect(content().string(containsString("This is root Page.")))  // string은 body를 검사
                .andExpect(view().name("index"))
                .andDo(print());
    }
}