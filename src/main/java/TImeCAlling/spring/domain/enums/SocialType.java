package TImeCAlling.spring.domain.enums;

public enum SocialType {
    GOOGLE("구글"),
    KAKAO("카카오");

    private final String displayName;

    SocialType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}