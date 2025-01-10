package TImeCAlling.spring.domain.enums;

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
}