package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.Checklist;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChecklistService {
    
    void createChecklists(Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request);
    
    List<Checklist> patchChecklists(Schedule schedule, ScheduleRequestDTO.SchedulePatchDTO request);
    
    List<Checklist> getCheckListByDateAndUser(LocalDate date, User user);
    
}
