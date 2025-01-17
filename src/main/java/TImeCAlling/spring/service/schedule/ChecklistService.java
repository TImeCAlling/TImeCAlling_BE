package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.Checklist;
import TImeCAlling.spring.domain.RecurringSchedule;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;

import java.util.List;

public interface ChecklistService {
    
    void createChecklists(Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request);
    
    List<Checklist> patchChecklists(Schedule schedule, ScheduleRequestDTO.SchedulePatchDTO request);
}
