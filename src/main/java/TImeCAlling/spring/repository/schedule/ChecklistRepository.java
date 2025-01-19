package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    
    void deleteAllByScheduleId(Long scheduleId);

}
