package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ProfileImage extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 2083, nullable = false)
    private String fileUrl;
    
    @Column(nullable = false)
    private String fileName;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String fileUrl, String fileName) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }
}
