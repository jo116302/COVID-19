package com.COVID19.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class APIDataResponse extends APIErrorResponse{

    /*
     * Object data 는 성공 데이터를 반환하기 위한 필드이다.
     * 성공과 실패 코드들을 함께 가지고 있을 경우 JSON 포멧이 동일해져 사용자측에서 안심하고 사용 가능하다.
     * Controller에서 Map과 같은 형태로 JSON을 리턴하는 경우 키를 모두 확인해야되는 불편함을 지니게 되지만,
     * 클래스를 설계해서 보내주는 것이 효율적이다.
     */
    private final Object data;

    private APIDataResponse(boolean success, Integer errorCode, String message, Object data) {
        super(success, errorCode, message);
        this.data=data;
    }

    public static APIDataResponse of(boolean success, Integer errorCode, String message, Object data) {
        return new APIDataResponse(success, errorCode, message, data);
    }
}
