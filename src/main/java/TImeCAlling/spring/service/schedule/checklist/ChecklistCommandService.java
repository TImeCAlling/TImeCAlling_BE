package TImeCAlling.spring.service.schedule.checklist;

import TImeCAlling.spring.domain.Checklist;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.checklist.ChecklistRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;

import java.time.LocalDate;
import java.util.List;

public interface ChecklistCommandService {
    
    void createChecklists(Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request);

    List<Checklist> patchChecklists(Schedule schedule, ScheduleRequestDTO.SchedulePatchDTO request);

    Long updateChecklist(Long scheduleId, Long userId, ChecklistRequestDTO.UpdateDTO request);
    
    void deleteExpiredChecklists();
}
