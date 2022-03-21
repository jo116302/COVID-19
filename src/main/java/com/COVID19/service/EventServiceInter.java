package com.COVID19.service;

import com.COVID19.constant.EventStatus;
import com.COVID19.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface EventServiceInter {

    /**
     * 
     * @param placeId 장소 ID
     * @param eventName 이벤트 이름
     * @param eventStatus 이벤트 상태
     * @param eventStartDatetime 시작시간
     * @param eventEndDatetime 종료시간
     * @return
     */
    List<EventDTO> findEvent(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    );
}
