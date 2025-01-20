package TImeCAlling.spring.domain.enums;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ChecklistHandler;

public enum Spare {
    MORE_THAN_10_MIN("10분 이상"),
    FIVE_TO_TEN_MIN("5분 ~ 10분"),
    ON_TIME("거의 정시");
    
    private final String description;
    
    Spare(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    public static Spare fromDescription(String description) {
        for (Spare spare : Spare.values()) {
            if (spare.getDescription().equals(description)) {
                return spare;
            }
        }
        throw new ChecklistHandler(ErrorStatus.SPARE_NOT_FOUND);
    }
}
