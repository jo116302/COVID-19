package com.COVID19.dto;

import com.COVID19.constant.PlaceType;

public record PlaceDTO(
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        int capacity,
        String memo
) {
    public static PlaceDTO of (
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            int capacity,
            String memo
    ) {
        return new PlaceDTO(placeType, placeName, address, phoneNumber, capacity, memo);
    }
}