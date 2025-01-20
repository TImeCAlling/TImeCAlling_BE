package TImeCAlling.spring.web.dto.checklist;

import TImeCAlling.spring.domain.enums.External;
import TImeCAlling.spring.domain.enums.Late;
import TImeCAlling.spring.domain.enums.Reason;
import TImeCAlling.spring.domain.enums.Spare;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class ChecklistRequestDTO {
    @Getter
    @Builder
    public static class UpdateDTO {
        private Boolean isSuccess;
        private String spare;
        private String late;
        private String reason;
        private String external;
        private Boolean isFit;
        private LocalDate date;
    }
}
