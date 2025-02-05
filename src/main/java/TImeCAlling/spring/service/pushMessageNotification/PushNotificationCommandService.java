package TImeCAlling.spring.service.pushMessageNotification;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.fcm.FcmTokenResponseDTO;
import TImeCAlling.spring.web.dto.pushMessageNotification.PushNotificationRequestDTO;
import TImeCAlling.spring.web.dto.pushMessageNotification.PushNotificationResponseDTO;

import java.io.IOException;

public interface PushNotificationCommandService {

    FcmTokenResponseDTO.UpdateDTO updateFcmToken(User user, String fcmToken);

    PushNotificationResponseDTO.NotificationDetails notifyUser(User user,
                               PushNotificationRequestDTO.NotificationDetails notificationDTO) throws IOException;
}
