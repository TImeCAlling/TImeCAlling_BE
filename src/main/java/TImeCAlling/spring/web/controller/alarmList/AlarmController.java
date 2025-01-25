package TImeCAlling.spring.web.controller.alarmList;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.alarmList.AlarmListCommandService;
import TImeCAlling.spring.service.alarmList.AlarmListQueryService;
import TImeCAlling.spring.web.dto.alarmList.AlarmListRequestDTO;
import TImeCAlling.spring.web.dto.alarmList.AlarmListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmListCommandService alarmListCommandService;
    private final AlarmListQueryService alarmListQueryService;

    @PostMapping
    public ApiResponse<AlarmListResponseDTO.CreateDTO> createAlarmList(
            @RequestBody AlarmListRequestDTO.CreateDTO pushMessageCreateDTO,
            @AuthenticationPrincipal User user) {

        AlarmListResponseDTO.CreateDTO response = alarmListCommandService
                .createAlarmList(pushMessageCreateDTO, user);

        return ApiResponse.onSuccess(response);
    }

    @GetMapping
    public ApiResponse<List<AlarmListResponseDTO.ListDTO>> getAlarmList(
            @AuthenticationPrincipal User user) {

        List<AlarmListResponseDTO.ListDTO> response =
                alarmListQueryService.getAlarmList(user);

        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/{alarmId}")
    public ApiResponse<AlarmListResponseDTO.DetailDTO> getDetailAlarmList(
            @PathVariable("alarmId") Long alarmId) {

        AlarmListResponseDTO.DetailDTO response = alarmListQueryService
                .getDetailAlarmList(alarmId);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{alarmId}/body")
    public ApiResponse<AlarmListResponseDTO.UpdateBodyDTO> updateBodyAlarmList(
            @PathVariable("alarmId") Long alarmId,
            @RequestBody AlarmListRequestDTO.UpdateBodyDTO updateBodyDTO) {

        AlarmListResponseDTO.UpdateBodyDTO response = alarmListCommandService
                .updateAlarmList(alarmId, updateBodyDTO);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{alarmId}/music")
    public ApiResponse<AlarmListResponseDTO.UpdateMusicDTO> updateMusicAlarmList(
            @PathVariable("alarmId") Long alarmId,
            @RequestBody AlarmListRequestDTO.UpdateMusicDTO updateMusicDTO) {

        AlarmListResponseDTO.UpdateMusicDTO response = alarmListCommandService
                .updateAlarmList(alarmId, updateMusicDTO);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{alarmId}/activation")
    public ApiResponse<AlarmListResponseDTO.UpdateIsActiveDTO> updateActivationAlarmList(
            @PathVariable("alarmId") Long alarmId,
            @RequestBody AlarmListRequestDTO.UpdateIsActiveDTO updateActivationDTO) {

        AlarmListResponseDTO.UpdateIsActiveDTO response = alarmListCommandService
                .updateAlarmList(alarmId, updateActivationDTO);

        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{alarmId}")
    public ApiResponse<AlarmListResponseDTO.DeleteDTO> deleteAlarmList(
            @PathVariable("alarmId") Long alarmId) {

        AlarmListResponseDTO.DeleteDTO response = alarmListCommandService
                .deleteAlarmList(alarmId);

        return ApiResponse.onSuccess(response);
    }
}
