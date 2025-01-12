package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.repository.schedule.ScheduleRepository;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService {
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public Schedule createSchedule(ScheduleRequestDTO.ScheduleCreateDTO request) {
        Schedule newSchedule = ScheduleConverter.toSchedule(request);

        return scheduleRepository.save(newSchedule);
    }
}
