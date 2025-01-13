package TImeCAlling.spring.web.dto.schedule;

import TImeCAlling.spring.domain.enums.Spare;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    public static class ScheduleGetDTO{
        Long scheduleId;
        LocalDateTime meetTime;
        Boolean isRepeat;
        String place;
        String body;
        Spare spare;
    }
}