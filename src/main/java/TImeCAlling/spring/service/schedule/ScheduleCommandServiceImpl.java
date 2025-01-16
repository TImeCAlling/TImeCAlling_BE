package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ScheduleHandler;
import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.domain.Category;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.Spare;
import TImeCAlling.spring.repository.schedule.CategoryRepository;
import TImeCAlling.spring.repository.schedule.ScheduleRepository;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService {
    private final ScheduleRepository scheduleRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Schedule createSchedule(User user, ScheduleRequestDTO.ScheduleCreateDTO request) {
        Schedule newSchedule = ScheduleConverter.toSchedule(user, request);
        Schedule savedSchedule = scheduleRepository.save(newSchedule);
        List<Category> categories = request.getCategories().stream()
                .map(categoryDTO -> Category.builder()
                        .name(categoryDTO.getCategoryName())
                        .color(categoryDTO.getCategoryColor())
                        .schedule(savedSchedule)
                        .build())
                .collect(Collectors.toList());
        categoryRepository.saveAll(categories);
        return savedSchedule;
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
