package TImeCAlling.spring.domain.enums;

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
    
}
