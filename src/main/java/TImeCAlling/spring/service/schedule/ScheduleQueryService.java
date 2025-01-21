package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;

public interface ScheduleQueryService {
    public Schedule getSchedule(Long scheduleId, User user);
}
