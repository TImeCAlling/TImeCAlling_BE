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
    public static class PushMessageSettingCreateDTO {
        private Long id;
        private Long userId;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class PushMessageSettingListDTO {
        private Long id;
        private Long userId;
        private Integer offset;
        private Boolean isActive;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class PushMessageSettingDetailDTO {
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
    public static class PushMessageSettingUpdateBodyDTO {
        private Long id;
        private String body;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class PushMessageSettingUpdateMusicDTO {
        private Long id;
        private String music;
        private String musicUrl;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class PushMessageSettingUpdateIsActiveDTO {
        private Long id;
        private Boolean isActive;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class PushMessageSettingDeleteDTO {
        private Long id;
    }
}
