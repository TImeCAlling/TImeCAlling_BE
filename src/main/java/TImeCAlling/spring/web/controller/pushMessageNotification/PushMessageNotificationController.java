package TImeCAlling.spring.web.controller.pushMessageNotification;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.pushMessageNotification.PushNotificationCommandService;
import TImeCAlling.spring.web.dto.fcm.FcmTokenRequestDTO;
import TImeCAlling.spring.web.dto.fcm.FcmTokenResponseDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/push-requests")
public class PushMessageNotificationController {

    private final PushNotificationCommandService pushNotificationCommandService;

    @PatchMapping("/fcm-token")
    public ApiResponse<FcmTokenResponseDTO.UpdateDTO> updateFcmToken(
            @RequestBody @Parameter(description = "FCM 토큰 업데이트 요청 데이터") FcmTokenRequestDTO.UpdateDTO updateDTO,
            @AuthenticationPrincipal User user) {

        return ApiResponse.onSuccess(pushNotificationCommandService.updateFcmToken(
                user, updateDTO.getFcmToken()));
    }
}
