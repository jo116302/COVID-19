package com.COVID19.exception;

import com.COVID19.constant.ErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    /*
     * 생성자로 인스턴스 생성시 생성자 메소드 내에서 초기화 진행한다.
     */
    private  final ErrorCode errorCode;

    public GeneralException() {
        super(ErrorCode.INTERNAL_ERROR.getMessage());
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(String message) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(message));
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(String message, Throwable cause) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(message), cause);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(Throwable cause) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(cause));
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /*
     * 특정 에러를 커스텀 되어졌을 경우 호출되며, 커스텀된 적합한 에러로 연결하는 매개체 메소드이다.
     */
    public GeneralException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
