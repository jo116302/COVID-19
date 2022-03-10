package com.COVID19.dto;

import com.COVID19.constant.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class APIDataResponseTest {

    @DisplayName("문자열 데이터가 주어지면, 표준 성공 응답율 생성한다.")
    @Test
    void givenStringData_whenCreatingResponse_thenReturnSuccessfulResponse() {
        // Given
        String data = "Test data";

        // When
        APIDataResponse<String> response = APIDataResponse.of(data);

        // Then
        Assertions.assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", data);
    }

    @DisplayName("문자열 데이터 없을 때, 비어있는 표준 성공 응답율 생성한다.")
    @Test
    void givenNothing_whenCreatingResponse_thenReturnEmptySuccessfulResponse() {
        // Given
        String data = "Test data";

        // When
        APIDataResponse<String> response = APIDataResponse.empty();

        // Then
        Assertions.assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", null);
    }
}