package TImeCAlling.spring.web.controller.pushMessageNotification;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.service.pushMessageNotification.PushNotificationCommandService;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.web.dto.fcm.FcmTokenRequestDTO;
import TImeCAlling.spring.web.dto.fcm.FcmTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/push-requests")
public class PushMessageNotificationController {

    private final PushNotificationCommandService pushNotificationCommandService;
    private final UserQueryService userQueryService;

    @PatchMapping("/fcm-token")
    public ApiResponse<FcmTokenResponseDTO.UpdateDTO> updateFcmToken(
            @RequestBody FcmTokenRequestDTO.UpdateDTO updateDTO) {

        return ApiResponse.onSuccess(pushNotificationCommandService.updateFcmToken(
                userQueryService.findOne(updateDTO.getUserId()), updateDTO.getFcmToken()));
    }
}
