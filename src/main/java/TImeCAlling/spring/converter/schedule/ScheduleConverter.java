package TImeCAlling.spring.converter.schedule;

import TImeCAlling.spring.domain.Category;
import TImeCAlling.spring.domain.RecurringSchedule;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleConverter {
    public static ScheduleResponseDTO.ScheduleCreateDTO toScheduleCreateDTO(Schedule schedule) {
        return ScheduleResponseDTO.ScheduleCreateDTO.builder()
                .scheduleId(schedule.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Schedule toSchedule(User user, ScheduleRequestDTO.ScheduleCreateDTO request){

        FreeTime freeTime = FreeTime.valueOf(request.getFreeTime());

        List<Category> categories = request.getCategories().stream()
                .map(categoryDTO -> Category.builder()
                        .name(categoryDTO.getCategoryName())
                        .color(categoryDTO.getColor())
                        .build())
                .collect(Collectors.toList());

        return Schedule.builder()
                .user(user)
                .name(request.getName())
                .body(request.getBody())
                .meetTime(request.getMeetTime().toLocalTime())
                .place(request.getPlace())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .moveTime(request.getMoveTime())
                .freeTime(freeTime)
                .isRepeat(request.getIsRepeat())
                .categories(categories)
                .build();
    }

    public static ScheduleResponseDTO.ScheduleGetDTO toScheduleGetDTO(Schedule schedule, RecurringSchedule recurringSchedule) {
        List<ScheduleResponseDTO.CategoryDTO> categoryDTOS = schedule.getCategories().stream()
                .map(category -> ScheduleResponseDTO.CategoryDTO.builder()
                        .categoryName(category.getName())
                        .categoryColor(category.getColor())
                        .build())
                .collect(Collectors.toList());
        if (schedule.getIsRepeat()) {
            List<String> repeatDays = recurringSchedule.getRepeatDays().stream()
                    .map(Enum::toString)
                    .toList();
            return ScheduleResponseDTO.ScheduleGetDTO.builder()
                    .name(schedule.getName())
                    .meetTime(schedule.getMeetTime())
                    .place(schedule.getPlace())
                    .repeatDays(repeatDays)
                    .moveTime(schedule.getMoveTime())
                    .body(schedule.getBody() == null ? null : schedule.getBody())
                    .freeTime(schedule.getFreeTime().toString())
                    .isRepeat(schedule.getIsRepeat())
                    .start(recurringSchedule.getStart())
                    .end(recurringSchedule.getEnd())
                    .categories(categoryDTOS)
                    .build();
        } else {
            return ScheduleResponseDTO.ScheduleGetDTO.builder()
                    .name(schedule.getName())
                    .meetTime(schedule.getMeetTime())
                    .place(schedule.getPlace())
                    .repeatDays(null)
                    .moveTime(schedule.getMoveTime())
                    .body(schedule.getBody() == null ? null : schedule.getBody())
                    .freeTime(schedule.getFreeTime().toString())
                    .isRepeat(schedule.getIsRepeat())
                    .start(null)
                    .end(null)
                    .categories(categoryDTOS)
                    .build();
        }
    }

    public static ScheduleResponseDTO.ScheduleGetDTO toSchedulePatchDTO(Schedule schedule) {
        List<ScheduleResponseDTO.CategoryDTO> categoryDTOS = schedule.getCategories().stream()
                .map(category -> ScheduleResponseDTO.CategoryDTO.builder()
                        .categoryName(category.getName())
                        .categoryColor(category.getColor())
                        .build())
                .collect(Collectors.toList());
        
        if (schedule.getIsRepeat()) {
            List<String> repeatDays = schedule.getRecurringSchedule().getRepeatDays().stream()
                    .map(Enum::toString)
                    .toList();
            return ScheduleResponseDTO.ScheduleGetDTO.builder()
                    .name(schedule.getName())
                    .meetTime(schedule.getMeetTime())
                    .place(schedule.getPlace())
                    .repeatDays(repeatDays)
                    .moveTime(schedule.getMoveTime())
                    .body(schedule.getBody() == null ? null : schedule.getBody())
                    .freeTime(schedule.getFreeTime().toString())
                    .isRepeat(schedule.getIsRepeat())
                    .start(schedule.getRecurringSchedule().getStart())
                    .end(schedule.getRecurringSchedule().getEnd())
                    .categories(categoryDTOS)
                    .build();
        } else {
            return ScheduleResponseDTO.ScheduleGetDTO.builder()
                    .name(schedule.getName())
                    .meetTime(schedule.getMeetTime())
                    .place(schedule.getPlace())
                    .repeatDays(null)
                    .moveTime(schedule.getMoveTime())
                    .body(schedule.getBody() == null ? null : schedule.getBody())
                    .freeTime(schedule.getFreeTime().toString())
                    .isRepeat(schedule.getIsRepeat())
                    .start(null)
                    .end(null)
                    .categories(categoryDTOS)
                    .build();
        }
    }

    public static ScheduleResponseDTO.ScheduleDeleteDTO toScheduleCommandDTO(Long scheduleId) {
        return ScheduleResponseDTO.ScheduleDeleteDTO.builder()
                .scheduleId(scheduleId)
                .build();
    }
    
    public static ScheduleResponseDTO.ScheduleStatusDTO toScheduleStatusDTO(Schedule schedule, List<User> users) {
        List<ScheduleResponseDTO.UserProfileDTO> userProfileDTOS = users.stream()
                .map(user -> ScheduleResponseDTO.UserProfileDTO.builder()
                        .profileImage(user.getProfileImage().getFileUrl())
                        .build()
                ).collect(Collectors.toList());
        Long leftTime = ChronoUnit.MINUTES.between(LocalTime.now(), schedule.getMeetTime());
        
        return ScheduleResponseDTO.ScheduleStatusDTO.builder()
                .name(schedule.getName())
                .userProfiles(userProfileDTOS)
                .meetTime(schedule.getMeetTime())
                .totalTime(LocalTime.MIN.plusMinutes(schedule.getMoveTime()))
                .leftTime(leftTime)
                .build();
        
        
        
        
    }

    public static List<ScheduleResponseDTO.SharedScheduleUserDTO> toSharedScheduleUserDTO (List<Schedule> schedules) {
        return schedules.stream()
                .map(schedule -> ScheduleResponseDTO.SharedScheduleUserDTO.builder()
                        .userId(schedule.getUser().getId())
                        .nickname(schedule.getUser().getNickname())
                        .profile(schedule.getUser().getProfileImage().getFileUrl())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
