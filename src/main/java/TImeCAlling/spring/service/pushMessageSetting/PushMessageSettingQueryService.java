package TImeCAlling.spring.service.pushMessageSetting;

import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingResponseDTO;

import java.util.List;

public interface PushMessageSettingQueryService {

    List<PushMessageSettingResponseDTO.ListDTO> getListPushMessageSetting(Long userId);

    PushMessageSettingResponseDTO.DetailDTO getDetailPushMessageSetting(Long pushMessageSettingId);
}
