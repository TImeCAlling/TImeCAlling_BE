package TImeCAlling.spring.service.alarmList;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.alarmList.AlarmListRequestDTO;
import TImeCAlling.spring.web.dto.alarmList.AlarmListResponseDTO;

public interface AlarmListCommandService {

    AlarmListResponseDTO.CreateDTO createAlarmList(
            AlarmListRequestDTO.CreateDTO createDTO, User user);

    AlarmListResponseDTO.DeleteDTO deleteAlarmList(Long pushMessageSettingId);

    AlarmListResponseDTO.UpdateBodyDTO updateAlarmList(
            Long id, AlarmListRequestDTO.UpdateBodyDTO pushMessageSettingUpdateBodyDTO);

    AlarmListResponseDTO.UpdateMusicDTO updateAlarmList(
            Long id, AlarmListRequestDTO.UpdateMusicDTO updateMusicDTO);

    AlarmListResponseDTO.UpdateIsActiveDTO updateAlarmList(
            Long id, AlarmListRequestDTO.UpdateIsActiveDTO updateIsActiveDTO);
}
