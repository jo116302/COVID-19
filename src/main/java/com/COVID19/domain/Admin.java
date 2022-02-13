package com.COVID19.domain;

import lombok.Data;

import java.time.LocalDateTime;

/*
 * @Data
 * - @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
 */
@Data
public class Admin {
    private long id;
    private String email;
    private String nickname;
    private String password;
    private String phoneNumber;
    private String memo;

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
