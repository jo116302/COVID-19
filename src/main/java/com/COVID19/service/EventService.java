package com.COVID19.service;

import com.COVID19.constant.ErrorCode;
import com.COVID19.constant.EventStatus;
import com.COVID19.dto.EventDTO;
import com.COVID19.exception.GeneralException;
import com.COVID19.repository.EventRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

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

    public List<EventDTO> getEvents(Predicate predicate) {
        try {
            return StreamSupport.stream(eventRepository.findAll(predicate).spliterator(), false)
                    .map(EventDTO::of)
                    .toList();
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public List<EventDTO> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        try{
            /*
            return eventRepository.findEvents(
                    placeId,
                    eventName,
                    eventStatus,
                    eventStartDatetime,
                    eventEndDatetime
            );
            */

            return null;
        } catch (Exception e) {
            /*
             * 에러가 발생했을 때 커스텀 되어진 에러형태로 출력 가능하도록 코드 작성
             *   - EventServiceTest.java 의 givenDataRelatedException_whenSearchingEvents_thenThrowsGeneralException() 메소드와 연동
             */
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<EventDTO> getEvent(Long eventId) {
        /*
        return eventRepository.findEvent(eventId);
        */

        try {
            return eventRepository.findById(eventId).map(EventDTO::of);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional
    public boolean createEvent(EventDTO eventDTO) {
        /*
        return eventRepository.insertEvent(eventDTO);
        */

        try {
            if (eventDTO == null) {
                return false;
            }

            eventRepository.save(eventDTO.toEntity());
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional
    public boolean modifyEvent(Long eventId, EventDTO eventDTO) {
        /*
        return eventRepository.updateEvent(eventId, eventDTO);
        */

        try {
            if (eventId == null || eventDTO == null) {
                return false;
            }

            eventRepository.findById(eventId)
                    .ifPresent(event -> eventRepository.save(eventDTO.updateEntity(event)));

            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean removeEvent(Long eventId) {
        /*
        return eventRepository.deleteEvent(eventId);
        */

        try {
            if (eventId == null) {
                return false;
            }

            eventRepository.deleteById(eventId);
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }
}
