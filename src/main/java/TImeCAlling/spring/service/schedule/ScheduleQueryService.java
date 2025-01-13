package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;

public interface ScheduleQueryService {
    public Schedule getSchedule(User user, Long scheduleId);
}
