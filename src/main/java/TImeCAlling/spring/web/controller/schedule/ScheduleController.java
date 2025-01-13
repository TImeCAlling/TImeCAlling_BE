package TImeCAlling.spring.web.controller.schedule;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.schedule.ScheduleCommandService;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleCommandService scheduleCommandService;
    private final UserQueryService userQueryService;

    @PostMapping
    public ApiResponse<ScheduleResponseDTO.ScheduleCreateDTO> scheduleCreate(@RequestParam Long userId, @RequestBody @Valid ScheduleRequestDTO.ScheduleCreateDTO request) {
        User user = userQueryService.findOne(userId);
        Schedule schedule = scheduleCommandService.createSchedule(user, request);
        return ApiResponse.onSuccess(ScheduleConverter.toScheduleCreateDTO(schedule));
    }
}
