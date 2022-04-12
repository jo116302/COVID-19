package com.COVID19.dto;

import com.COVID19.constant.EventStatus;
import com.COVID19.domain.Event;

import java.time.LocalDateTime;

public record EventDTO(
        Long id,
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDatetime,
        LocalDateTime eventEndDatetime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo,
        LocalDateTime createAt,
        LocalDateTime modifiedAt
) {
    public static EventDTO of(
            Long id,
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo,
            LocalDateTime createAt,
            LocalDateTime modifiedAt
    ) {
        return new EventDTO(
                id,
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo,
                createAt,
                modifiedAt
        );
    }

    public static EventDTO of(Event event) {
        return new EventDTO(
                event.getId(),
                event.getPlaceId(),
                event.getEventName(),
                event.getEventStatus(),
                event.getEventStartDateTime(),
                event.getEventEndDateTime(),
                event.getCurrentNumberOfPeople(),
                event.getCapacity(),
                event.getMemo(),
                event.getCreatedAt(),
                event.getModifiedAt()
        );
    }

    public Event toEntity() {
        return Event.of(
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

    public Event updateEntity(Event event) {
        if (placeId != null) { event.setPlaceId(placeId); }
        if (eventName != null) { event.setEventName(eventName); }
        if (eventStatus != null) { event.setEventStatus(eventStatus); }
        if (eventStartDatetime != null) { event.setEventStartDateTime(eventStartDatetime); }
        if (eventEndDatetime != null) { event.setEventEndDateTime(eventEndDatetime); }
        if (currentNumberOfPeople != null) { event.setCurrentNumberOfPeople(currentNumberOfPeople); }
        if (capacity != null) { event.setCapacity(capacity); }
        if (memo != null) { event.setMemo(memo); }

        return event;
    }
}
