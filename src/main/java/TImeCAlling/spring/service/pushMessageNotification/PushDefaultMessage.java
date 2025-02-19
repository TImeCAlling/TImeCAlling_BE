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
    PUSH_DEFAULT_MESSAGE0(0, "벌써 약속 시간 다가와요!"),
    PUSH_DEFAULT_MESSAGE1(1, "슬슬 나가실 시간이에요!"),
    PUSH_DEFAULT_MESSAGE2(2, "준비되셨나요!"),
    PUSH_DEFAULT_MESSAGE3(3, "불은 끄셨나요!"),
    PUSH_DEFAULT_MESSAGE4(4, "잊어버린 물건은 없으신가요!");

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
