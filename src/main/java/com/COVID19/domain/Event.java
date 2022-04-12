package com.COVID19.domain;

import com.COVID19.constant.EventStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@Table(indexes = {
        @Index(columnList = "placeId"),
        @Index(columnList = "eventName"),
        @Index(columnList = "eventStartDatetime"),
        @Index(columnList = "eventEndDatetime"),
        @Index(columnList = "createAt"),
        @Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Event {

    /*
     * @GeneratedValue(strategy = GenerationType.IDENTITY) 키값의 자동 증가 autoincrement와 같은 기능을 수행한다.
     * @Setter 와 @Getter 모두 Class와 필드에 적용가능하다.
     * @Column 은 기본적으로 적용되기 때문에 설정을 안해도된다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(nullable = false)
    private long placeId;

    @Setter
    @Column(nullable = false)
    private String eventName;

    @Setter
    @Column(nullable = false, columnDefinition = "varchar default 'OPENED'")
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @Setter
    @Column(nullable = false, columnDefinition = "datetime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventStartDateTime;

    @Setter
    @Column(nullable = false, columnDefinition = "datetime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventEndDateTime;

    @Setter
    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer currentNumberOfPeople;

    @Setter
    @Column(nullable = false)
    private Integer capacity;

    @Setter
    private String memo;

    /*
     * @CreatedDate와 @LastModifiedDate는 데이터베이스에서 지원하는 기능을 사용하는 것이 아니라 자바 내에서의 기능이 동작하여 데이터베이스로 데이터를 저장하는 방식이다.
     * 하지만, @Column어노테이션에서 사용되는 'columnDefinition'와 관련된 속성은 별개로 데이터베이스의 스키마에 Default 값을 설정한것이다. 다만, 목표는 동일하다.
     * 제외한다면 @Column어노테이션의 'columnDefinition'를 제거하면 된다. 기능 명시를 위해서 작성한 것이기 때문이다.
     *
     * insertable, updatable 은 SQL insert, update 문이 동작하지 않게된다. 기본적으로 insert, update문으로 해당 컬럼이 안들어갈 경우 JPA는 null값으로 처리하고 notnull시에는 문제가 발생한다.
     * 이를 막기위해 default 값이 적용될 수 있도록하기 위해서는 false로 설정을 적용해야된다.
     */
    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    protected Event() {}

    protected Event(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        this.placeId = placeId;
        this.eventName = eventName;
        this.eventStatus = eventStatus;
        this.eventStartDateTime = eventStartDateTime;
        this.eventEndDateTime = eventEndDateTime;
        this.currentNumberOfPeople = currentNumberOfPeople;
        this.capacity = capacity;
        this.memo = memo;
    }

    public static Event of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new Event(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }
}
