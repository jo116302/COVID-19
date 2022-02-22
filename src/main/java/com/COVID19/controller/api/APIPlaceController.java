package com.COVID19.controller.api;

import com.COVID19.constant.PlaceType;
import com.COVID19.dto.APIDataResponse;
import com.COVID19.dto.PlaceDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIPlaceController {

    @GetMapping("/places")
    public APIDataResponse<List<PlaceDTO>> getPlaces() {
        return APIDataResponse.of(List.of(PlaceDTO.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "경기도 수원시 매영로",
                "010-3681-0008",
                20,
                "신규 개장"
        )));
    }

    @PostMapping("/places")
    public Boolean createPlace() {
        return false;
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceDTO> getPlace(@PathVariable Integer placeId) {
        // 테스트를 위한 임시 if 분기처리
        if (placeId.equals(2)) {
            return APIDataResponse.of(null);
        }

        return APIDataResponse.of(PlaceDTO.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "경기도 수원시 매영로",
                "010-3681-0008",
                20,
                "신규 개장"
        ));
    }

    @PostMapping("/places/{placeId}")
    public Boolean modifyPlace(@PathVariable Integer placeId) {
        return true;
    }

    @DeleteMapping("/places/{placeId}")
    public Boolean removePlace(@PathVariable Integer placeId) {
        return false;
    }
}
