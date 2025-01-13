package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;

public interface ScheduleCommandService {
    public Schedule createSchedule(User user, ScheduleRequestDTO.ScheduleCreateDTO request);
}
