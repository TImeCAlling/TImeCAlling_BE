package TImeCAlling.spring.web.dto.pushMessageSetting;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PushMessageSettingRequestDTO {

    public static class PushMessageSettingCreateDTO {
        @NotNull(message = "userId는 필수 항목입니다.")
        private Long userId;

        @NotNull(message = "offset은 필수 값입니다.")
        private Integer offset;

        @Size(max = 20, message = "body는 최대 20자까지 입력 가능합니다.")
        private String body;

        @Size(max = 20, message = "music은 최대 20자까지 입력 가능합니다.")
        private String music;

        @Size(max = 2083, message = "musicUrl은 최대 2083자까지 입력 가능합니다.")
        private String musicUrl;

//        @NotNull(message = "isActive는 필수 값입니다.")
        private Boolean isActive;
    }

    public static class PushMessageSettingUpdateBodyDTO {
        @Size(max = 20, message = "body는 최대 20자까지 입력 가능합니다.")
        private String body;
    }

    public static class PushMessageSettingUpdateMusicDTO {
        @Size(max = 20, message = "music은 최대 20자까지 입력 가능합니다.")
        private String music;

        @Size(max = 2083, message = "musicUrl은 최대 2083자까지 입력 가능합니다.")
        private String musicUrl;
    }

    public static class PushMessageSettingUpdateIsActiveDTO {
        private Boolean isActive;
    }
}
