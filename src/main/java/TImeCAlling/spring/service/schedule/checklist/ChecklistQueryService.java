package TImeCAlling.spring.service.schedule.checklist;

import TImeCAlling.spring.domain.Checklist;
import TImeCAlling.spring.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ChecklistQueryService {
    Checklist findOne(Long checklistId);
    
    List<Checklist> getCheckListByDateAndUser(LocalDate date, User user);
    
    List<Checklist> getPastCheckList(LocalDateTime dateTime, User user);
    
}
