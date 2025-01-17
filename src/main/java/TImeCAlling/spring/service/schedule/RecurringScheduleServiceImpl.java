package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.converter.schedule.RecurringScheduleConverter;
import TImeCAlling.spring.domain.RecurringSchedule;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.repository.schedule.RecurringScheduleRepository;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecurringScheduleServiceImpl implements RecurringScheduleService {
    
    private final RecurringScheduleRepository recurringScheduleRepository;
    
    @Override
    public void createRecurringSchedule(Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request) {
        RecurringSchedule recurringSchedule = RecurringScheduleConverter.toRecurringSchedule(schedule, request);
        recurringScheduleRepository.save(recurringSchedule);
    }
}
