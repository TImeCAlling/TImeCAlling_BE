package TImeCAlling.spring.service.pushMessageSetting;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.PushMessageSettingHandler;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.converter.pushMessageSetting.PushMessageSettingConverter;
import TImeCAlling.spring.domain.PushMessageSetting;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.pushMessageSetting.PushMessageSettingRepository;
import TImeCAlling.spring.repository.user.UserRepository;
import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingRequestDTO;
import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PushMessageSettingCommandServiceImpl implements PushMessageSettingCommandService {

    private final PushMessageSettingRepository pushMessageSettingRepository;
    private final UserRepository userRepository;

    @Override
    public PushMessageSettingResponseDTO.CreateDTO createPushMessageSetting(
            PushMessageSettingRequestDTO.CreateDTO createDTO) {

        User user = userRepository.findById(createDTO.getUserId()).orElseThrow(
                () -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        PushMessageSetting pushMessageSetting = PushMessageSettingConverter
                .toPushMessageSetting(user, createDTO);

        PushMessageSetting savedPushMessageSetting = pushMessageSettingRepository.save(pushMessageSetting);

        return PushMessageSettingConverter.toCreateDTO(savedPushMessageSetting);
    }

    @Override
    public PushMessageSettingResponseDTO.DeleteDTO deletePushMessageSetting(
            Long pushMessageSettingId) {

        PushMessageSetting findPushMessageSetting = getFindPushMessageSetting(pushMessageSettingId);
        pushMessageSettingRepository.delete(findPushMessageSetting);

        return PushMessageSettingConverter.toDeleteDTO(findPushMessageSetting);
    }

    @Override
    public PushMessageSettingResponseDTO.UpdateBodyDTO updatePushMessageSetting(
            Long id, PushMessageSettingRequestDTO.UpdateBodyDTO updateBodyDTO) {

        PushMessageSetting findPushMessageSetting = getFindPushMessageSetting(id);
        findPushMessageSetting.update(null, updateBodyDTO.getBody(), null, null);
        PushMessageSetting savedPushMessageSetting = pushMessageSettingRepository.save(findPushMessageSetting);

        return PushMessageSettingConverter.toUpdateBodyDto(savedPushMessageSetting);
    }

    @Override
    public PushMessageSettingResponseDTO.UpdateMusicDTO updatePushMessageSetting(
            Long id, PushMessageSettingRequestDTO.UpdateMusicDTO updateMusicDTO) {

        PushMessageSetting findPushMessageSetting = getFindPushMessageSetting(id);
        findPushMessageSetting.update(null, null, updateMusicDTO.getMusic(),
                updateMusicDTO.getMusicUrl());
        PushMessageSetting savedPushMessageSetting = pushMessageSettingRepository.save(findPushMessageSetting);

        return PushMessageSettingConverter.toUpdateMusicDto(savedPushMessageSetting);
    }

    @Override
    public PushMessageSettingResponseDTO.UpdateIsActiveDTO updatePushMessageSetting(
            Long id, PushMessageSettingRequestDTO.UpdateIsActiveDTO updateIsActiveDTO) {

        PushMessageSetting findPushMessageSetting = getFindPushMessageSetting(id);
        findPushMessageSetting.updateIsActive(updateIsActiveDTO.getIsActive());
        PushMessageSetting savedPushMessageSetting = pushMessageSettingRepository.save(findPushMessageSetting);

        return PushMessageSettingConverter.toUpdateIsActiveDto(savedPushMessageSetting);
    }

    private PushMessageSetting getFindPushMessageSetting(Long id) {
        return pushMessageSettingRepository.findById(id).orElseThrow(
                () -> new PushMessageSettingHandler(ErrorStatus.PUSH_SETTING_NOT_FOUND));
    }
}
