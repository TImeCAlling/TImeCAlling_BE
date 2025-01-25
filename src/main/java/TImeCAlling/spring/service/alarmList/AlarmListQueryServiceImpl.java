package TImeCAlling.spring.service.alarmList;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.AlarmListHandler;
import TImeCAlling.spring.converter.alarmList.AlarmListConverter;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.alarmList.AlarmListRepository;
import TImeCAlling.spring.web.dto.alarmList.AlarmListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmListQueryServiceImpl implements AlarmListQueryService {

    private final AlarmListRepository alarmListRepository;
    private final AlarmListConverter alarmListConverter;

    @Override
    public List<AlarmListResponseDTO.ListDTO> getAlarmList(User user) {

        return alarmListRepository.findByUserIdOrThrow(user.getId()).stream()
                .map(alarmListConverter::toListDTO)
                .toList();
    }

    @Override
    public AlarmListResponseDTO.DetailDTO getDetailAlarmList(Long alarmList) {
        return alarmListRepository.findById(alarmList)
                .map(alarmListConverter::toDetailDTO)
                .orElseThrow(() -> new AlarmListHandler(ErrorStatus.ALARM_LIST_NOT_FOUND));
    }
}
