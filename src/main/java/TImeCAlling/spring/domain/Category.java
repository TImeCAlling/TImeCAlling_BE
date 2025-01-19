package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Category {
    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 10)
    private String color;
}
