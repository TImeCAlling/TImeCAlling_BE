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
import TImeCAlling.spring.service.schedule.checklist.ChecklistQueryService;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.validation.annotation.ExistSchedule;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@Tag(name = "일정 관련 API")
@Validated
public class ScheduleController {
    private final UserQueryService userQueryService;
    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;
    private final RecurringScheduleService recurringScheduleService;
    private final ChecklistCommandService checklistCommandService;
    private final ChecklistQueryService checklistQueryService;
    
    @PostMapping
    public ApiResponse<ScheduleResponseDTO.ScheduleCreateDTO> scheduleCreate(@RequestParam Long userId, @RequestBody @Valid ScheduleRequestDTO.ScheduleCreateDTO request) {
        User user = userQueryService.findOne(userId);
        Schedule schedule = scheduleCommandService.createSchedule(user, request);
        if (request.getIsRepeat()) {
            recurringScheduleService.createRecurringSchedule(schedule, request);
        }
        checklistCommandService.createChecklists(schedule, request);
        
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
    
    @Operation(summary = "준비중 일정 조회 API", description = "일정 id로 조회시 해당 일정의 준비중 상태를 조회합니다.")
    @GetMapping("/{scheduleId}/status")
    public ApiResponse<ScheduleResponseDTO.ScheduleStatusDTO> getScheduleStatus(
            @Parameter(description = "준비중 상태인 일정 id를 입력하세요.")
            @PathVariable @ExistSchedule Long scheduleId,
            @AuthenticationPrincipal User user) {
        Schedule schedule = scheduleQueryService.getSchedule(scheduleId, user);
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleStatusDTO(schedule));
    }
    

    @Operation(summary = "나의 일정 현황 API", description = "나의 성공, 실패 일정 수와 총 일정 수, 성공률을 조회할 수 있습니다.")
    @GetMapping("/success-rate")
    public ApiResponse<ScheduleResponseDTO.MyScheduleRateDTO> successRate(
            @AuthenticationPrincipal User user) {
        return ApiResponse.onSuccess(UserConverter.toMyScheduleRateDTO(user));
    }
    
    @Operation(summary = "특정 날짜 일정 조회 API", description = "특정 날짜의 일정들을 조회하는 API입니다.")
    @GetMapping("/date")
    public ApiResponse<ScheduleResponseDTO.SchedulesByDateDTO> getSchedulesByDate(
            @Parameter(description = "YYYY-MM-DD 형식을 지켜주세요.")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @AuthenticationPrincipal User user) {
        List<Checklist> checklists = checklistQueryService.getCheckListByDateAndUser(date, user);
        
        return ApiResponse.onSuccess(ScheduleConverter.toSchedulesByDateDTO(checklists));
    }
    
    /**
     * 공유 일정 팀원 조회하여 리스트 형태로 반환
     *
     * @param scheduleId 일정 ID
     * @param user 인증된 사용자
     * @return 팀원 리스트
     */
    @Operation(
            summary = "공유 일정 팀원 조회",
            description = "특정 스케줄의 팀원 목록을 반환합니다. 사용자는 해당 일정의 팀원이어야 정상적으로 작동합니다."
    )
    @GetMapping("/{scheduleId}/users")
    public ApiResponse<List<ScheduleResponseDTO.SharedScheduleUserDTO>> getSharedScheduleUser(
            @Parameter(description = "조회할 일정 ID", example = "101")
            @PathVariable @ExistSchedule Long scheduleId,
            @AuthenticationPrincipal User user) {

        Schedule schedule = scheduleQueryService.getSchedule(scheduleId, user);

        return ApiResponse.onSuccess(scheduleQueryService.getSharedScheduleUsers(schedule));
    }
    
    @Operation(summary = "오늘 일정 목록 조회 API", description = "오늘 일정들의 제목, 메모, 시간을 조회하는 API입니다.")
    @GetMapping("/today")
    public ApiResponse<ScheduleResponseDTO.TodaySchedulesDTO> getTodaySchedules(
            @AuthenticationPrincipal User user) {
        List<Checklist> checklists = checklistQueryService.getCheckListByDateAndUser(LocalDate.now(), user);
        return ApiResponse.onSuccess(ScheduleConverter.toTodaySchedulesDTO(checklists));
    }
}
