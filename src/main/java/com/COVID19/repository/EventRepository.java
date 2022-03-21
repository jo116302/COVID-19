package com.COVID19.repository;

import com.COVID19.constant.EventStatus;
import com.COVID19.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/*
 * Class로 만들어도 되나 JPA를 사용할 것을 염두하여 Interface로 작성
 * 
 * Interface이기 때문에 Spring Bean으로 등록 불가하기 때문에 Bean 생성을 대체하기 위한
 * Class인 config/RepositoryConfig 에서 객체 생성 후 반환하는 메서드 작성
 */

// TODO : 인스턴스 생성 편의를 위해 임시로 default 사용, repository layer 구현이 완성되면 삭제할 것
public interface EventRepository {

    /*
     * default 로 미리 구현하여 객체 생성할 때 익명 클래스 구현했기 때문에 생성 시 깔끔하게 작성 가능하다.
     */
    default List<EventDTO> findEvents(
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDatetime,
        LocalDateTime eventEndDatetime
    ) {
        return List.of();
    }

    default Optional<EventDTO> findEvent(Long eventId) {
        return Optional.empty();
    }

    default boolean insertEvent(EventDTO eventDTO) {
        return true;
    }

    default boolean updateEvent(Long eventId, EventDTO eventDTO) {
        return false;
    }

    default boolean deleteEvent(Long eventId) {
        return false;
    }
}
