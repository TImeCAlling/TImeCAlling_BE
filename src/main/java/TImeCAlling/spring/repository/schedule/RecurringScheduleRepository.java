package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.RecurringSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecurringScheduleRepository extends JpaRepository<RecurringSchedule, Long> {
    
    Optional<RecurringSchedule> findByScheduleId(Long scheduleId);
}
