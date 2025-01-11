package TImeCAlling.spring.domain.enums;

public enum FreeTime {
    TIGHT("딱딱"),
    RELAXED("여유"),
    PLENTY("넉넉");

    private final String description;

    FreeTime(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}