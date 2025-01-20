package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ScheduleHandler;
import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.schedule.ScheduleRepository;
import TImeCAlling.spring.validation.annotation.ExistSchedule;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleQueryServiceImpl implements ScheduleQueryService {
    private final ScheduleRepository scheduleRepository;

    @Override
    @ExistSchedule
    public Schedule getSchedule(Long scheduleId, User user) {
        
        return scheduleRepository.findByIdAndUser(scheduleId, user).orElseThrow(() -> new ScheduleHandler(ErrorStatus._BAD_REQUEST));
    }

    @Override
    @ExistSchedule
    public Schedule getSchedule(Long scheduleId) {

        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleHandler(ErrorStatus.SCHEDULE_NOT_FOUND));
    }

    @Override
    @ExistSchedule
    public List<ScheduleResponseDTO.SharedScheduleUserDTO> getSharedScheduleUsers(Schedule schedule) {

        if (schedule.getShareId() == null) {
            return Collections.emptyList();
        }

        List<Schedule> users = scheduleRepository.findByShareId(schedule.getShareId())
                .orElseThrow(() -> new ScheduleHandler(ErrorStatus.SCHEDULE_SHARE_ID_NOT_FOUND));

        return ScheduleConverter.toSharedScheduleUserDTO(users);
    }
}
