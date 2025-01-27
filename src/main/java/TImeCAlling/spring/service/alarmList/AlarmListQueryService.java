package TImeCAlling.spring.service.alarmList;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.alarmList.AlarmListResponseDTO;

import java.util.List;

public interface AlarmListQueryService {

    List<AlarmListResponseDTO.ListDTO> getAlarmList(User user);

    AlarmListResponseDTO.DetailDTO getDetailAlarmList(Long alarmList);
}
