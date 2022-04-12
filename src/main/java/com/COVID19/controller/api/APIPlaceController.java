package com.COVID19.controller.api;

import com.COVID19.constant.PlaceType;
import com.COVID19.dto.APIDataResponse;
import com.COVID19.dto.PlaceDTO;
import com.COVID19.dto.PlaceRequest;
import com.COVID19.dto.PlaceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated
//@RestController
//@RequestMapping("/api")
public class APIPlaceController {

    @GetMapping("/places")
    public APIDataResponse<List<PlaceResponse>> getPlaces() {
        return APIDataResponse.of(List.of(PlaceResponse.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "경기도 수원시 매영로",
                "010-3681-0008",
                20,
                "신규 개장"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/places")
    public APIDataResponse<Void> createPlace(@RequestBody PlaceRequest placeRequest) {
        return APIDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceResponse> getPlace(@PathVariable Integer placeId) {
        // 테스트를 위한 임시 if 분기처리
        if (placeId.equals(2)) {
            return APIDataResponse.empty();
        }

        return APIDataResponse.of(PlaceResponse.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "경기도 수원시 매영로",
                "010-3681-0008",
                20,
                "신규 개장"
        ));
    }

    @PutMapping("/places/{placeId}")
    public APIDataResponse<Void> modifyPlace(
            @PathVariable Integer placeId,
            @RequestBody PlaceRequest placeRequest
    ) {
        return APIDataResponse.empty();
    }

    @DeleteMapping("/places/{placeId}")
    public APIDataResponse<Void> removePlace(@PathVariable Integer placeId) {
        return APIDataResponse.empty();
    }
}
