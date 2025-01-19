package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;

public interface RecurringScheduleService {
    
    void createRecurringSchedule(Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request);
}
