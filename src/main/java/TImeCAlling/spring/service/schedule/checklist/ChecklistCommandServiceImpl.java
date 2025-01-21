package TImeCAlling.spring.service.schedule.checklist;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ScheduleHandler;
import TImeCAlling.spring.domain.Checklist;
import TImeCAlling.spring.domain.RecurringSchedule;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.*;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.RepeatDay;
import TImeCAlling.spring.repository.schedule.ChecklistRepository;
import TImeCAlling.spring.repository.schedule.RecurringScheduleRepository;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.web.dto.checklist.ChecklistRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistCommandServiceImpl implements ChecklistCommandService {

    private final ChecklistRepository checklistRepository;
    private final RecurringScheduleRepository recurringScheduleRepository;
    private final UserQueryService userQueryService;

    @Override
    public void createChecklists(Schedule schedule, ScheduleRequestDTO.ScheduleCreateDTO request) {

        List<Checklist> checklists = new ArrayList<>();

        if (request.getIsRepeat()) {
            RecurringSchedule recurringSchedule = recurringScheduleRepository.findByScheduleId(schedule.getId()).orElseThrow(
                    () -> new ScheduleHandler(ErrorStatus.RECURRING_SCHEDULE_NOT_FOUND)
            );
            // 반복 일정 날짜 계산
            List<LocalDate> repeatDates = calculateRepeatDates(
                    recurringSchedule.getStart(),
                    recurringSchedule.getEnd(),
                    recurringSchedule.getRepeatDays()
            );
            // 반복 일정에 대한 체크리스트 생성
            for (LocalDate date : repeatDates) {
                Checklist checklist = Checklist.builder()
                        .isSuccess(null)
                        .spare(null)
                        .late(null)
                        .reason(null)
                        .external(null)
                        .isFit(null)
                        .date(date)
                        .schedule(schedule)
                        .build();

                checklists.add(checklist);
            }
        } else {
            // 단일 일정에 대한 체크리스트 생성
            Checklist checklist = Checklist.builder()
                    .isSuccess(null)
                    .spare(null)
                    .late(null)
                    .reason(null)
                    .external(null)
                    .isFit(null)
                    .date(request.getMeetTime().toLocalDate())
                    .schedule(schedule)
                    .build();

            checklists.add(checklist);
        }
        checklistRepository.saveAll(checklists);
    }

    @Override
    public List<Checklist> patchChecklists(Schedule schedule, ScheduleRequestDTO.SchedulePatchDTO request) {
        checklistRepository.deleteAllByScheduleId(schedule.getId());

        List<Checklist> checklists = new ArrayList<>();

        if (request.getIsRepeat()) {
            RecurringSchedule recurringSchedule = recurringScheduleRepository.findByScheduleId(schedule.getId()).orElseThrow(
                    () -> new ScheduleHandler(ErrorStatus.RECURRING_SCHEDULE_NOT_FOUND)
            );
            // 반복 일정 날짜 계산
            List<LocalDate> repeatDates = calculateRepeatDates(
                    recurringSchedule.getStart(),
                    recurringSchedule.getEnd(),
                    recurringSchedule.getRepeatDays()
            );
            // 반복 일정에 대한 체크리스트 생성
            for (LocalDate date : repeatDates) {
                Checklist checklist = Checklist.builder()
                        .isSuccess(null)
                        .spare(null)
                        .late(null)
                        .reason(null)
                        .external(null)
                        .isFit(null)
                        .date(date)
                        .schedule(schedule)
                        .build();

                checklists.add(checklist);
            }
        } else {
            // 단일 일정에 대한 체크리스트 생성
            Checklist checklist = Checklist.builder()
                    .isSuccess(null)
                    .spare(null)
                    .late(null)
                    .reason(null)
                    .external(null)
                    .isFit(null)
                    .date(request.getMeetTime().toLocalDate())
                    .schedule(schedule)
                    .build();

            checklists.add(checklist);
        }

        return checklists;
    }

    
    @Override
    public List<Checklist> getCheckListByDateAndUser(LocalDate date, User user) {
        return checklistRepository.findChecklistsBySchedule_User_IdAndDate(user.getId(), date);
    }
    
    private List<LocalDate> calculateRepeatDates(LocalDate start, LocalDate end, java.util.List<RepeatDay> repeatDays) {
        List<LocalDate> repeatDates = new ArrayList<>();

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            DayOfWeek currentDay = date.getDayOfWeek();
            if (repeatDays.stream().anyMatch(repeatDay -> repeatDay.getDayOfWeek() == currentDay)) {
                repeatDates.add(date);
            }
        }

        return repeatDates;
    }

    @Override
    @Transactional
    public Long updateChecklist(Long scheduleId, Long userId, ChecklistRequestDTO.UpdateDTO request) {
        Checklist checklist = checklistRepository.findByScheduleIdAndDate(scheduleId, request.getDate());
        checklist.updateChecklist(request.getIsSuccess(), Spare.fromDescription(request.getSpare()), Late.fromDescription(request.getLate()), Reason.fromDescription(request.getReason()), External.fromDescription(request.getExternal()), request.getIsFit());

        User user = userQueryService.findOne(userId);
        user.addResult(request.getIsSuccess());

        return checklist.getId();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteExpiredChecklists() {
        LocalDate now = LocalDate.now();
        checklistRepository.deleteExpiredChecklists(now);
    }
}
