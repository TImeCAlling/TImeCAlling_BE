package TImeCAlling.spring.web.dto.pushMessageNotification;

import lombok.Getter;

public class PushNotificationRequestDTO {

    @Getter
    public static class NotificationDetails {
        private Long receiverId;
        private String shareId;
        private String scheduledDate;
    }
}
