package TImeCAlling.spring.domain.enums;

public enum Success {
    SUCCESS("성공"),
    FAILURE("실패"),
    NONE("미정");
    
    private final String description;
    
    Success(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}