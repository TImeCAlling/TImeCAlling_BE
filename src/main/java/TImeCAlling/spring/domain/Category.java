package TImeCAlling.spring.domain;

import TImeCAlling.spring.domain.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Embeddable
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Category {
    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    @Min(0)
    @Max(9)
    private Integer color;

}
