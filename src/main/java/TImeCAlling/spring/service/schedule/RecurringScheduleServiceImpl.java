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
    public void createRecurringSchedule(Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request) {
        RecurringSchedule recurringSchedule = RecurringScheduleConverter.toRecurringSchedule(schedule, request);
        recurringScheduleRepository.save(recurringSchedule);
    }

    @Override
    public void createShareRecurringSchedule(Long scheduleId, Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request) {

        Schedule shareSchedule = scheduleRepository.findById(scheduleId).get();

        Set<RepeatDay> shareRepeatDays = new HashSet<>(shareSchedule.getRecurringSchedule().getRepeatDays());
        Set<RepeatDay> requestRepeatDays = request.getRepeatDays().stream()
                .map(RepeatDay::valueOf)
                .collect(Collectors.toSet());

        if (!shareRepeatDays.equals(requestRepeatDays)) {
            scheduleRepository.delete(schedule);
            throw new ScheduleHandler(ErrorStatus.REPEAT_DAYS_MISMATCH);
        } else if (!shareSchedule.getIsRepeat().equals(request.getIsRepeat()) ||
                !shareSchedule.getRecurringSchedule().getStart().equals(request.getStart()) ||
                !shareSchedule.getRecurringSchedule().getEnd().equals(request.getEnd())) {
            scheduleRepository.delete(schedule);
            throw new ScheduleHandler(ErrorStatus.RECURRING_MISMATCH);
        }
        
        RecurringSchedule recurringSchedule = RecurringScheduleConverter.toRecurringSchedule(schedule, request);
        recurringScheduleRepository.save(recurringSchedule);
    }
}
