package TImeCAlling.spring.web.controller.schedule;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.converter.user.UserConverter;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.schedule.checklist.ChecklistCommandService;
import TImeCAlling.spring.service.schedule.RecurringScheduleService;
import TImeCAlling.spring.service.schedule.ScheduleCommandService;
import TImeCAlling.spring.service.schedule.ScheduleQueryService;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.validation.annotation.ExistSchedule;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@Validated
public class ScheduleController {
    private final UserQueryService userQueryService;
    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;
    private final RecurringScheduleService recurringScheduleService;
    private final ChecklistCommandService checklistService;

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
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleGetDTO(schedule, schedule.getRecurringSchedule() == null ? null : schedule.getRecurringSchedule()));
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
    
    @GetMapping("/{scheduleId}/status")
    public ApiResponse<ScheduleResponseDTO.ScheduleStatusDTO> getScheduleStatus(@PathVariable @ExistSchedule Long scheduleId,
                                                                                @AuthenticationPrincipal User user) {
        Schedule schedule = scheduleQueryService.getSchedule(scheduleId, user);
        List<User> users = new ArrayList<>();
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleStatusDTO(schedule, users));
    }

    @GetMapping("/success-rate")
    public ApiResponse<ScheduleResponseDTO.MyScheduleRateDTO> successRate(@AuthenticationPrincipal User user) {
        return ApiResponse.onSuccess(UserConverter.toMyScheduleRateDTO(user));
    }

    /** 공유 일정 멤버 조회 컨트롤러*/
    @GetMapping("/{scheduleId}/users")
    public ApiResponse<List<ScheduleResponseDTO.SharedScheduleUserDTO>> getSharedScheduleUser(
            @PathVariable @ExistSchedule Long scheduleId) {
        Schedule schedule = scheduleQueryService.getSchedule(scheduleId);
        return ApiResponse.onSuccess(scheduleQueryService.getSharedScheduleUsers(schedule));
    }
}
