package TImeCAlling.spring.web.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
        Integer categoryColor;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleGetDTO{
        String name;
        LocalTime meetTime;
        String place;
        List<String> repeatDays;
        Integer moveTime;
        String body;
        String freeTime;
        Boolean isRepeat;
        LocalDate start;
        LocalDate end;
        List<CategoryDTO> categories;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleDeleteDTO{
        Long scheduleId;
    }
  
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SharedScheduleUserDTO{
        Long userId;
        String nickname;
        String profile; //유저 이미지 URL
    }
  
    public static class MyScheduleRateDTO {
        Integer total;
        Integer success;
        Integer failed;
        Double successRate;
    }
}

