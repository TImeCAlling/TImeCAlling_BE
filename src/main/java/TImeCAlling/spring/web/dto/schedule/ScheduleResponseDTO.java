package TImeCAlling.spring.web.dto.schedule;

import TImeCAlling.spring.domain.enums.FreeTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ScheduleResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleCreateDTO{
        Long scheduleId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDTO{
        String categoryName;
        String categoryColor;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleGetDTO{
        Long scheduleId;
        LocalTime meetTime;
        Boolean isRepeat;
        String place;
        String body;
        FreeTime freeTime;
        List<CategoryDTO> categories;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleDeleteDTO{
        Long scheduleId;
    }
}