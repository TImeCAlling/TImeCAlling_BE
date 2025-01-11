package TImeCAlling.spring.domain.enums;

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
}