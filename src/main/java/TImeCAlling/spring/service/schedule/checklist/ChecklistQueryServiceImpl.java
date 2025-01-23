package TImeCAlling.spring.service.schedule.checklist;

import TImeCAlling.spring.domain.Checklist;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.schedule.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistQueryServiceImpl implements ChecklistQueryService {
    private final ChecklistRepository checklistRepository;
    @Override
    public Checklist findOne(Long checklistId) {
        return checklistRepository.findById(checklistId).orElseThrow();
    }
    
    @Override
    public List<Checklist> getCheckListByDateAndUser(LocalDate date, User user) {
        return checklistRepository.findChecklistsBySchedule_User_IdAndDate(user.getId(), date);
    }
}
