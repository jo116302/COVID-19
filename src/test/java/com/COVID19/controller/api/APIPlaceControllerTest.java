package com.COVID19.controller.api;

import com.COVID19.constant.ErrorCode;
import com.COVID19.constant.PlaceType;
import com.COVID19.dto.PlaceRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[API] [View] 컨트롤러 - 장소")
@WebMvcTest(APIPlaceController.class)
// 의존성 주입에 final 과 생성자로 진행으로 수정하면서 주석처리
// @AutoConfigureMockMvc
class APIPlaceControllerTest {

    // @Autowired
    private final MockMvc mvc;
    private final ObjectMapper mapper;

    public APIPlaceControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
    ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }
    
    @DisplayName("[API] [GET] 장소 리스트 조회 - 장소 리스트 데이터를 담은 표준 API 출력")
    @Test
    void givenNothing_whenRequestingPlaces_thenReturnsListOfPlacesInStandardResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/places"))
                .andExpectAll(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data[0].placeName").value("랄라배드민턴장"))
                .andExpect(jsonPath("$.data[0].address").value("경기도 수원시 매영로"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("010-3681-0008"))
                .andExpect(jsonPath("$.data[0].capacity").value(20))
                .andExpect(jsonPath("$.data[0].memo").value("신규 개장"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API] [POST] 장소 생성")
    @Test
    void givenPlace_whenCreatingPlace_thenReturnSucessfulStandardResponse() throws Exception {
        // Given
        PlaceRequest placeRequest = PlaceRequest.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "경기도 수원시 매영로",
                "010-3681-0008",
                30,
                "신장 개장"
        );

        // When & Then
        mvc.perform(
                post("/api/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(placeRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API] [GET] 단일 장소 조회 - 장소 존재하는 경우, 장소 데이터를 담은 표준 API 출력")
    @Test
    void givenPlaceAndItsId_whenRequestingPlace_thenReturnsPlaceInStandardResponse() throws Exception {
        // Given
        long placeId = 1L;

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpectAll(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data.placeName").value("랄라배드민턴장"))
                .andExpect(jsonPath("$.data.address").value("경기도 수원시 매영로"))
                .andExpect(jsonPath("$.data.phoneNumber").value("010-3681-0008"))
                .andExpect(jsonPath("$.data.capacity").value(20))
                .andExpect(jsonPath("$.data.memo").value("신규 개장"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API] [GET] 단일 장소 조회 - 장소가 존재하지 않는 경우, 빈 표준 API 출력")
    @Test
    void givenPlaceId_whenRequestingPlace_thenReturnsEmptyStandardResponse() throws Exception {
        // Given
        long placeId = 2L;

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpectAll(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][PUT] 장소 변경")
    @Test
    void givenPlace_whenModifyingPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        long placeId = 1L;
        PlaceRequest placeRequest = PlaceRequest.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "서울시 강남구 강남대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        );

        // When & Then
        mvc.perform(
                put("/api/places/" + placeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(placeRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][DELETE] 장소 삭제")
    @Test
    void givenPlace_whenDeletingAPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        long placeId = 1L;

        // When & Then
        mvc.perform(delete("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }
}