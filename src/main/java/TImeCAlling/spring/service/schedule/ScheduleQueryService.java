package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;

import java.util.List;

public interface ScheduleQueryService {
    public Schedule getScheduleWithChecklist(Long checklistId, User user);
    public Schedule getSchedule(Long scheduleId, User user);
    public Schedule getShareSchedule(Long scheduleId, User user);
    public Schedule getSchedule(Long scheduleId);
    public List<ScheduleResponseDTO.SharedScheduleUserDTO> getSharedScheduleUsers(Schedule schedule);
}
