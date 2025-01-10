package TImeCAlling.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PushMessageSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer offset;

    @Column(length = 20)
    private String body;

    @Column(length = 20)
    private String music;

    @Column(length = 2083)
    private String musicUrl;

    @Column
    private Boolean isActive;
}