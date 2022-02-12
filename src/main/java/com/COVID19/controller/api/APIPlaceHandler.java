package com.COVID19.controller.api;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.List;

@Component
public class APIPlaceHandler {

    public ServerResponse getPlaces(ServerRequest request) {
        return ServerResponse.ok().body(List.of("place1", "place2"));
    }

    public ServerResponse createPlace(ServerRequest request) {
        /* resource를 생성하는데 성공했을 때 쓰는 status code를 사용하는 경우 (단, 인자값은 필수)*/
        return ServerResponse.created(URI.create("/api/places/1")).body(true);
    }

    public ServerResponse getPlace(ServerRequest request) {
        return ServerResponse.ok().body("place : "+ request.pathVariable("placeId"));
    }

    public ServerResponse modifyPlaces(ServerRequest request) {
        return ServerResponse.ok().body(true);
    }

    public ServerResponse removePlaces(ServerRequest request) {
        return ServerResponse.ok().body(true);
    }
}
