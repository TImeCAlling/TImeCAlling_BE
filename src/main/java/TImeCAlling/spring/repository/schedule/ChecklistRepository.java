package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    
    void deleteAllByScheduleId(Long scheduleId);
    
    @Query("SELECT c FROM Checklist c " +
            "JOIN c.schedule s " +
            "WHERE s.user.id = :userId " +
            "AND c.date = :date")
    List<Checklist> findChecklistsByUserAndDate(@Param("userId") Long userId,
                                              @Param("date") LocalDate date);
}
