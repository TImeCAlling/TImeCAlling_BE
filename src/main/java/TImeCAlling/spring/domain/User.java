package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.SocialType;
import TImeCAlling.spring.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    
    @Column(nullable = false, columnDefinition = "varchar(10) default ACTIVE")
    private Status status;
    
    @Column(nullable = false)
    private LocalDate inactivationDate;
    
    @Column(nullable = true)
    private Integer success;
    
    @Column(nullable = true)
    private Integer failed;
    
}
