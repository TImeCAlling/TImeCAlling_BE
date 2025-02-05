package TImeCAlling.spring.web.dto.pushMessageNotification;

import TImeCAlling.spring.domain.enums.MessageStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PushNotificationResponseDTO {

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class NotificationDetails {
        private Long receiverId;
        private String shareId;
        private MessageStatus status;
    }
}
