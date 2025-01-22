package TImeCAlling.spring.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserAuthDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoUserInfoDTO {
        Long id; // 회원번호
        String connected_at; // 서비스에 연결 완료된 시각
        KaKaoPropertiesDTO properties;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoPropertiesDTO {
        @NotBlank
        String nickname;
        String profile_image; // 프로필 사진 URL
        String thumbnail_image; // 프로필 미리보기 이미지 URL
    }

}
