package TImeCAlling.spring.domain.enums;

public enum Fit {
    YES("네"),
    NO("아니오");
    
    private final String description;
    
    Fit(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
