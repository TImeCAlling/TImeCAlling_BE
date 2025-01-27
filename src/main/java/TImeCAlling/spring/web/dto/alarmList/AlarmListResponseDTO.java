package TImeCAlling.spring.web.dto.alarmList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AlarmListResponseDTO {

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class CreateDTO {
        private Long alarmId;
        private Long userId;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class ListDTO {
        private Long alarmId;
        private Long userId;
        private Integer offset;
        private Boolean isActive;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class DetailDTO {
        private Long alarmId;
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
        private Long alarmId;
        private String body;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UpdateMusicDTO {
        private Long alarmId;
        private String music;
        private String musicUrl;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UpdateIsActiveDTO {
        private Long alarmId;
        private Boolean isActive;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class DeleteDTO {
        private Long alarmId;
    }
}
