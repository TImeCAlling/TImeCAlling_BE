package TImeCAlling.spring.service.alarmList;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.AlarmListHandler;
import TImeCAlling.spring.converter.alarmList.AlarmListConverter;
import TImeCAlling.spring.domain.AlarmList;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.alarmList.AlarmListRepository;
import TImeCAlling.spring.web.dto.alarmList.AlarmListRequestDTO;
import TImeCAlling.spring.web.dto.alarmList.AlarmListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmListCommandServiceImpl implements AlarmListCommandService {

    private final AlarmListRepository alarmListRepository;

    @Override
    public AlarmListResponseDTO.CreateDTO createAlarmList(
            AlarmListRequestDTO.CreateDTO createDTO, User user) {

        AlarmList alarmList = AlarmListConverter
                .toAlarmList(user, createDTO);

        AlarmList savedAlarmList = alarmListRepository.save(alarmList);

        return AlarmListConverter.toCreateDTO(savedAlarmList);
    }

    @Override
    public AlarmListResponseDTO.DeleteDTO deleteAlarmList(Long alarmListId) {

        AlarmList findAlarmList = getFindAlarmList(alarmListId);
        alarmListRepository.delete(findAlarmList);

        return AlarmListConverter.toDeleteDTO(findAlarmList);
    }

    @Override
    public AlarmListResponseDTO.UpdateBodyDTO updateAlarmList(
            Long alarmListId, AlarmListRequestDTO.UpdateBodyDTO updateBodyDTO) {

        AlarmList findAlarmList = getFindAlarmList(alarmListId);
        findAlarmList.update(null, updateBodyDTO.getBody(), null, null);
        AlarmList savedAlarmList = alarmListRepository.save(findAlarmList);

        return AlarmListConverter.toUpdateBodyDto(savedAlarmList);
    }

    @Override
    public AlarmListResponseDTO.UpdateMusicDTO updateAlarmList(
            Long id, AlarmListRequestDTO.UpdateMusicDTO updateMusicDTO) {

        AlarmList findAlarmList = getFindAlarmList(id);
        findAlarmList.update(null, null, updateMusicDTO.getMusic(),
                updateMusicDTO.getMusicUrl());
        AlarmList savedAlarmList = alarmListRepository.save(findAlarmList);

        return AlarmListConverter.toUpdateMusicDto(savedAlarmList);
    }

    @Override
    public AlarmListResponseDTO.UpdateIsActiveDTO updateAlarmList(
            Long id, AlarmListRequestDTO.UpdateIsActiveDTO updateIsActiveDTO) {

        AlarmList findAlarmList = getFindAlarmList(id);
        findAlarmList.updateIsActive(updateIsActiveDTO.getIsActive());
        AlarmList savedAlarmList = alarmListRepository.save(findAlarmList);

        return AlarmListConverter.toUpdateIsActiveDto(savedAlarmList);
    }

    private AlarmList getFindAlarmList(Long id) {
        return alarmListRepository.findById(id).orElseThrow(
                () -> new AlarmListHandler(ErrorStatus.ALARM_LIST_NOT_FOUND));
    }
}
