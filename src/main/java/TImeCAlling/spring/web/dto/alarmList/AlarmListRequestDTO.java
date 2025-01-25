package TImeCAlling.spring.web.dto.alarmList;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class AlarmListRequestDTO {


    @Getter
    public static class CreateDTO {

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


    @Getter
    public static class UpdateBodyDTO {
        @Size(max = 20, message = "body는 최대 20자까지 입력 가능합니다.")
        private String body;
    }


    @Getter
    public static class UpdateMusicDTO {
        @Size(max = 20, message = "music은 최대 20자까지 입력 가능합니다.")
        private String music;

        @Size(max = 2083, message = "musicUrl은 최대 2083자까지 입력 가능합니다.")
        private String musicUrl;
    }


    @Getter
    public static class UpdateIsActiveDTO {
        private Boolean isActive;
    }
}
