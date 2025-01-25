package TImeCAlling.spring.converter.alarmList;

import TImeCAlling.spring.domain.AlarmList;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.alarmList.AlarmListRequestDTO;
import TImeCAlling.spring.web.dto.alarmList.AlarmListResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AlarmListConverter {

    public static AlarmList toAlarmList(
            User user, AlarmListRequestDTO.CreateDTO createDTO) {

        return AlarmList.builder()
                .user(user)
                .offset(createDTO.getOffset())
                .body(createDTO.getBody())
                .music(createDTO.getMusic())
                .musicUrl(createDTO.getMusicUrl())
                .isActive(createDTO.getIsActive())
                .build();
    }

    public static AlarmListResponseDTO.UpdateBodyDTO toUpdateBodyDto(AlarmList alarmList) {

        return AlarmListResponseDTO.UpdateBodyDTO.builder()
                .alarmId(alarmList.getId())
                .body(alarmList.getBody())
                .build();
    }

    public static AlarmListResponseDTO.UpdateMusicDTO toUpdateMusicDto(AlarmList alarmList) {

        return AlarmListResponseDTO.UpdateMusicDTO.builder()
                .alarmId(alarmList.getId())
                .music(alarmList.getMusic())
                .musicUrl(alarmList.getMusicUrl())
                .build();
    }

    public static AlarmListResponseDTO.UpdateIsActiveDTO toUpdateIsActiveDto(
            AlarmList alarmList) {

        return AlarmListResponseDTO.UpdateIsActiveDTO.builder()
                .alarmId(alarmList.getId())
                .isActive(alarmList.getIsActive())
                .build();
    }

    public static AlarmListResponseDTO.CreateDTO toCreateDTO(AlarmList alarmList) {

        return AlarmListResponseDTO.CreateDTO.builder()
                .alarmId(alarmList.getId())
                .userId(alarmList.getUser().getId())
                .build();
    }


    public static AlarmListResponseDTO.DeleteDTO toDeleteDTO(AlarmList alarmList) {

        return AlarmListResponseDTO.DeleteDTO.builder()
                .alarmId(alarmList.getId())
                .build();
    }

    public AlarmListResponseDTO.ListDTO toListDTO(AlarmList alarmList) {
        return AlarmListResponseDTO.ListDTO.builder()
                .alarmId(alarmList.getId())
                .userId(alarmList.getUser().getId())
                .offset(alarmList.getOffset())
                .isActive(alarmList.getIsActive())
                .build();
    }

    public AlarmListResponseDTO.DetailDTO toDetailDTO(AlarmList alarmList) {
        return AlarmListResponseDTO.DetailDTO.builder()
                .alarmId(alarmList.getId())
                .userId(alarmList.getUser().getId())
                .offset(alarmList.getOffset())
                .body(alarmList.getBody())
                .music(alarmList.getMusic())
                .musicUrl(alarmList.getMusicUrl())
                .isActive(alarmList.getIsActive())
                .build();
    }
}

