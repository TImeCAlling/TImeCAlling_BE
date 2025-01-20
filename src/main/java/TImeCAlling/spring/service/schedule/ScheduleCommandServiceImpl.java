package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ScheduleHandler;
import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.domain.*;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.RepeatDay;
import TImeCAlling.spring.repository.schedule.RecurringScheduleRepository;
import TImeCAlling.spring.repository.schedule.ScheduleRepository;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService {
    private final ScheduleRepository scheduleRepository;
     private final RecurringScheduleRepository recurringScheduleRepository;
    private final ChecklistService checklistService;

    @Override
    @Transactional
    public Schedule createSchedule(User user, ScheduleRequestDTO.ScheduleCreateDTO request) {
        Schedule newSchedule = ScheduleConverter.toSchedule(user, request);
        Schedule savedSchedule = scheduleRepository.save(newSchedule);

        return savedSchedule;
    }
    
    @Override
    @Transactional
    public Schedule patchSchedule(Long scheduleId, User user, ScheduleRequestDTO.SchedulePatchDTO request) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleHandler(ErrorStatus.SCHEDULE_NOT_FOUND));
        scheduleRepository.findByIdAndUser(scheduleId, user)
                .orElseThrow(() -> new ScheduleHandler(ErrorStatus._BAD_REQUEST));

        List<Category> categories = request.getCategories().stream()
                .map(categoryDTO -> Category.builder()
                        .name(categoryDTO.getCategoryName())
                        .color(categoryDTO.getColor())
                        .build())
                .collect(Collectors.toList());
        
        List<Checklist> checklists = findSchedule.getChecklists();
        
        if (request.getIsRepeat()) {
            List<RepeatDay> repeatDays = request.getRepeatDays().stream()
                    .map(RepeatDay::valueOf)
                    .collect(Collectors.toList());
            
            // 기존에 RecurringSchedule이 없는 경우 새로 생성
            if (findSchedule.getRecurringSchedule() == null) {
                RecurringSchedule newRecurringSchedule = RecurringSchedule.builder()
                        .start(request.getStart())
                        .end(request.getEnd())
                        .repeatDays(repeatDays)
                        .schedule(findSchedule)
                        .build();
                
                findSchedule.setRecurringSchedule(newRecurringSchedule);
            } else {
                // 기존 RecurringSchedule 업데이트
                if (isChanged(request, findSchedule, repeatDays)) {
                    findSchedule.getRecurringSchedule().update(request.getStart(), request.getEnd(), repeatDays);
                    checklists = checklistService.patchChecklists(findSchedule, request);
                }
            }
            
            if (isChanged(request, findSchedule, repeatDays)) {
                findSchedule.getRecurringSchedule().update(request.getStart(), request.getEnd(), repeatDays);
                checklists = checklistService.patchChecklists(findSchedule, request);
            }
            // 일정 업데이트
            findSchedule.updateSchedule(request.getName(),
                    request.getBody(),
                    request.getMeetTime(),
                    request.getPlace(),
                    request.getLongitude(),
                    request.getLatitude(),
                    request.getMoveTime(),
                    FreeTime.valueOf(request.getFreeTime()),
                    request.getIsRepeat(),
                    categories,
                    checklists);
        } else {
            // 반복 일정이 아닌 경우 RecurringSchedule 제거
            findSchedule.setRecurringSchedule(null);
            checklists = checklistService.patchChecklists(findSchedule, request);
            
            findSchedule.updateSchedule(request.getName(),
                    request.getBody(),
                    request.getMeetTime(),
                    request.getPlace(),
                    request.getLongitude(),
                    request.getLatitude(),
                    request.getMoveTime(),
                    FreeTime.valueOf(request.getFreeTime()),
                    request.getIsRepeat(),
                    categories,
                    checklists);
        }
        
        return scheduleRepository.save(findSchedule);
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
    
    // 두 리스트 값 비교 메서드
    
    private static boolean areListsEqual(List<?> list1, List<?> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        return Objects.equals(new HashSet<>(list1), new HashSet<>(list2));
    }
    
    private static boolean isChanged(ScheduleRequestDTO.SchedulePatchDTO request, Schedule findSchedule, List<RepeatDay> repeatDays) {
        return findSchedule.getRecurringSchedule().getStart() != request.getStart() || findSchedule.getRecurringSchedule().getEnd() != request.getEnd() || !areListsEqual(findSchedule.getRecurringSchedule().getRepeatDays(), repeatDays);
    }
}
