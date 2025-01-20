package TImeCAlling.spring.service.schedule.checklist;

import TImeCAlling.spring.domain.Checklist;
import TImeCAlling.spring.repository.schedule.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistQueryServiceImpl implements ChecklistQueryService {
    private final ChecklistRepository checklistRepository;
    @Override
    public Checklist findOne(Long checklistId) {
        return checklistRepository.findById(checklistId).orElseThrow();
    }
}
