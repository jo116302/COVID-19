package com.COVID19.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * @Data
 * - @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
 */
@Getter
@ToString
@EqualsAndHashCode
@Table(indexes = {
        @Index(columnList = "phoneNumber"),
        @Index(columnList = "createAt"),
        @Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Admin {
    /*
     * 속성값이 unique한 값이 되야하는 경우 @Table, @Column 모두 기능지원을 한다.
     *  - @Table 복합키와 같은 복잡한 상태에서 설정해야되는 경우 주로 사용한다.
     *  - @Column 은 단순한 하나의 컬럼의 unique한 값이 필요한 경우 주로 사용한다.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false, unique = true)
    private String nickname;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    private String phoneNumber;

    @Setter
    private String memo;

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
