package TImeCAlling.spring.converter.schedule;

import TImeCAlling.spring.domain.enums.External;
import TImeCAlling.spring.web.dto.checklist.ChecklistResponseDTO;

public class ChecklistConverter {
    public static ChecklistResponseDTO.UpdateResultDTO toUpdateResultDTO(Long checklistId) {

        return ChecklistResponseDTO.UpdateResultDTO.builder()
                .checklistId(checklistId)
                .build();
    }

}
