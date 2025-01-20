package TImeCAlling.spring.domain.enums;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ChecklistHandler;

public enum Reason {
    TRAFFIC_JAM("교통 체증"),
    LATE_DEPARTURE("늦게 출발"),
    WRONG_DIRECTION("길을 잘못 찾음"),
    EARLY_DEPARTURE("일찍 출발"),
    SMOOTH_TRAFFIC("교통상황 원활"),
    CLOSE_TO_DESTINATION("목적지와 가까움");
    
    private final String description;
    
    Reason(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    public static Reason fromDescription(String description) {
        for (Reason reason : Reason.values()) {
            if (reason.getDescription().equals(description)) {
                return reason;
            }
        }
        throw new ChecklistHandler(ErrorStatus.REASON_NOT_FOUND);
    }
}