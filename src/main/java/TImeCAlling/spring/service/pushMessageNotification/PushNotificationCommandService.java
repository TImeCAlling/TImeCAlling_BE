package TImeCAlling.spring.service.pushMessageNotification;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.fcm.FcmTokenResponseDTO;

public interface PushNotificationCommandService {

    FcmTokenResponseDTO.UpdateDTO updateFcmToken(User user, String fcmToken);
}
