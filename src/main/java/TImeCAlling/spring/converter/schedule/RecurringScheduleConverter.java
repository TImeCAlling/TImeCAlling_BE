package TImeCAlling.spring.converter.schedule;

import TImeCAlling.spring.domain.RecurringSchedule;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.enums.RepeatDay;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;

import java.util.List;

public class RecurringScheduleConverter {
    
    public static RecurringSchedule toRecurringSchedule(Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request) {
        
        List<RepeatDay> repeatDays = request.getRepeatDays().stream()
                .map(RepeatDay::valueOf)
                .toList();
        
        return RecurringSchedule.builder()
                .start(request.getStart())
                .end(request.getEnd())
                .repeatDays(repeatDays)
                .schedule(schedule)
                .build();
    }
}
