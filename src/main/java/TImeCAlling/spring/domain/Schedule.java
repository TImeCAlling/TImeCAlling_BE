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
    
    private Long shareId;
    
    @Column(length = 15, nullable = false)
    private String longitude;
    
    @Column(length = 15, nullable = false)
    private String latitude;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ElementCollection
    @CollectionTable(name = "category", joinColumns = @JoinColumn(name = "schedule_id"))
    private List<Category> categories = new ArrayList<>();
    
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Checklist> checklists = new ArrayList<>();
    
    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    private RecurringSchedule recurringSchedule;
    
    public void updateSchedule(
            String name,
            String body,
            LocalTime meetTime,
            String place,
            String longitude,
            String latitude,
            Integer moveTime,
            FreeTime freeTime,
            Boolean isRepeat,
            List<Category> categories,
            List<Checklist> checklists
    ) {
        this.name = name;
        this.body = body;
        this.meetTime = meetTime;
        this.place = place;
        this.longitude = longitude;
        this.latitude = latitude;
        this.moveTime = moveTime;
        this.freeTime = freeTime;
        this.isRepeat = isRepeat;
        this.categories = categories;
        this.checklists = checklists;
    }
    
    public void setRecurringSchedule(RecurringSchedule recurringSchedule) {
        this.recurringSchedule = recurringSchedule;
    }
}
