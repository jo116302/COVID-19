package com.COVID19.controller.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class APIPlaceRouter {

    /*
     * S:APIPlaceRouter만 사용한 경우
     */

    /*
     * 정상적으로 등록된 경우 Debug에서 다음 로그를 확인할 수 있다. (localhost:[port]/actuator 에서는 확인 불가능)
     * [           main] o.s.w.s.f.support.RouterFunctionMapping  : 1 RouterFunction(s) in 'routerFunctionMapping'
     *
     * *.nest(path("/api/places"), [function] 은 Class의 RequestMapping과 동일한 기능 수행
     */

    /*
    @Bean
    public RouterFunction<ServerResponse> placeRouter() {

        return route().nest(path("/api/places"), builder -> builder
                .GET("", req -> ServerResponse.ok().body(List.of("place1", "place2")))
                .POST("", req -> ServerResponse.ok().body(true))
                .GET("/{placeId}", req -> ServerResponse.ok().body("place : "+ req.pathVariable("placeId")))
                .PUT("/{placeId}", req -> ServerResponse.ok().body(true))
                .DELETE("/{placeId}", req -> ServerResponse.ok().body(true))
        ).build();
    }
    */

    /*
     * E:APIPlaceRouter만 사용한 경우
     */

    /*
     * S:APIPlaceHandler 를 사용하여 작성한 경우
     */

    /*
     * Bean을 주입할 때 필드로 선언해도 되고 파라미터로 넣어줘도 가능하다.
     * 해당 Class에서는 파라미터로 넣어주기 때문에 주석처리
     *
     * private final APIPlaceHandler apiPlaceHandler;
     */

    @Bean
    public RouterFunction<ServerResponse> placeRouter(APIPlaceHandler apiPlaceHandler) {

        return route().nest(path("/api/places"), builder -> builder
                .GET("", apiPlaceHandler::getPlaces)
                .POST("", apiPlaceHandler::createPlace)
                .GET("/{placeId}", apiPlaceHandler::getPlace)
                .PUT("/{placeId}", apiPlaceHandler::modifyPlaces)
                .DELETE("/{placeId}", apiPlaceHandler::removePlaces)
        ).build();
    }

    /*
     * E:APIPlaceHandler 를 사용하여 작성한 경우
     */
}
