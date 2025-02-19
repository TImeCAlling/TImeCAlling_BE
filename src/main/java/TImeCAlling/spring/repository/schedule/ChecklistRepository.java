package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.Checklist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    
    void deleteAllByScheduleId(Long scheduleId);
    
    @Query("SELECT c FROM Checklist c " +
            "JOIN FETCH c.schedule s " +
            "LEFT JOIN FETCH s.recurringSchedule rs " +
            "LEFT JOIN FETCH rs.repeatDays " +
            "WHERE s.user.id = :userId " +
            "AND c.date = :date")
    List<Checklist> findChecklistsByScheduleUserIdAndDate(@Param("userId") Long userId,
                                                          @Param("date") LocalDate date);

    Checklist findByScheduleIdAndDate(Long scheduleId, LocalDate date);
    
    @Query("""
            select c from Checklist c
            JOIN fetch c.schedule s
            where c.isWritten = false
            and ( c.date < :today OR (c.date = :today AND s.meetTime < :nowTime))
            and s.user.id = :userId
            """)
    List<Checklist> findPastChecklists(LocalDate today, LocalTime nowTime, Long userId);
    
    @Modifying
    @Query(
            value = "DELETE from Checklist where date < :date and is_written = true",
            nativeQuery = true
    )
    void deleteExpiredChecklists(@Param("date") LocalDate date);
}
