package TImeCAlling.spring.web.controller.pushMessageNotification;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.pushMessageNotification.PushNotificationCommandService;
import TImeCAlling.spring.web.dto.fcm.FcmTokenRequestDTO;
import TImeCAlling.spring.web.dto.fcm.FcmTokenResponseDTO;
import TImeCAlling.spring.web.dto.pushMessageNotification.PushNotificationRequestDTO;
import TImeCAlling.spring.web.dto.pushMessageNotification.PushNotificationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/push-requests")
public class PushMessageNotificationController {

    private final PushNotificationCommandService pushNotificationCommandService;

    /**
     * FCM 토큰 업데이트
     *
     * @param updateDTO FCM 토큰 업데이트 요청 DTO
     * @param user 인증된 사용자
     * @return 업데이트된 토큰을 소유한 userID 정보
     */
    @Operation(
            summary = "FCM 토큰 업데이트",
            description = "사용자의 FCM 토큰을 업데이트합니다. 클라이언트가 새 FCM 토큰을 전달해야 합니다."
    )
    @PatchMapping("/fcm-token")
    public ApiResponse<FcmTokenResponseDTO.UpdateDTO> updateFcmToken(
            @RequestBody @Parameter(description = "FCM 토큰 업데이트 요청 데이터") FcmTokenRequestDTO.UpdateDTO updateDTO,
            @AuthenticationPrincipal User user) {

        return ApiResponse.onSuccess(pushNotificationCommandService.updateFcmToken(
                user, updateDTO.getFcmToken()));
    }

    /**
     * 깨우기
     *
     * @param notificationDTO 수신자의 id, shareId, body 구성 DTO
     */
    @Operation(
            summary = "같은 일정을 공유하는 팀원에게 FCM 알람이 가도록합니다.",
            description = "수신자의 id와 공유하고 싶은 스케줄은 'shareId'를 통해 보내주세요. body 같은 내용 추가 부분도" +
            "확장성을 고려하여 미리 추가하였습니다. 없으시면 기본 메세지로 json을 날려주세요!"
    )
    @PostMapping()
    public ApiResponse<PushNotificationResponseDTO.NotificationDetails> userWakeUp(
            @RequestBody @Parameter(description = "유저 깨우기 관련 request 데이터")
            PushNotificationRequestDTO.NotificationDetails notificationDTO,
            @AuthenticationPrincipal User user) throws IOException {

        return ApiResponse.onSuccess(pushNotificationCommandService.notifyUser(user, notificationDTO));
    }

}
