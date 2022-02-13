package com.COVID19.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminPlaceMap {

    private long id;
    private long adminId;
    private long placeId;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
