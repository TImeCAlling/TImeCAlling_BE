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
        String categoryColor;
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
    public static class ScheduleStatusDTO{
        
        String name;
        List<UserProfileDTO> userProfiles;
        LocalTime meetTime;
        LocalTime totalTime;
        Long leftTime;
    }
    
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfileDTO{
        
        String profileImage;
    }
}