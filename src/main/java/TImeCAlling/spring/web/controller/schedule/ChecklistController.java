package TImeCAlling.spring.web.controller.schedule;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.converter.schedule.ChecklistConverter;
import TImeCAlling.spring.service.schedule.checklist.ChecklistCommandService;
import TImeCAlling.spring.web.dto.checklist.ChecklistRequestDTO;
import TImeCAlling.spring.web.dto.checklist.ChecklistResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistCommandService checklistCommandService;

    @PatchMapping("/api/checklist/{schedule_id}/{user_id}")
    public ApiResponse<ChecklistResponseDTO.UpdateResultDTO> update(@PathVariable("schedule_id") Long scheduleId, @PathVariable("user_id") Long userId, @RequestBody ChecklistRequestDTO.UpdateDTO request) {
        Long resultId = checklistCommandService.updateChecklist(scheduleId, userId, request);

        return ApiResponse.onSuccess(ChecklistConverter.toUpdateResultDTO(resultId));
    }
}
