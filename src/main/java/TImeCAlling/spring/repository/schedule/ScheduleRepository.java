package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByIdAndUser(Long id, User user);
}
