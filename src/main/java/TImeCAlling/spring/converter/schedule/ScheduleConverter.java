package TImeCAlling.spring.converter.schedule;

import TImeCAlling.spring.domain.*;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleConverter {
    public static ScheduleResponseDTO.ScheduleCreateDTO toScheduleCreateDTO(Schedule schedule) {
        return ScheduleResponseDTO.ScheduleCreateDTO.builder()
                .scheduleId(schedule.getId())
                .build();
    }

    public static Schedule toSchedule(User user, ScheduleRequestDTO.ScheduleCommandDTO request){

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
                .meetTime(request.getMeetTime())
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

        List<String> repeatDays = schedule.getIsRepeat()
                ? recurringSchedule.getRepeatDays().stream()
                .map(Enum::toString)
                .toList()
                : null;

        return ScheduleResponseDTO.ScheduleGetDTO.builder()
                .scheduleId(schedule.getId())
                .name(schedule.getName())
                .meetDate(schedule.getChecklists().get(0).getDate())
                .meetTime(schedule.getMeetTime())
                .place(schedule.getPlace())
                .repeatDays(repeatDays)
                .moveTime(schedule.getMoveTime())
                .body(schedule.getBody() == null ? null : schedule.getBody())
                .freeTime(schedule.getFreeTime().toString())
                .isRepeat(schedule.getIsRepeat())
                .start(schedule.getIsRepeat() ? recurringSchedule.getStart() : null)
                .end(schedule.getIsRepeat() ? recurringSchedule.getEnd() : null)
                .categories(categoryDTOS)
                .shareId(schedule.getShareId())
                .build();
    }

    public static ScheduleResponseDTO.ScheduleCreateDTO toSchedulePatchDTO(Schedule schedule) {
        return ScheduleResponseDTO.ScheduleCreateDTO.builder()
                .scheduleId(schedule.getId())
                .build();
    }

    public static ScheduleResponseDTO.ScheduleDeleteDTO toScheduleCommandDTO(Long scheduleId) {
        return ScheduleResponseDTO.ScheduleDeleteDTO.builder()
                .scheduleId(scheduleId)
                .build();
    }

    public static ScheduleResponseDTO.GetShareScheduleDTO toGetShareScheduleDTO(Schedule schedule, RecurringSchedule recurringSchedule) {

        List<String> repeatDays = schedule.getIsRepeat()
                ? recurringSchedule.getRepeatDays().stream()
                .map(Enum::toString)
                .toList()
                : null;

        return ScheduleResponseDTO.GetShareScheduleDTO.builder()
                .nickname(schedule.getUser().getNickname())
                .name(schedule.getName())
                .meetDate(schedule.getChecklists().get(0).getDate())
                .meetTime(schedule.getMeetTime())
                .place(schedule.getPlace())
                .longitude(schedule.getLongitude())
                .latitude(schedule.getLatitude())
                .repeatDays(repeatDays)
                .isRepeat(schedule.getIsRepeat())
                .start(schedule.getIsRepeat() ? recurringSchedule.getStart() : null)
                .end(schedule.getIsRepeat() ? recurringSchedule.getEnd() : null)
                .build();
    }

    public static Schedule toShareSchedule(User user, ScheduleRequestDTO.ScheduleCommandDTO request, String shareId){

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
                .meetTime(request.getMeetTime())
                .place(request.getPlace())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .moveTime(request.getMoveTime())
                .freeTime(freeTime)
                .isRepeat(request.getIsRepeat())
                .categories(categories)
                .shareId(shareId)
                .build();
    }

    public static ScheduleResponseDTO.ScheduleStatusDTO toScheduleStatusDTO(Schedule schedule, User user) {
        
        Long leftTime = ChronoUnit.MINUTES.between(LocalTime.now(), schedule.getMeetTime());
        int spareTime;
        int plusTime;
        double checkPercent = (double) user.getSuccess() / (user.getSuccess() + user.getFailed());
        
        if (checkPercent > 90.0) {
            plusTime = 0;
        } else if (checkPercent > 60.0) {
            plusTime = 5;
        } else if (checkPercent > 40.0) {
            plusTime = 10;
        } else {
            plusTime = 15;
        }
        
        switch (schedule.getFreeTime()) {
            case TIGHT -> spareTime = 5;
            case RELAXED -> spareTime = 10;
            case PLENTY -> spareTime = 15;
            default -> spareTime = 0;
        }
        
        return ScheduleResponseDTO.ScheduleStatusDTO.builder()
                .name(schedule.getName())
                .meetTime(schedule.getMeetTime())
                .totalTime(LocalTime.MIN.plusMinutes(schedule.getMoveTime()).plusMinutes(user.getAvgPrepTime()).plusMinutes(spareTime).plusMinutes(plusTime))
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
                        .scheduleId(checklist.getSchedule().getId())
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
                        .scheduleId(checklist.getSchedule().getId())
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
    
    public static ScheduleResponseDTO.PastCheckListsDTO toPastCheckListsDTO(List<Checklist> checklists) {
        List<ScheduleResponseDTO.PastCheckListDTO> dtoList = checklists.stream()
                .map(checklist -> ScheduleResponseDTO.PastCheckListDTO.builder()
                        .scheduleId(checklist.getSchedule().getId())
                        .checkListId(checklist.getId())
                        .date(checklist.getDate())
                        .name(checklist.getSchedule().getName())
                        .body(checklist.getSchedule().getBody())
                        .meetTime(checklist.getSchedule().getMeetTime())
                        .build()
                ).collect(Collectors.toList());
        return ScheduleResponseDTO.PastCheckListsDTO.builder()
                .checkLists(dtoList)
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
