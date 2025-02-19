package TImeCAlling.spring.domain.enums;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ChecklistHandler;

public enum Late {
    LESS_THAN_5_MIN("5분 미만"),
    FIVE_TO_TEN_MIN("5분 ~ 10분"),
    MORE_THAN_10_MIN("10분 이상"),
    EMPTY("없음");
    
    private final String description;
    
    Late(String description) {this.description = description;}
    
    public String getDescription() {
        return description;
    }

    public static Late fromDescription(String description) {
        for (Late late : Late.values()) {
            if (late.getDescription().equals(description)) {
                return late;
            }
        }
        throw new ChecklistHandler(ErrorStatus.LATE_NOT_FOUND);
    }
}
