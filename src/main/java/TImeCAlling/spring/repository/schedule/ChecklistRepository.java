package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    
    void deleteAllByScheduleId(Long scheduleId);

    Checklist findByScheduleIdAndDate(Long scheduleId, LocalDate date);

    @Modifying
    @Query(
            value = "DELETE from Checklist where date < :date and is_written = true",
            nativeQuery = true
    )
    void deleteExpiredChecklists(@Param("date") LocalDate date);
}
