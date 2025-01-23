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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyScheduleRateDTO {
        Integer total;
        Integer success;
        Integer failed;
        Double successRate;
    }
    
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleStatusDTO{
        
        String name;
        LocalTime meetTime;
        LocalTime totalTime;
        Long leftTime;
    }
    
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleByDateDTO {
        Long checkListId;
        String name;
        Boolean iseRepeat;
        List<String> repeatDays;
        List<ScheduleResponseDTO.CategoryDTO> categories;
        LocalTime meetTime;
        Boolean isWritten;
        
    }
    
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchedulesByDateDTO {
        List<ScheduleByDateDTO> schedules;
    }
    
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodayScheduleDTO {
        Long checkListId;
        String name;
        String body;
        LocalTime meetTime;
    }
    
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodaySchedulesDTO {
        List<TodayScheduleDTO> schedules;
    }
}
