package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.RecurringSchedule;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;

public interface ChecklistService {
    
    void createChecklists(Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request);
}
