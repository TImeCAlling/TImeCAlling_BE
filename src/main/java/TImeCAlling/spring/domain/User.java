package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.SocialType;
import TImeCAlling.spring.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class User extends BaseEntity implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 10, nullable = false)
    private String nickname;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    private Long socialId;
    
    @Column(nullable = false)
    private Integer avgPrepTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FreeTime freeTime;
    
    @Column(nullable = false)
    private String fcmToken;

    private String refreshToken;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.ACTIVE;
    
    private LocalDate inactivationDate;
    
    @Builder.Default
    private Integer success = 0;
    
    @Builder.Default
    private Integer failed = 0;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProfileImage profileImage;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AlarmList> alarmList = new ArrayList<>();

    public void update(String nickname, Integer avgPrepTime, FreeTime freeTime) {
        this.nickname = nickname;
        this.avgPrepTime = avgPrepTime;
        this.freeTime = freeTime;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getPassword() {
        return null;
    }
    @Override
    public String getUsername() {
        return null;
    }

    public void addResult(Boolean isSuccess) {
        if (isSuccess) {
            success += 1;
        } else {
            failed += 1;
        }
    }

    /**Fcm 토큰 업데이트 관련 메소드*/
    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
