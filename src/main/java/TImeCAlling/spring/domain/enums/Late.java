package TImeCAlling.spring.domain.enums;

public enum Late {
    LESS_THAN_5_MIN("5분 미만"),
    FIVE_TO_TEN_MIN("5분 ~ 10분"),
    MORE_THAN_10_MIN("10분 이상");
    
    private final String displayName;
    
    Late(String displayName) {this.displayName = displayName;}
    
    public String getDisplayName() {
        return displayName;
    }
    
    
}
