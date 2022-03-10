package com.COVID19.dto;

import com.COVID19.constant.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class APIDataResponse<T> extends APIErrorResponse{

    /*
     * Generic 을 사용하면 Type safe 이 된다.
     *
     * Object data 는 성공 데이터를 반환하기 위한 필드이다.
     * 성공과 실패 코드들을 함께 가지고 있을 경우 JSON 포멧이 동일해져 사용자측에서 안심하고 사용 가능하다.
     * Controller에서 Map과 같은 형태로 JSON을 리턴하는 경우 키를 모두 확인해야되는 불편함을 지니게 되지만,
     * 클래스를 설계해서 보내주는 것이 효율적이다.
     */
    private final T data;

    /* // 정상 상황을 가정하여 만든 메소드라서 메소드 파라미터로 예외 체크하는 파라미터를 제외가 필요하여 수정
    private APIDataResponse(boolean success, Integer errorCode, String message, Object data) {
        super(success, errorCode, message);
        this.data=data;
    }

    public static APIDataResponse of(boolean success, Integer errorCode, String message, Object data) {
        return new APIDataResponse(success, errorCode, message, data);
    }
    */
    private APIDataResponse(T data) {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    public static <T> APIDataResponse<T> of(T data) {
        return new APIDataResponse(data);
    }

    // 데이터가 없는 경우
    public static <T> APIDataResponse<T> empty() {
        return new APIDataResponse<>(null);
    }
}
