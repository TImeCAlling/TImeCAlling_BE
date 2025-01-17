package TImeCAlling.spring.web.dto.schedule;

import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.Spare;
import TImeCAlling.spring.validation.annotation.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleRequestDTO {

    @Getter
    public static class CategoryDTO {

        @Size(max = 10)
        @NotBlank
        String categoryName;

        @Size(max = 10)
        @NotBlank
        String categoryColor;
    }

    @Getter
    public static class ScheduleCreateDTO {

        @NotBlank
        @Size(max = 10)
        String name;

        @Size(max = 20)
        String body;

        LocalDateTime meetTime;

        @NotNull
        String place;

        @NotNull
        String longitude;

        @NotNull
        String latitude;

        @NotNull
        Integer moveTime;

        @NotNull
        @ValidEnum(enumClass = FreeTime.class)
        String freeTime;

        @NotNull
        Boolean isRepeat;
        
        LocalDate start;
        
        LocalDate end;

        List<CategoryDTO> categories;
    }

    @Getter
    public static class SchedulePatchDTO {

        @Size(max = 20)
        String body;

        @NotNull
        Integer moveTime;

        @NotNull
        @ValidEnum(enumClass = FreeTime.class)
        String freeTime;

        @NotNull
        Boolean isRepeat;

        List<CategoryDTO> categories;
    }
}
