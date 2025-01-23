package TImeCAlling.spring.web.controller.schedule;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.converter.schedule.ChecklistConverter;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.schedule.checklist.ChecklistCommandService;
import TImeCAlling.spring.web.dto.checklist.ChecklistRequestDTO;
import TImeCAlling.spring.web.dto.checklist.ChecklistResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistCommandService checklistCommandService;

    @PatchMapping("/api/checklist/{schedule_id}")
    @Operation(summary = "체크리스트 작성 API", description = "체크리스트 각 항목에 enum으로 정해진 값만 넣어서 주세요 !!")
    public ApiResponse<ChecklistResponseDTO.UpdateResultDTO> update(@PathVariable("schedule_id") Long scheduleId, @AuthenticationPrincipal User user, @RequestBody ChecklistRequestDTO.UpdateDTO request) {
        Long resultId = checklistCommandService.updateChecklist(scheduleId, user.getId(), request);

        return ApiResponse.onSuccess(ChecklistConverter.toUpdateResultDTO(resultId));
    }
}
