package TImeCAlling.spring.converter.pushMessageSetting;

import TImeCAlling.spring.domain.PushMessageSetting;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingRequestDTO;
import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PushMessageSettingConverter {

    public static PushMessageSetting toPushMessageSetting(
            User user, PushMessageSettingRequestDTO.CreateDTO createDTO) {

        return PushMessageSetting.builder()
                .user(user)
                .offset(createDTO.getOffset())
                .body(createDTO.getBody())
                .music(createDTO.getMusic())
                .musicUrl(createDTO.getMusicUrl())
                .isActive(createDTO.getIsActive())
                .build();
    }

    public static PushMessageSettingResponseDTO.UpdateBodyDTO toUpdateBodyDto(PushMessageSetting pushMessageSetting) {

        return PushMessageSettingResponseDTO.UpdateBodyDTO.builder()
                .id(pushMessageSetting.getId())
                .body(pushMessageSetting.getBody())
                .build();
    }

    public static PushMessageSettingResponseDTO.UpdateMusicDTO toUpdateMusicDto(PushMessageSetting pushMessageSetting) {

        return PushMessageSettingResponseDTO.UpdateMusicDTO.builder()
                .id(pushMessageSetting.getId())
                .music(pushMessageSetting.getMusic())
                .musicUrl(pushMessageSetting.getMusicUrl())
                .build();
    }

    public static PushMessageSettingResponseDTO.UpdateIsActiveDTO toUpdateIsActiveDto(
            PushMessageSetting pushMessageSetting) {

        return PushMessageSettingResponseDTO.UpdateIsActiveDTO.builder()
                .id(pushMessageSetting.getId())
                .isActive(pushMessageSetting.getIsActive())
                .build();
    }

    public static PushMessageSettingResponseDTO.CreateDTO toCreateDTO(PushMessageSetting pushMessageSetting) {

        return PushMessageSettingResponseDTO.CreateDTO.builder()
                .id(pushMessageSetting.getId())
                .userId(pushMessageSetting.getUser().getId())
                .build();
    }


    public static PushMessageSettingResponseDTO.DeleteDTO toDeleteDTO(PushMessageSetting pushMessageSetting) {

        return PushMessageSettingResponseDTO.DeleteDTO.builder()
                .id(pushMessageSetting.getId())
                .build();
    }

    public PushMessageSettingResponseDTO.ListDTO toListDTO(PushMessageSetting pushMessageSetting) {
        return PushMessageSettingResponseDTO.ListDTO.builder()
                .id(pushMessageSetting.getId())
                .userId(pushMessageSetting.getUser().getId())
                .offset(pushMessageSetting.getOffset())
                .isActive(pushMessageSetting.getIsActive())
                .build();
    }

    public PushMessageSettingResponseDTO.DetailDTO toDetailDTO(PushMessageSetting pushMessageSetting) {
        return PushMessageSettingResponseDTO.DetailDTO.builder()
                .id(pushMessageSetting.getId())
                .userId(pushMessageSetting.getUser().getId())
                .offset(pushMessageSetting.getOffset())
                .body(pushMessageSetting.getBody())
                .music(pushMessageSetting.getMusic())
                .musicUrl(pushMessageSetting.getMusicUrl())
                .isActive(pushMessageSetting.getIsActive())
                .build();
    }
}

