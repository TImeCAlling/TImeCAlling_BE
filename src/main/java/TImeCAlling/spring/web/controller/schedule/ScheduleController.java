package TImeCAlling.spring.web.controller.schedule;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ScheduleHandler;
import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.schedule.ChecklistService;
import TImeCAlling.spring.service.schedule.RecurringScheduleService;
import TImeCAlling.spring.service.schedule.ScheduleCommandService;
import TImeCAlling.spring.service.schedule.ScheduleQueryService;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.validation.annotation.ExistSchedule;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@Validated
public class ScheduleController {
    private final UserQueryService userQueryService;
    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;
    private final RecurringScheduleService recurringScheduleService;
    private final ChecklistService checklistService;

    @PostMapping
    public ApiResponse<ScheduleResponseDTO.ScheduleCreateDTO> scheduleCreate(@RequestParam Long userId, @RequestBody @Valid ScheduleRequestDTO.ScheduleCreateDTO request) {
        User user = userQueryService.findOne(userId);
        Schedule schedule = scheduleCommandService.createSchedule(user, request);
        if (request.getIsRepeat()) {
            recurringScheduleService.createRecurringSchedule(schedule, request);
        }
        checklistService.createChecklists(schedule, request);
        
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleCreateDTO(schedule));
    }

    @GetMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDTO.ScheduleGetDTO> scheduleGet(@PathVariable @ExistSchedule Long scheduleId, @RequestParam Long userId) {
        User user = userQueryService.findOne(userId);
        Schedule schedule = scheduleQueryService.getSchedule(scheduleId, user);
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleGetDTO(schedule, schedule.getRecurringSchedule()));
    }

    @PatchMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDTO.ScheduleGetDTO> schedulePatch(@PathVariable @ExistSchedule Long scheduleId, @RequestParam Long userId, @RequestBody @Valid ScheduleRequestDTO.SchedulePatchDTO request) {
        User user = userQueryService.findOne(userId);
        Schedule schedule = scheduleCommandService.patchSchedule(scheduleId, user, request);
        return ApiResponse.onSuccess(ScheduleConverter.toSchedulePatchDTO(schedule));
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDTO.ScheduleDeleteDTO> scheduleDelete(@PathVariable @ExistSchedule Long scheduleId, @RequestParam Long userId) {
        User user = userQueryService.findOne(userId);
        Schedule schedule = scheduleCommandService.deleteSchedule(scheduleId, user);
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleCommandDTO(scheduleId));
    }
}
