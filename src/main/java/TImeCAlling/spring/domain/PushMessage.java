package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.*;
import org.aspectj.bridge.Message;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PushMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime alarmTime;  // 유저의 평균 준비시간과 기준시간 계산값
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus messageStatus;  // PENDING, SENT, FAILED
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Boolean repeat;
}