package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
