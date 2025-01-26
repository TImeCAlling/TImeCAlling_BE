package TImeCAlling.spring.web.dto.schedule;

import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.RepeatDay;
import TImeCAlling.spring.validation.annotation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
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

        @NotNull
        LocalDate meetDate;

        @JsonFormat(pattern = "HH:mm")
        @Schema(type = "string", example = "00:00")
        @NotNull
        LocalTime meetTime;

        @NotNull
        String place;

        @NotNull
        String longitude;

        @NotNull
        String latitude;

        @NotNull
        Integer moveTime;

        @Schema(example = "TIGHT")
        @NotNull
        @ValidEnum(enumClass = FreeTime.class)
        String freeTime;

        @Schema(example = "[\"MONDAY\", \"TUESDAY\"]")
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

        @NotNull
        LocalDate meetDate;

        @JsonFormat(pattern = "HH:mm")
        @Schema(type = "string", example = "00:00")
        @NotNull
        LocalTime meetTime;
        
        @NotNull
        String place;
        
        @NotNull
        String longitude;
        
        @NotNull
        String latitude;
        
        @NotNull
        Integer moveTime;

        @Schema(example = "TIGHT")
        @NotNull
        @ValidEnum(enumClass = FreeTime.class)
        String freeTime;

        @NotNull
        Boolean isRepeat;

        @Schema(example = "[\"MONDAY\", \"TUESDAY\"]")
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
