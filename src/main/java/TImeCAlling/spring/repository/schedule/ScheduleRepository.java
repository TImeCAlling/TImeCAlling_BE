package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s JOIN FETCH s.categories WHERE s.id = :id")
    Optional<Schedule> findByIdWithCategories(@Param("id") Long id);

    Optional<Schedule> findByIdAndUser(Long id, User user);

    Optional<List<Schedule>> findByShareId(Long shareId);
}
