package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    
    void deleteAllByScheduleId(Long scheduleId);

    Checklist findByScheduleIdAndDate(Long scheduleId, LocalDate date);
}
