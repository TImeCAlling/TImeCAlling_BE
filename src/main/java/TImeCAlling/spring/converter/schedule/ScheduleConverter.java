package TImeCAlling.spring.converter.schedule;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.Spare;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ScheduleConverter {
    public static ScheduleResponseDTO.ScheduleCreateDTO toScheduleCreateDTO(Schedule schedule) {
        return ScheduleResponseDTO.ScheduleCreateDTO.builder()
                .scheduleId(schedule.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Schedule toSchedule(User user, ScheduleRequestDTO.ScheduleCreateDTO request){
        Spare spare = Spare.valueOf(request.getSpare());
//        switch (request.getSpare()){
//            case "딱딱":
//                spare = Spare.ON_TIME;
//                break;
//            case "여유":
//                spare = Spare.FIVE_TO_TEN_MIN;
//                break;
//            case "넉넉":
//                spare = Spare.MORE_THAN_10_MIN;
//                break;
//        }

        return Schedule.builder()
                .user(user)
                .name(request.getName())
                .body(request.getBody())
                .meetTime(request.getMeetTime())
                .place(request.getPlace())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .moveTime(request.getMoveTime())
                .spare(spare)
                .isRepeat(request.getIsRepeat())
                .scheduleCategories(new ArrayList<>())
                .build();
    }

    public static ScheduleResponseDTO.ScheduleGetDTO toScheduleGetDTO(Schedule schedule) {
        return ScheduleResponseDTO.ScheduleGetDTO.builder()
                .scheduleId(schedule.getId())
                .meetTime(schedule.getMeetTime())
                .isRepeat(schedule.getIsRepeat())
                .place(schedule.getPlace())
                .body(schedule.getBody())
                .spare(schedule.getSpare())
                .build();
    }
}
