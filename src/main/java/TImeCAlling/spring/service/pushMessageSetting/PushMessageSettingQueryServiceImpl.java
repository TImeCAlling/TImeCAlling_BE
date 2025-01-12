package TImeCAlling.spring.service.pushMessageSetting;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.PushMessageSettingHandler;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.converter.pushMessageSetting.PushMessageSettingConverter;
import TImeCAlling.spring.repository.pushMessageSetting.PushMessageSettingRepository;
import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PushMessageSettingQueryServiceImpl implements PushMessageSettingQueryService {

    private final PushMessageSettingRepository pushMessageSettingRepository;
    private final PushMessageSettingConverter pushMessageSettingConverter;

    @Override
    public List<PushMessageSettingResponseDTO.ListDTO> getListPushMessageSetting(Long userId) {

        return pushMessageSettingRepository.findByUserIdOrThrow(userId).stream()
                .map(pushMessageSettingConverter::toListDTO)
                .toList();
    }

    @Override
    public PushMessageSettingResponseDTO.DetailDTO getDetailPushMessageSetting(Long pushMessageSettingId) {
        return pushMessageSettingRepository.findById(pushMessageSettingId)
                .map(pushMessageSettingConverter::toDetailDTO)
                .orElseThrow(() -> new PushMessageSettingHandler(ErrorStatus.PUSH_SETTING_NOT_FOUND));
    }
}
