package com.COVID19.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(0, ErrorCategory.NORMAL, "Ok"),

    BAD_REQUEST(10000, ErrorCategory.CLIENT_SIDE, "Bad request"),
    SPRING_BAD_REQUEST(10001, ErrorCategory.CLIENT_SIDE, "Spring-detected bad request"),
    VALIDATION_ERROR(10002, ErrorCategory.CLIENT_SIDE, "Validation Error"),

    INTERNAL_ERROR(20000, ErrorCategory.SERVER_SIDE, "Internal error"),
    SPRING_INTERNAL_ERROR(20001, ErrorCategory.SERVER_SIDE, "Spring-detected internal error"),
    DATA_ACCESS_ERROR(20002, ErrorCategory.SERVER_SIDE, "Data access error");

    private final Integer code;
    private final ErrorCategory errorCategory;
    private final String message;

    // 기본으로 예외 메시지 전달
    public String getMessage(Exception e) {
        return getMessage(this.getMessage() + " - " + e.getMessage());
    }

    // 사용자가 직접 메시지 입력한 경우
    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public boolean isClientSideError() {
        return this.getErrorCategory() == ErrorCategory.CLIENT_SIDE;
    }

    public boolean isServerSideError() {
        return this.getErrorCategory() == ErrorCategory.SERVER_SIDE;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }

    public enum ErrorCategory {
        NORMAL, CLIENT_SIDE, SERVER_SIDE
    }
}
