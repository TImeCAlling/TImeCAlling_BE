package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import TImeCAlling.spring.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FreeTime freeTime;
    
    @Column(nullable = false)
    private LocalTime meetTime;
    
    @Column(length = 20, nullable = false)
    private String place;
    
    @Column(nullable = false)
    private Integer moveTime;
    
    @Column(nullable = false)
    private Boolean isRepeat;
    
    private LocalDate start;
    
    private LocalDate end;
    
    private Long shareId;
    
    @Column(length = 15, nullable = false)
    private String longitude;
    
    @Column(length = 15, nullable = false)
    private String latitude;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();
    
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Checklist> checklists = new ArrayList<>();
    
    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    private RecurringSchedule recurringSchedule;

    public void setBody(String body) { this.body = body; }
    public void setMoveTime(Integer moveTime) { this.moveTime = moveTime; }
    public void setFreeTime(FreeTime freeTime) { this.freeTime = freeTime; }
    public void setIsRepeat(Boolean isRepeat) { this.isRepeat = isRepeat; }
}
