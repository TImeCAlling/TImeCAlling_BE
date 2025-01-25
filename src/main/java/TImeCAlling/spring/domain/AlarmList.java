package TImeCAlling.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AlarmList {

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "alarmList", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();

    public void update(Integer offset, String body, String music, String musicUrl) {
        if (offset != null) {
            this.offset = offset;
        }
        if (body != null) {
            this.body = body;
        }
        if (music != null) {
            this.music = music;
        }
        if (musicUrl != null) {
            this.musicUrl = musicUrl;
        }
    }

    public void updateIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
