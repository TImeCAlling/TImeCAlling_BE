package TImeCAlling.spring.service.pushMessageNotification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * FCM 기본 알림 메세지 Enum
 */
@Getter
@RequiredArgsConstructor
public enum PushDefaultMessage {
    PUSH_DEFAULT_MESSAGE0(0, "푸시 테스트 메세지 0"),
    PUSH_DEFAULT_MESSAGE1(1, "푸시 테스트 메세지 1"),
    PUSH_DEFAULT_MESSAGE2(2, "푸시 테스트 메세지 2"),
    PUSH_DEFAULT_MESSAGE3(3, "푸시 테스트 메세지 3"),
    PUSH_DEFAULT_MESSAGE4(4, "푸시 테스트 메세지 4"),
    PUSH_DEFAULT_MESSAGE5(5, "푸시 테스트 메세지 5"),
    PUSH_DEFAULT_MESSAGE6(6, "푸시 테스트 메세지 6");

    private final int idx;
    private final String body;

    private static final Map<Integer, String> INDEX_MAP = new HashMap<>();

    static {
        for (PushDefaultMessage message : values()) {
            INDEX_MAP.put(message.idx, message.body);
        }
    }

    public static Optional<String> fromIndex(int index) {
        return Optional.ofNullable(PushDefaultMessage.INDEX_MAP.get(index));
    }
}
