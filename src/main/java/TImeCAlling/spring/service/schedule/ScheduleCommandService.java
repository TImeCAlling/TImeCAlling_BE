package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;

public interface ScheduleCommandService {
    public Schedule createSchedule(User user, ScheduleRequestDTO.ScheduleCommandDTO request);
    public Schedule patchSchedule(Long scheduleId, User user, ScheduleRequestDTO.ScheduleCommandDTO request);
    public Schedule deleteSchedule(Long scheduleId, User user);
    public Schedule createShareSchedule(User user, Long scheduleId, ScheduleRequestDTO.ScheduleCommandDTO request);
}
