package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import TImeCAlling.spring.domain.enums.RepeatDay;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RecurringSchedule extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate start;
    
    @Column(nullable = false)
    private LocalDate end;
    
    @ElementCollection
    @CollectionTable(name = "schedule_repeat_day", joinColumns = @JoinColumn(name = "recurring_schedule_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "repeat_day")
    private List<RepeatDay> repeatDays;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    
    
}
