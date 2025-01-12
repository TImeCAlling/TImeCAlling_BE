package TImeCAlling.spring.web.dto.schedule;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleRequestDTO {
    @Getter
    public static class ScheduleCreateDTO {
        String name;
        String body;
        LocalDateTime meetTime;
        String place;
        String longitude;
        String latitude;
        Integer moveTime;
        String spare;
        Boolean isRepeat;
        List<Long> category;
    }
}
