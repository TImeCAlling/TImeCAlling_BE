package TImeCAlling.spring.web.controller.pushMessageSetting;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.service.pushMessageSetting.PushMessageSettingCommandService;
import TImeCAlling.spring.service.pushMessageSetting.PushMessageSettingQueryService;
import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingRequestDTO;
import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/push-settings")
public class PushMessageSettingController {

    private final PushMessageSettingCommandService pushMessageSettingCommandService;
    private final PushMessageSettingQueryService pushMessageSettingQueryService;

    @PostMapping
    public ApiResponse<PushMessageSettingResponseDTO.CreateDTO> createPushMessageSetting(
            @RequestBody PushMessageSettingRequestDTO.CreateDTO pushMessageCreateDTO) {

        PushMessageSettingResponseDTO.CreateDTO response = pushMessageSettingCommandService
                .createPushMessageSetting(pushMessageCreateDTO);

        return ApiResponse.onSuccess(response);
    }

    @GetMapping
    public ApiResponse<List<PushMessageSettingResponseDTO.ListDTO>> getListPushMessageSetting(
            @RequestParam Long userId) {

        List<PushMessageSettingResponseDTO.ListDTO> response =
                pushMessageSettingQueryService.getListPushMessageSetting(userId);

        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/{push-settingId}")
    public ApiResponse<PushMessageSettingResponseDTO.DetailDTO> getDetailPushMessageSetting(
            @PathVariable("push-settingId") Long pushSettingId) {

        PushMessageSettingResponseDTO.DetailDTO response = pushMessageSettingQueryService
                .getDetailPushMessageSetting(pushSettingId);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{push-settingId}/body")
    public ApiResponse<PushMessageSettingResponseDTO.UpdateBodyDTO> updateBodyPushMessageSetting(
            @PathVariable("push-settingId") Long pushSettingId,
            @RequestBody PushMessageSettingRequestDTO.UpdateBodyDTO updateBodyDTO) {

        PushMessageSettingResponseDTO.UpdateBodyDTO response = pushMessageSettingCommandService
                .updatePushMessageSetting(pushSettingId, updateBodyDTO);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{push-settingId}/music")
    public ApiResponse<PushMessageSettingResponseDTO.UpdateMusicDTO> updateMusicPushMessageSetting(
            @PathVariable("push-settingId") Long pushSettingId,
            @RequestBody PushMessageSettingRequestDTO.UpdateMusicDTO updateMusicDTO) {

        PushMessageSettingResponseDTO.UpdateMusicDTO response = pushMessageSettingCommandService
                .updatePushMessageSetting(pushSettingId, updateMusicDTO);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{push-settingId}/activation")
    public ApiResponse<PushMessageSettingResponseDTO.UpdateIsActiveDTO> updateActivationPushMessageSetting(
            @PathVariable("push-settingId") Long pushSettingId,
            @RequestBody PushMessageSettingRequestDTO.UpdateIsActiveDTO updateActivationDTO) {

        PushMessageSettingResponseDTO.UpdateIsActiveDTO response = pushMessageSettingCommandService
                .updatePushMessageSetting(pushSettingId, updateActivationDTO);

        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{push-settingId}")
    public ApiResponse<PushMessageSettingResponseDTO.DeleteDTO> deletePushMessageSetting(
            @PathVariable("push-settingId") Long pushSettingId) {

        PushMessageSettingResponseDTO.DeleteDTO response = pushMessageSettingCommandService
                .deletePushMessageSetting(pushSettingId);

        return ApiResponse.onSuccess(response);
    }
}
