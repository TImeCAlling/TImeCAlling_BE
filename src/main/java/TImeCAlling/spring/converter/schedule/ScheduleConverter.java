package TImeCAlling.spring.converter.schedule;

import TImeCAlling.spring.domain.Category;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.Spare;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                .build();
    }

    public static ScheduleResponseDTO.ScheduleGetDTO toScheduleGetDTO(Schedule schedule) {
        List<ScheduleResponseDTO.CategoryDTO> categoryDTOS = schedule.getCategories().stream()
                .map(category -> ScheduleResponseDTO.CategoryDTO.builder()
                        .categoryName(category.getName())
                        .categoryColor(category.getColor())
                        .build())
                .collect(Collectors.toList());

        return ScheduleResponseDTO.ScheduleGetDTO.builder()
                .scheduleId(schedule.getId())
                .meetTime(schedule.getMeetTime())
                .isRepeat(schedule.getIsRepeat())
                .place(schedule.getPlace())
                .body(schedule.getBody())
                .freeTime(schedule.getFreeTime())
                .categories(categoryDTOS)
                .build();
    }

    public static ScheduleResponseDTO.ScheduleGetDTO toSchedulePatchDTO(Schedule schedule) {
        return ScheduleResponseDTO.ScheduleGetDTO.builder()
                .scheduleId(schedule.getId())
                .meetTime(schedule.getMeetTime())
                .isRepeat(schedule.getIsRepeat())
                .place(schedule.getPlace())
                .body(schedule.getBody())
                .freeTime(schedule.getFreeTime())
                .build();
    }

    public static ScheduleResponseDTO.ScheduleDeleteDTO toScheduleCommandDTO(Long scheduleId) {
        return ScheduleResponseDTO.ScheduleDeleteDTO.builder()
                .scheduleId(scheduleId)
                .build();
    }
}
