package TImeCAlling.spring.web.dto.schedule;

import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.RepeatDay;
import TImeCAlling.spring.validation.annotation.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleRequestDTO {

    @Getter
    @AllArgsConstructor
    public static class CategoryDTO {

        @Size(max = 10)
        @NotBlank
        String categoryName;

        @Max(9)
        @Min(0)
        @NotBlank
        Integer color;

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
        
        @ValidEnum(enumClass = RepeatDay.class)
        List<String> repeatDays;

        @NotNull
        Boolean isRepeat;
        
        LocalDate start;
        
        LocalDate end;

        List<CategoryDTO> categories;
    }

    @Getter
    @AllArgsConstructor
    public static class SchedulePatchDTO {
        
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
        
        @ValidEnum(enumClass = RepeatDay.class)
        List<String> repeatDays;
        
        LocalDate start;
        
        LocalDate end;

        List<CategoryDTO> categories;
    }
    
    @Getter
    public static class SearchSchedulesByDateDTO {
        
        @NotNull
        LocalDate date;
    }
}
