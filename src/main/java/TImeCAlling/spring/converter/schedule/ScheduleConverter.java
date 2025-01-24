package TImeCAlling.spring.converter.schedule;

import TImeCAlling.spring.domain.*;
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
                    .meetDate(schedule.getChecklists().get(0).getDate())
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
                    .meetDate(schedule.getChecklists().get(0).getDate())
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
    
    public static ScheduleResponseDTO.ScheduleStatusDTO toScheduleStatusDTO(Schedule schedule) {
        
        Long leftTime = ChronoUnit.MINUTES.between(LocalTime.now(), schedule.getMeetTime());
        
        return ScheduleResponseDTO.ScheduleStatusDTO.builder()
                .name(schedule.getName())
                .meetTime(schedule.getMeetTime())
                .totalTime(LocalTime.MIN.plusMinutes(schedule.getMoveTime()))
                .leftTime(leftTime)
                .build();
    }
    
    public static List<ScheduleResponseDTO.CategoryDTO> toCategoryDTOList(List<Category> categories) {
        return categories.stream()
                .map(category -> ScheduleResponseDTO.CategoryDTO.builder()
                        .categoryName(category.getName())
                        .categoryColor(category.getColor())
                        .build()
                )
                .collect(Collectors.toList());
    }
    
    
    public static ScheduleResponseDTO.SchedulesByDateDTO toSchedulesByDateDTO(List<Checklist> checklistList) {
        List<ScheduleResponseDTO.ScheduleByDateDTO> schedulesByDateDTOList = checklistList.stream()
                .map(checklist -> ScheduleResponseDTO.ScheduleByDateDTO.builder()
                        .checkListId(checklist.getId())
                        .name(checklist.getSchedule().getName())
                        .iseRepeat(checklist.getSchedule().getIsRepeat())
                        .repeatDays(getRepeatDays(checklist))
                        .isWritten(checklist.getIsWritten())
                        .meetTime(checklist.getSchedule().getMeetTime())
                        .categories(toCategoryDTOList(checklist.getSchedule().getCategories()))
                        .build()
                )
                .collect(Collectors.toList());
        
        return ScheduleResponseDTO.SchedulesByDateDTO.builder()
                .schedules(schedulesByDateDTOList)
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
    
    public static ScheduleResponseDTO.TodaySchedulesDTO toTodaySchedulesDTO(List<Checklist> checklists) {
        List<ScheduleResponseDTO.TodayScheduleDTO> dtoList = checklists.stream()
                .map(checklist -> ScheduleResponseDTO.TodayScheduleDTO.builder()
                        .checkListId(checklist.getId())
                        .name(checklist.getSchedule().getName())
                        .body(checklist.getSchedule().getBody())
                        .meetTime(checklist.getSchedule().getMeetTime())
                        .build()
                ).collect(Collectors.toList());
        return ScheduleResponseDTO.TodaySchedulesDTO.builder()
                .schedules(dtoList)
                .build();
    }
    
    
    private static List<String> getRepeatDays(Checklist checklist) {
        if (checklist.getSchedule().getIsRepeat()) {
            return checklist.getSchedule().getRecurringSchedule().getRepeatDays().stream()
                    .map(Enum::toString)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
