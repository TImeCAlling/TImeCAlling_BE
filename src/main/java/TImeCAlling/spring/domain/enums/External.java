package TImeCAlling.spring.domain.enums;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ChecklistHandler;

public enum External {
    WEATHER("날씨"),
    TRAFFIC_CONDITIONS("교통상황"),
    UNEXPECTED_PERSONAL_ISSUE("예상치 못한 개인 사정"),
    OTHER("기타");

    private final String description;

    External(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static External fromDescription(String description) {
        for (External external : External.values()) {
            if (external.getDescription().equals(description)) {
                return external;
            }
        }
        throw new ChecklistHandler(ErrorStatus.EXTERNAL_NOT_FOUND);
    }
}