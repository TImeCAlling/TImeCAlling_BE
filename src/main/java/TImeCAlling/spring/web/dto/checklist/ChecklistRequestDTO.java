package TImeCAlling.spring.web.dto.checklist;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class ChecklistRequestDTO {
    @Getter
    public static class ChecklistUpdateDTO {
        private Boolean isSuccess;
        private String spare="없음";
        private String late="없음";
        private String reason="없음";
        private String external="없음";
        private Boolean isFit;
        private LocalDate date;
    }
}
