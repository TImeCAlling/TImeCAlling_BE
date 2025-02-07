package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ScheduleHandler;
import TImeCAlling.spring.converter.schedule.RecurringScheduleConverter;
import TImeCAlling.spring.domain.RecurringSchedule;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.enums.RepeatDay;
import TImeCAlling.spring.repository.schedule.RecurringScheduleRepository;
import TImeCAlling.spring.repository.schedule.ScheduleRepository;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecurringScheduleServiceImpl implements RecurringScheduleService {
    
    private final RecurringScheduleRepository recurringScheduleRepository;
    private final ScheduleRepository scheduleRepository;
    
    @Override
    public void createRecurringSchedule(Schedule schedule, ScheduleRequestDTO.ScheduleCommandDTO request) {
        RecurringSchedule recurringSchedule = RecurringScheduleConverter.toRecurringSchedule(schedule, request);
        recurringScheduleRepository.save(recurringSchedule);
    }
}
