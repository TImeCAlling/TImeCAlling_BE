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
    public Schedule createSchedule(User user, ScheduleRequestDTO.ScheduleCommandDTO request) {

        Schedule newSchedule = ScheduleConverter.toSchedule(user, request);
        return scheduleRepository.save(newSchedule);
    }
    
    @Override
    @Transactional
    public Schedule patchSchedule(Long scheduleId, User user, ScheduleRequestDTO.ScheduleCommandDTO request) {

        Schedule findSchedule = scheduleRepository.findByIdAndUser(scheduleId, user)
                .orElseThrow(() -> new ScheduleHandler(ErrorStatus._BAD_REQUEST));

        // 공유 일정 검증
        validateShareSchedule(findSchedule, request);

        List<Category> categories = request.getCategories().stream()
                .map(categoryDTO -> Category.builder()
                        .name(categoryDTO.getCategoryName())
                        .color(categoryDTO.getColor())
                        .build())
                .collect(Collectors.toList());

        List<Checklist> checklists = handleRecurringSchedule(findSchedule, request);

        // 일정 업데이트
        findSchedule.updateSchedule(
                request.getName(),
                request.getBody(),
                request.getMeetTime(),
                request.getPlace(),
                request.getLongitude(),
                request.getLatitude(),
                request.getMoveTime(),
                FreeTime.valueOf(request.getFreeTime()),
                request.getIsRepeat(),
                categories,
                checklists
        );
        return scheduleRepository.save(findSchedule);
    }

    @Transactional
    protected List<Checklist> handleRecurringSchedule(Schedule schedule, ScheduleRequestDTO.ScheduleCommandDTO request) {

        // 반복 일정이 아닌 경우 RecurringSchedule 제거
        if (!request.getIsRepeat()) {
            schedule.setRecurringSchedule(null);
            return checklistService.patchChecklists(schedule, request);
        }

        List<RepeatDay> repeatDays = request.getRepeatDays().stream()
                .map(RepeatDay::valueOf)
                .collect(Collectors.toList());

        // 기존에 RecurringSchedule이 없는 경우 새로 생성
        if (schedule.getRecurringSchedule() == null) {
            RecurringSchedule newRecurringSchedule = RecurringSchedule.builder()
                    .start(request.getStart())
                    .end(request.getEnd())
                    .repeatDays(repeatDays)
                    .schedule(schedule)
                    .build();

            schedule.setRecurringSchedule(newRecurringSchedule);
        } else {
            // 기존 RecurringSchedule 업데이트
            if (isChanged(request, schedule, repeatDays)) {
                schedule.getRecurringSchedule().update(request.getStart(), request.getEnd(), repeatDays);
            }
        }
        return checklistService.patchChecklists(schedule, request);
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
    @Transactional
    public Schedule createShareSchedule(User user, Long scheduleId, ScheduleRequestDTO.ScheduleCommandDTO request) {

        Schedule shareSchedule = scheduleRepository.findById(scheduleId).get();
        
        // 공유 id 생성
        String shareId = getShareId(shareSchedule);
        if (scheduleRepository.existsByShareIdAndUser(shareId, user)) {
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_ALREADY_EXIST);
        }

        // 공유 일정 검증
        validateShareSchedule(shareSchedule, request);

        Schedule newSchedule = ScheduleConverter.toShareSchedule(user, request, shareId);
        return scheduleRepository.save(newSchedule);
    }

    private void validateShareSchedule(Schedule schedule, ScheduleRequestDTO.ScheduleCommandDTO request) {

        if (schedule.getShareId() == null)
            return;

        if (!schedule.getName().equals(request.getName()) ||
                !schedule.getChecklists().get(0).getDate().equals(request.getMeetDate()) ||
                !schedule.getMeetTime().equals(request.getMeetTime()) ||
                !schedule.getPlace().equals(request.getPlace()) ||
                !schedule.getLongitude().equals(request.getLongitude()) ||
                !schedule.getLatitude().equals(request.getLatitude()) ||
                !schedule.getIsRepeat().equals(request.getIsRepeat())) {
            throw new ScheduleHandler(ErrorStatus.SCHEDULE_MISMATCH);
        }

        // isRepeat가 true일 때, 반복 일정 검증
        if (schedule.getIsRepeat()) {
            Set<RepeatDay> repeatDays = new HashSet<>(schedule.getRecurringSchedule().getRepeatDays());
            Set<RepeatDay> requestRepeatDays = request.getRepeatDays().stream()
                    .map(RepeatDay::valueOf)
                    .collect(Collectors.toSet());

            if (!repeatDays.equals(requestRepeatDays) ||
                    !schedule.getRecurringSchedule().getStart().equals(request.getStart()) ||
                    !schedule.getRecurringSchedule().getEnd().equals(request.getEnd())) {
                throw new ScheduleHandler(ErrorStatus.SCHEDULE_MISMATCH);
            }
        }
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
    
    private static boolean isChanged(ScheduleRequestDTO.ScheduleCommandDTO request, Schedule findSchedule, List<RepeatDay> repeatDays) {
        return findSchedule.getRecurringSchedule().getStart() != request.getStart() || findSchedule.getRecurringSchedule().getEnd() != request.getEnd() || !areListsEqual(findSchedule.getRecurringSchedule().getRepeatDays(), repeatDays);
    }
}
