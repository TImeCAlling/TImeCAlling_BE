package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.SocialType;
import TImeCAlling.spring.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 10, nullable = false)
    private String nickname;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;
    
    @Column(nullable = false)
    private Integer avgPrepTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FreeTime freeTime;
    
    @Column(nullable = false)
    private String fcmToken;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;
    
    @Column(nullable = false)
    private LocalDate inactivationDate;
    
    private Integer success;
    
    private Integer failed;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProfileImage profileImage;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PushMessageSetting> pushMessageSettings = new ArrayList<>();
}
