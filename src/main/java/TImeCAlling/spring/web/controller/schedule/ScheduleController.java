package TImeCAlling.spring.web.controller.schedule;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.converter.user.UserConverter;
import TImeCAlling.spring.domain.Checklist;
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
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @Operation(summary = "일정 추가", description = "새로운 일정을 추가합니다.")
    @PostMapping
    public ApiResponse<ScheduleResponseDTO.ScheduleCreateDTO> scheduleCreate(@AuthenticationPrincipal User user, @RequestBody @Valid ScheduleRequestDTO.ScheduleCreateDTO request) {

        Schedule schedule = scheduleCommandService.createSchedule(user, request);
        if (request.getIsRepeat()) {
            recurringScheduleService.createRecurringSchedule(schedule, request);
        }
        checklistService.createChecklists(schedule, request);
        
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleCreateDTO(schedule));
    }

    @Operation(summary = "일정 상세 조회", description = "일정 id로 일정의 정보를 상세 조회합니다.")
    @GetMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDTO.ScheduleGetDTO> scheduleGet(@AuthenticationPrincipal User user, @PathVariable @ExistSchedule Long scheduleId) {

        Schedule schedule = scheduleQueryService.getSchedule(scheduleId, user);
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleGetDTO(schedule, schedule.getRecurringSchedule() == null ? null : schedule.getRecurringSchedule()));
    }

    @Operation(summary = "일정 수정", description = "일정 id로 일정의 정보를 수정합니다. 공유하지 않은 일정은 모든 항목에 대해 수정 가능합니다.")
    @PatchMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDTO.ScheduleGetDTO> schedulePatch(@AuthenticationPrincipal User user, @PathVariable @ExistSchedule Long scheduleId, @RequestBody @Valid ScheduleRequestDTO.SchedulePatchDTO request) {

        Schedule schedule = scheduleCommandService.patchSchedule(scheduleId, user, request);
        return ApiResponse.onSuccess(ScheduleConverter.toSchedulePatchDTO(schedule));
    }

    @Operation(summary = "일정 삭제", description = "일정 id로 일정을 삭제합니다.")
    @DeleteMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponseDTO.ScheduleDeleteDTO> scheduleDelete(@AuthenticationPrincipal User user, @PathVariable @ExistSchedule Long scheduleId) {

        Schedule schedule = scheduleCommandService.deleteSchedule(scheduleId, user);
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleCommandDTO(scheduleId));
    }
    
    @GetMapping("/{scheduleId}/status")
    public ApiResponse<ScheduleResponseDTO.ScheduleStatusDTO> getScheduleStatus(@PathVariable @ExistSchedule Long scheduleId,
                                                                                @AuthenticationPrincipal User user) {
        Schedule schedule = scheduleQueryService.getSchedule(scheduleId, user);
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleStatusDTO(schedule));
    }

    @GetMapping("/success-rate")
    public ApiResponse<ScheduleResponseDTO.MyScheduleRateDTO> successRate(@AuthenticationPrincipal User user) {
        return ApiResponse.onSuccess(UserConverter.toMyScheduleRateDTO(user));
    }
    
    @GetMapping("/date")
    public ApiResponse<ScheduleResponseDTO.SchedulesByDateDTO> getSchedulesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                                  @AuthenticationPrincipal User user) {
        List<Checklist> checklists = checklistService.getCheckListByDateAndUser(date, user);
        
        return ApiResponse.onSuccess(ScheduleConverter.toSchedulesByDateDTO(checklists));
    }

    /** 공유 일정 멤버 조회 컨트롤러*/
    @GetMapping("/{scheduleId}/users")
    public ApiResponse<List<ScheduleResponseDTO.SharedScheduleUserDTO>> getSharedScheduleUser(
            @PathVariable @ExistSchedule Long scheduleId) {
        Schedule schedule = scheduleQueryService.getSchedule(scheduleId);
        return ApiResponse.onSuccess(scheduleQueryService.getSharedScheduleUsers(schedule));
    }
}
