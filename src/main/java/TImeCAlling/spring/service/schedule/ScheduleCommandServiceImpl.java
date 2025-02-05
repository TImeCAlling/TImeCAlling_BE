package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ScheduleHandler;
import TImeCAlling.spring.converter.schedule.ScheduleConverter;
import TImeCAlling.spring.domain.*;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.RepeatDay;
import TImeCAlling.spring.repository.schedule.RecurringScheduleRepository;
import TImeCAlling.spring.repository.schedule.ScheduleRepository;
import TImeCAlling.spring.service.schedule.checklist.ChecklistCommandService;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService {
    private final ScheduleRepository scheduleRepository;
     private final RecurringScheduleRepository recurringScheduleRepository;
    private final ChecklistCommandService checklistService;

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

        Schedule findSchedule = scheduleRepository.findById(scheduleId).get();
        scheduleRepository.findByIdAndUser(scheduleId, user)
                .orElseThrow(() -> new ScheduleHandler(ErrorStatus._BAD_REQUEST));

        if (findSchedule.getShareId() != null) {
            if (!findSchedule.getName().equals(request.getName()) ||
                    !findSchedule.getChecklists().get(0).getDate().equals(request.getMeetDate()) ||
                    !findSchedule.getMeetTime().equals(request.getMeetTime()) ||
                    !findSchedule.getPlace().equals(request.getPlace()) ||
                    !findSchedule.getLongitude().equals(request.getLongitude()) ||
                    !findSchedule.getLatitude().equals(request.getLatitude()) ||
                    !findSchedule.getIsRepeat().equals(request.getIsRepeat()) ||
                    !findSchedule.getRecurringSchedule().getStart().equals(request.getStart()) ||
                    !findSchedule.getRecurringSchedule().getEnd().equals(request.getEnd())) {
                throw new ScheduleHandler(ErrorStatus.SCHEDULE_MISMATCH);
                }
        }

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
        String shareId = schedule.getShareId();
        scheduleRepository.delete(schedule);
        if (scheduleRepository.existsById(scheduleId)) {
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_DELETE_FAIL);
        }
        if (scheduleRepository.countByShareId(shareId) == 1) {
            Schedule findSchedule = scheduleRepository.findByShareId(shareId).get();
            findSchedule.setShareId(null);
        }
        return schedule;
    }

    @Override
    public Schedule createShareSchedule(User user, Long scheduleId, ScheduleRequestDTO.ScheduleCreateDTO request) {

        Schedule shareSchedule = scheduleRepository.findById(scheduleId).get();

        // 공유 일정과 기본 정보 일치하는지 확인
        if (!shareSchedule.getName().equals(request.getName()))
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_NAME_MISMATCH);
        else if (!shareSchedule.getChecklists().get(0).getDate().equals(request.getMeetDate()))
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_DATE_MISMATCH);
        else if (!shareSchedule.getMeetTime().equals(request.getMeetTime()))
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_TIME_MISMATCH);
        else if (!shareSchedule.getPlace().equals(request.getPlace()))
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_PLACE_MISMATCH);
        else if (!shareSchedule.getLongitude().equals(request.getLongitude()))
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_LONGITUDE_MISMATCH);
        else if (!shareSchedule.getLatitude().equals(request.getLatitude()))
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_LATITUDE_MISMATCH);

        // 공유 id 생성
        String shareId = getShareId(shareSchedule);
        if (scheduleRepository.existsByShareIdAndUser(shareId, user)) {
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_ALREADY_EXIST);
        }

        Schedule newSchedule = ScheduleConverter.toShareSchedule(user, request, shareId);
        return scheduleRepository.save(newSchedule);
    }

    private String getShareId(Schedule schedule) {
        if (schedule.getShareId() == null) {
            String shareId = UUID.randomUUID().toString();
            schedule.setShareId(shareId);
            scheduleRepository.save(schedule);
        }
        return schedule.getShareId();
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
