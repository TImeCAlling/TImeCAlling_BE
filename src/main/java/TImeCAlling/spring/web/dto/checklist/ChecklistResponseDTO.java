package TImeCAlling.spring.web.dto.checklist;

import lombok.Builder;
import lombok.Getter;

public class ChecklistResponseDTO {
    @Getter
    @Builder
    public static class UpdateResultDTO {
        private Long checklistId;
    }
}
