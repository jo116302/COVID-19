package com.COVID19.controller.api;

import com.COVID19.constant.ErrorCode;
import com.COVID19.constant.PlaceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(APIPlaceController.class)
@AutoConfigureMockMvc
class APIPlaceControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("[API] [GET] 장소 리스트 조회")
    @Test
    void givenNothing_whenRequestingPlaces_thenReturnsListOfPlacesInStandardResponse() throws Exception {
        // Given

        // When & then
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

    @DisplayName("[API] [GET] 단일 장소 조회 - 장소 존재하는 경우")
    @Test
    void givenPlaceAndItsId_whenRequestingPlace_thenReturnsPlaceInStandardResponse() throws Exception {
        // Given
        int placeId = 1;

        // When & then
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

    @DisplayName("[API] [GET] 단일 장소 조회 - 장소가 존재하지 않는 경우")
    @Test
    void givenPlaceId_whenRequestingPlace_thenReturnsEmptyStandardResponse() throws Exception {
        // Given
        int placeId = 2;

        // When & then
        mvc.perform(get("/api/places/" + placeId))
                .andExpectAll(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }
}