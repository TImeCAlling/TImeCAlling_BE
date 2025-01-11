package TImeCAlling.spring.domain.enums;

public enum MessageStatus {
    PENDING("대기 중"),
    SENT("전송 완료"),
    FAILED("전송 실패");
    
    private final String description;
    
    MessageStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
