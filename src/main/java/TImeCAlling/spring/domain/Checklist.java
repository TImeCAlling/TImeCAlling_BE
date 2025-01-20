package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import TImeCAlling.spring.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Checklist extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Boolean isSuccess;
    
    @Enumerated(EnumType.STRING)
    private Spare spare;
    
    @Enumerated(EnumType.STRING)
    private Late late;
    
    @Enumerated(EnumType.STRING)
    private Reason reason;
    
    @Enumerated(EnumType.STRING)
    private External external;
    
    private Boolean isFit;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean isWritten = Boolean.FALSE;
    
    private LocalDate date;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public void updateChecklist(Boolean isSuccess, Spare spare, Late late, Reason reason, External external, boolean isFit) {
        this.isSuccess = isSuccess;
        this.spare = spare;
        this.late = late;
        this.reason = reason;
        this.external = external;
        this.isFit = isFit;
        this.isWritten = true;
    }
}
