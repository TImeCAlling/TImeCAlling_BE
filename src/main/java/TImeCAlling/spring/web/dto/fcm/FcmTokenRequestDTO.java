package TImeCAlling.spring.web.dto.fcm;

import lombok.Builder;
import lombok.Getter;

public class FcmTokenRequestDTO {

    @Builder
    @Getter
    public static class UpdateDTO {
        private String fcmToken;
    }
}
