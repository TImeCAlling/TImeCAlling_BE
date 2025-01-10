package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class profileImage extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 2083, nullable = false)
    private String fileUrl;
    
    @Column(nullable = false)
    private String fileName;
    
    
}
