package com.COVID19.dto;

import com.COVID19.constant.EventStatus;

import java.time.LocalDateTime;

public record EventResponse(
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDatetime,
        LocalDateTime eventEndDatetime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo
) {

    public static EventResponse of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventResponse(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public static EventResponse from(EventDTO eventDto) {
        if (eventDto == null) { return null; }
        return EventResponse.of(
                eventDto.placeId(),
                eventDto.eventName(),
                eventDto.eventStatus(),
                eventDto.eventStartDatetime(),
                eventDto.eventEndDatetime(),
                eventDto.currentNumberOfPeople(),
                eventDto.capacity(),
                eventDto.memo()
        );
    }
}
