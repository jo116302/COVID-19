package com.COVID19.controller;

import com.COVID19.constant.PlaceType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    /*
     * Enum의 데이터를 가져오는 방식
     * 1) PlaceType.COMMON - Enum의 하나의 요소를 선택하여 호출하는 경우 Jackson이 Converter/직렬화를 수행한다.
     *
     * @RequestParam 은 기본적으로 컬렉션 데이터가 아닌 단일 객체의 경우에는 자동적으로 적용되어 생략 가능하다.
     *   - annotation 을 기입 유무의 차이점은 기입 시 @RequestParam(required = true)가 적용되며,
     *     기입하지 않은 경우 RequestParam(required = false) 형태로 적용된다.
     *   - 컬렉션 데이터(Map, List)를 입력하게 되는 경우 @ModelAttribute 등으로 인식하게 된다.
     *
     * @ModelAndView 은 EL로 값을 받아서 바로 사용할 수 있도록 지원한다. 사용방법으로는 다음과 같다.
     * 1. ModelAndView를 리턴할 때 'return new ModelAndView("admin/places", map)' 형태로 반환
     * 2. 메소드 내에 ModelAndView 객체(인스턴스)를 생성하여 값을 설정한다.
     *   - ModelAndView mv = new ModelAndView();
     *     mv.setViewName("admin/places");
     *     mv.addAllObjects(map);
     * 3. 파리미터로 ModelAndView 객체를 생성해 사용한다.
     *   - public ModelAndView adminPlaces (..., ModelAndView mv) {...ModelAndView 설정}
     */
    @GetMapping("/places")
    public ModelAndView adminPlaces (
            PlaceType placeType,
            String placeName,
            String address
            )
    {
        Map<String, Object> map = new HashMap<>();
        map.put("placeType", placeType);
        map.put("placeName", placeName);
        map.put("address", address);

        return new ModelAndView("admin/places", map);
    }

    @GetMapping("/places/{placeId}")
    public String adminPlaceDetail (@PathVariable Integer placeId) {
        return "admin/place-detail";
    }

    @GetMapping("/events")
    public String adminEvents () {
        return "admin/events";
    }

    @GetMapping("/events/{eventId}")
    public String adminEventDetail(@PathVariable Integer eventId) {
        return "admin/event-detail";
    }
}
