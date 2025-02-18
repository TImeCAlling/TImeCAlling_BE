package TImeCAlling.spring.web.dto.pushMessageNotification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PushNotificationRequestDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationDetails {
        private Long receiverId;
        private String shareId;
        private String scheduledDate;
    }
}
