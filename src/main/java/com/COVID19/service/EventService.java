package com.COVID19.service;

import com.COVID19.constant.EventStatus;
import com.COVID19.dto.EventDTO;
import com.COVID19.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
 * @Service
 *   - 서비스로 등록시키는 어노테이션이며, 해당 서비스가 Bean 으로 등록된다
 *
 * Event InterFace 는 Event(EventServiceInter)만 생성해보고 실습은 Event Class 하나로 수행한다.
 */
@RequiredArgsConstructor
@Service
public class EventService {

    /*
     * Data는 Data Model Layer에서 호출하는 방법으로 Repository를 사용한다.
     */
    private final EventRepository eventRepository;

    public List<EventDTO> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        return eventRepository.findEvents(
            placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime
        );
    }

    public Optional<EventDTO> getEvent(Long eventId) {
        return eventRepository.findEvent(eventId);
    }

    public boolean createEvent(EventDTO eventDTO) {
        return eventRepository.insertEvent(eventDTO);
    }

    public boolean modifyEvent(Long eventId, EventDTO eventDTO) {
        return eventRepository.updateEvent(eventId, eventDTO);
    }

    public boolean removeEvent(Long eventId) {
        return eventRepository.deleteEvent(eventId);
    }
}
