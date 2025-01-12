package TImeCAlling.spring.service.pushMessageSetting;

import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingRequestDTO;
import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingResponseDTO;

public interface PushMessageSettingCommandService {

    PushMessageSettingResponseDTO.CreateDTO createPushMessageSetting(
            PushMessageSettingRequestDTO.CreateDTO createDTO);

    PushMessageSettingResponseDTO.DeleteDTO deletePushMessageSetting(Long pushMessageSettingId);

    PushMessageSettingResponseDTO.UpdateBodyDTO updatePushMessageSetting(
            Long id, PushMessageSettingRequestDTO.UpdateBodyDTO pushMessageSettingUpdateBodyDTO);

    PushMessageSettingResponseDTO.UpdateMusicDTO updatePushMessageSetting(
            Long id, PushMessageSettingRequestDTO.UpdateMusicDTO updateMusicDTO);

    PushMessageSettingResponseDTO.UpdateIsActiveDTO updatePushMessageSetting(
            Long id, PushMessageSettingRequestDTO.UpdateIsActiveDTO updateIsActiveDTO);
}
