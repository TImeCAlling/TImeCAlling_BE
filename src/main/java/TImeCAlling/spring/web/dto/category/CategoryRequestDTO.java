package TImeCAlling.spring.web.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CategoryRequestDTO {
    @Setter
    @Getter
    public static class CreateOrUpdateDto {
        String type;
    }
}
