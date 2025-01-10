package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import TImeCAlling.spring.domain.enums.*;
import TImeCAlling.spring.domain.mapping.ScheduleCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Schedule extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 20, nullable = false)
    private String name;
    
    @Column(length = 20)
    private String body;
    
    private LocalDateTime meetTime;
    
    @Column(length = 20, nullable = false)
    private String place;
    
    @Column(nullable = false)
    private Integer moveTime;
    
    @Column(nullable = false)
    private Boolean repeat;
    
    private LocalDate start;
    
    private LocalDate end;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10) default NONE")
    private Success success;
    
    @Enumerated(EnumType.STRING)
    private Spare spare;
    
    @Enumerated(EnumType.STRING)
    private Late late;
    
    @Enumerated(EnumType.STRING)
    private Reason reason;
    
    @Enumerated(EnumType.STRING)
    private External external;
    
    @Enumerated(EnumType.STRING)
    private Fit fit;
    
    private Long shareId;
    
    @Column(length = 15, nullable = false)
    private String longitude;
    
    @Column(length = 15, nullable = false)
    private String latitude;
    
    @ElementCollection
    @CollectionTable(name = "schedule_repeat_day", joinColumns = @JoinColumn(name = "schedule_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "repeat_day")
    private List<RepeatDay> repeatDays;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<ScheduleCategory> scheduleCategories = new ArrayList<>();
    
}
