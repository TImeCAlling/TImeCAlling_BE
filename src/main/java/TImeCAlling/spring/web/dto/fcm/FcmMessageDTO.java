package TImeCAlling.spring.web.dto.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * FCM 전송 Format DTO
 *
 */
@Getter
@Builder
public class FcmMessageDTO {
    private boolean validateOnly;
    private FcmMessageDTO.Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
//        private FcmMessageDTO.Notification notification;
        private String token;
        private Map<String, String> data;
        private FcmMessageDTO.AndroidConfig android;
    }

//    @Builder
//    @AllArgsConstructor
//    @Getter
//    public static class Notification {
//        private String title;
//        private String body;
//    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class AndroidConfig {
        private String ttl; //Time-to-Live ("3600" -> 1시간)
        private String priority; // 우선순위 설정 -> 타임콜링은 시간이 중요하므로 high 설정 권장
    }
}
