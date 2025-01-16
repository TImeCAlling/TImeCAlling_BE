package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ScheduleHandler;
import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.Spare;
import TImeCAlling.spring.repository.schedule.ScheduleRepository;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService {
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public Schedule createSchedule(User user, ScheduleRequestDTO.ScheduleCreateDTO request) {
        Schedule newSchedule = ScheduleConverter.toSchedule(user, request);
        return scheduleRepository.save(newSchedule);
    }

    @Override
    @Transactional
    public Schedule patchSchedule(Long scheduleId, User user, ScheduleRequestDTO.SchedulePatchDTO request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleHandler(ErrorStatus.SCHEDULE_NOT_FOUND));
        scheduleRepository.findByIdAndUser(scheduleId, user).orElseThrow(() -> new ScheduleHandler(ErrorStatus._BAD_REQUEST));

        if (request.getBody() != null) {
            schedule.setBody(request.getBody());
        }
        schedule.setMoveTime(request.getMoveTime());
        schedule.setSpare(Spare.valueOf(request.getSpare()));
        schedule.setIsRepeat(request.getIsRepeat());

        return schedule;
    }

    @Override
    @Transactional
    public Schedule deleteSchedule(Long scheduleId, User user) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleHandler(ErrorStatus.SCHEDULE_NOT_FOUND));
        scheduleRepository.findByIdAndUser(scheduleId, user).orElseThrow(() -> new ScheduleHandler(ErrorStatus._BAD_REQUEST));
        scheduleRepository.delete(schedule);
        if (scheduleRepository.existsById(scheduleId)) {
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_DELETE_FAIL);
        }
        return schedule;
    }
}
