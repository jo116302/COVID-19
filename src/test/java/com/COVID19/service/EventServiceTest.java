package com.COVID19.service;

import com.COVID19.constant.EventStatus;
import com.COVID19.domain.Event;
import com.COVID19.dto.EventDTO;

import com.COVID19.repository.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

/*
 * @SpringBootTest를 띄워 @Autowired를 사용하게 되면 다른 Bean까지 등록 대기해야되기 때문에
 * 빠른확인을 하기 위해서 필요한 EventService만을 @BeforeEach 어노테이션을 사용하여 의존성 주입한다.
 *
 * @ExtendWith 를 사용하여 목키토를 사용할 수 있게 작성한다.
 *   - Mocking을 통해서 테스트 코드에서 원하는 원하는 데이터가 리턴 값으로 받은 것처럼 처리해 준다.
 */

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    // S:@ExtendWith(MockitoExtension.class) 사용하기 이전의 테스트 구간
    /*
    private EventService sut;

    @BeforeEach
    void setUp() {
        sut = new EventService();
    }
    */
    // E:@ExtendWith(MockitoExtension.class) 사용하기 이전의 테스트 구간

    // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    @InjectMocks private EventService sut;
    @Mock private EventRepository eventRepository;
    // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

    /*
     * 리스트 조회
     */
    @DisplayName("검색 조건 없이 이벤트 검색하면, 전체 결과를 출력하여 보여준다.")
    @Test
    void giveNothing_whenSearchingEvent_thenReturnEntireEventList() {

        // Given
        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.findEvents(
                null, null,null,null,null
        )).willReturn(List.of(
                createEventDTO(1L, "오전 운동", true),
                createEventDTO(1L, "오후 운동", false)
        ));
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        List<EventDTO> list = sut.getEvents(
                null, null, null, null, null
        );

        // Then
        assertThat(list).hasSize(2);

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().findEvents(null,null,null,null,null);
        //verify(eventRepository).findEvents(null,null,null,null,null);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("검색 조건과 함께 이벤트 검색하면, 전체 결과를 출력하여 보여준다.")
    @Test
    void giveSearchParams_whenSearchingEvent_thenReturnEventList() {

        // Given
        Long placeId = 1L;
        String eventName = "오전 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDatetime = LocalDateTime.of(
                2022, 1, 1, 0, 0, 0, 0
        );
        LocalDateTime eventEndDatetime = LocalDateTime.of(
                2022, 2, 1, 0, 0, 0, 0
        );

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.findEvents(
                placeId, eventName,eventStatus,eventStartDatetime,eventEndDatetime
        )).willReturn(List.of(
                createEventDTO(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime)
        ));
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        List<EventDTO> list = sut.getEvents(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime
        );

        // Then
        assertThat(list)
                .hasSize(1)
                .allSatisfy(event -> {
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);
                    assertThat(event.eventStartDatetime()).isAfterOrEqualTo(eventStartDatetime);
                    assertThat(event.eventStartDatetime()).isBeforeOrEqualTo(eventStartDatetime);
                });

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
        //verify(eventRepository).findEvents(1L, "오후 운동", eventStatus, eventStartDatetime, eventEndDatetime);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다.")
    @Test
    void getEventID_whenSearchingExistingEvent_thenReturnsEvent() {

        // Given
        long eventId = 1L;
        EventDTO eventDTO = createEventDTO(1L, "오전 운동", true);

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDTO));
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).hasValue(eventDTO);

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().findEvent(eventId);
        //verify(eventRepository).findEvent(eventId);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("이벤트 ID로 이벤트를 조회하면, 빈 정보를 출력하여 보여준다.")
    @Test
    void giveEventId_whenSearchingNonexistentEvent_thenReturnEmptyOptional() {

        // Given
        long eventId = 2L;

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).isEmpty();

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().findEvent(eventId);
        //verify(eventRepository).findEvent(eventId);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("이벤트 정보를 제공하면, 이벤트를 생성하고 결과를 true로 보여준다.")
    @Test
    void giveEvent_whenCreating_thenCreatsEventAndReturnsTrue() {

        // Given
        EventDTO dto = createEventDTO(1L, "오후 운동", false);

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.insertEvent(dto)).willReturn(true);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        boolean result = sut.createEvent(dto);

        // Then
        assertThat(result).isTrue();

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().insertEvent(dto);
        //verify(eventRepository).insertEvent(dto);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("이벤트 정보를 제공하지 않으면, 생성 중단하고 결과를 false로 보여준다.")
    @Test
    void giveNothing_whenCreating_thenAbortCreatingAndReturnsFalse() {

        // Given

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.insertEvent(null)).willReturn(false);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        boolean result = sut.createEvent(null);

        // Then
        assertThat(result).isFalse();

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().insertEvent(null);
        //verify(eventRepository).insertEvent(null);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("이벤트 ID와 정보를 제공하면, 이벤트 정보를 변경하고 결과를 true로 보여준다.")
    @Test
    void giveEventIdAndItsInfo_whenModifying_thenModifiesEventAndReturnsTrue() {

        // Given
        long eventId = 1L;
        EventDTO dto = createEventDTO(1L, "오후 운동", false);

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.updateEvent(eventId, dto)).willReturn(true);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        boolean result = sut.modifyEvent(eventId, dto);

        // Then
        assertThat(result).isTrue();

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().updateEvent(eventId, dto);
        //verify(eventRepository).updateEvent(eventId, dto);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("이벤트 ID정보를 제공하지 않으면, 이벤트 정보 변경을 중단하고 결과를 false로 보여준다.")
    @Test
    void giveEventId_whenModifying_thenAbortModifyingAndReturnsFalse() {

        // Given
        EventDTO dto = createEventDTO(1L, "오후 운동", false);

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.updateEvent(null, dto)).willReturn(false);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        boolean result = sut.modifyEvent(null, dto);

        // Then
        assertThat(result).isFalse();

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().updateEvent(null, dto);
        //verify(eventRepository).updateEvent(null, dto);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("이벤트 ID정보를 제공하고 정보를 제공하지 않으면, 이벤트 정보 변경을 중단하고 결과를 false로 보여준다.")
    @Test
    void giveEventIdOnly_whenModifying_thenAbortModifyingAndReturnsFalse() {

        // Given
        long eventId = 1L;

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.updateEvent(eventId, null)).willReturn(false);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        boolean result = sut.modifyEvent(eventId, null);

        // Then
        assertThat(result).isFalse();

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().updateEvent(eventId, null);
        //verify(eventRepository).updateEvent(eventId, null);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("이벤트 ID정보를 제공하면, 이벤트 정보 삭제하고 결과를 true로 보여준다.")
    @Test
    void giveEventId_whenDeleting_thenDeletesEventAndReturnsTrue() {

        // Given
        long eventId = 1L;

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.deleteEvent(eventId)).willReturn(true);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        boolean result = sut.removeEvent(eventId);

        // Then
        assertThat(result).isTrue();

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().deleteEvent(eventId);
        //verify(eventRepository).deleteEvent(eventId);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    @DisplayName("이벤트 ID정보를 제공하지 않으면, 이벤트 정보 삭제를 중단하고 결과를 false로 보여준다.")
    @Test
    void giveNothing_whenDeleting_thenDeletesAbortEventAndReturnsFalse() {

        // Given

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // 기존 Mockito의 When과 동일한 기능
        given(eventRepository.deleteEvent(null)).willReturn(false);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간

        // When
        boolean result = sut.removeEvent(null);

        // Then
        assertThat(result).isFalse();

        // S:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
        // verify() 목키토로 한번 호출진 요청을 확인하는 용도로 사용된다 (then()과 verify()는 동일한 기능 수행)
        then(eventRepository).should().deleteEvent(null);
        //verify(eventRepository).deleteEvent(null);
        // E:@ExtendWith(MockitoExtension.class) 사용한 테스트 구간
    }

    /*
     * 테스트 더미를 만드는 메소드 - 테스트에 필요한 메서드만 지정 가능
     */
    private EventDTO createEventDTO(
            long placeId,
            String eventName,
            boolean isMorning
    ) {
        String hourStart = isMorning ? "09" : "13";
        String hourEnd = isMorning ? "12" : "16";

        return createEventDTO(
                placeId,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.parse("2022-01-01T%s:00:00".formatted(hourStart)),
                LocalDateTime.parse("2022-01-01T%s:00:00".formatted(hourEnd))
        );
    }

    private EventDTO createEventDTO(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        return EventDTO.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                0,
                24,
                "마스크 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}