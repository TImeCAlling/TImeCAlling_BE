package TImeCAlling.spring.web.dto.pushMessageSetting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PushMessageSettingResponseDTO {

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class CreateDTO {
        private Long id;
        private Long userId;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class ListDTO {
        private Long id;
        private Long userId;
        private Integer offset;
        private Boolean isActive;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class DetailDTO {
        private Long id;
        private Long userId;
        private Integer offset;
        private String body;
        private String music;
        private String musicUrl;
        private Boolean isActive;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UpdateBodyDTO {
        private Long id;
        private String body;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UpdateMusicDTO {
        private Long id;
        private String music;
        private String musicUrl;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UpdateIsActiveDTO {
        private Long id;
        private Boolean isActive;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class DeleteDTO {
        private Long id;
    }
}
