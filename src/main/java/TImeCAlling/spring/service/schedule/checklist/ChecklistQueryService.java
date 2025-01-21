package TImeCAlling.spring.service.schedule.checklist;

import TImeCAlling.spring.domain.Checklist;

public interface ChecklistQueryService {
    Checklist findOne(Long checklistId);
}
