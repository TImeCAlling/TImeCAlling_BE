package TImeCAlling.spring.web.dto.checklist;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class ChecklistRequestDTO {
    @Getter
    @Builder
    public static class ChecklistUpdateDTO {
        private Boolean isSuccess;
        private String spare;
        private String late;
        private String reason;
        private String external;
        private Boolean isFit;
        private LocalDate date;
    }
}
