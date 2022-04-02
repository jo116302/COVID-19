package com.COVID19.constant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.*;

/*
 * ErrorCode.java 를 테스트하는 테스트 클래스이다.
 */

class ErrorCodeTest {

    /*
     * 파라미터라이즈(@ParameterizedTest) 테스트
     *  @Test 어노테이션의 동작도 포함하고 있기 때문에 @ParameterizedTest 와는 함께 사용되지 않는다.
     *
     * @MethodSource 입력 소스로 메소드를 사용하겠다는 선언하는 어노테이션이다.
     *   - 동일한 검사에서 다양한 입력값에 따른 결과값이 적합하게 도출되는지 확인하기 위한 방법으로 사용된다.
     *   - 테스트 메소드 명과 동일한 메소드를 오버로딩형식으로 static 형태로 작성하게 된다.
     *   - @MethodSource() 에서 파라미터 값으로 문자열을 주게 되면 오버로딩이 아닌 입력된 메소드명으로 작성된 메소드를 설정할 수 있다.
     */
    @ParameterizedTest(name = "[{index}] \"{0}\" -----> \"{1}\"")
    @MethodSource
    @DisplayName("예외를 받으면, 예외 메시지가 표시된 메시지 출력")
    // @Test
    void givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage(ErrorCode sut, String expected) {
        // Given
        Exception e = new Exception("This is test message.");

        // When
        String result = sut.getMessage(e);

        // Then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage() {
        /*
         * arguments()의 첫번째 인자 값이 입력되면, 두번째 파라미터로 출력되야한다는 것을 의미
         */
        return Stream.of(
                arguments(ErrorCode.OK, "Ok - This is test message."),
                arguments(ErrorCode.BAD_REQUEST, "Bad request - This is test message."),
                arguments(ErrorCode.SPRING_BAD_REQUEST, "Spring-detected bad request - This is test message."),
                arguments(ErrorCode.VALIDATION_ERROR, "Validation Error - This is test message."),
                arguments(ErrorCode.INTERNAL_ERROR, "Internal error - This is test message."),
                arguments(ErrorCode.SPRING_INTERNAL_ERROR, "Spring-detected internal error - This is test message."),
                arguments(ErrorCode.DATA_ACCESS_ERROR, "Data access error - This is test message.")
        );
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" -----> \"{1}\"")
    @MethodSource
    @DisplayName("에러 메시지를 받으면, 해당 에러 메시지로 출력")
        // @Test
    void givenMessage_whenGettingMessage_thenReturnsMessage(String input, String expected) {
        // Given

        // When
        String result = ErrorCode.INTERNAL_ERROR.getMessage(input);

        // Then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> givenMessage_whenGettingMessage_thenReturnsMessage() {
        /*
         * arguments()의 첫번째 인자 값이 입력되면, 두번째 파라미터로 출력되야한다는 것을 의미
         */
        return Stream.of(
                arguments(null, ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("", ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("  ", ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("This is test message.", "This is test message.")
        );
    }

    @DisplayName("ToString() 호출 포멧")
    @Test
    void givenErrorCode_whenToString_thenReturnsSimplifiedToString() {
        // Given

        // When
        String result = ErrorCode.INTERNAL_ERROR.toString();

        // Then
        Assertions.assertThat(result).isEqualTo("INTERNAL_ERROR (20000)");
    }
}