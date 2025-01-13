package TImeCAlling.spring.web.dto.pushMessageSetting;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class PushMessageSettingRequestDTO {


    @Getter
    public static class CreateDTO {
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

        /** 테스트용 생성자*/
        public CreateDTO(Long userId, Integer offset, String body, String music, String musicUrl, Boolean isActive) {
            this.userId = userId;
            this.offset = offset;
            this.body = body;
            this.music = music;
            this.musicUrl = musicUrl;
            this.isActive = isActive;
        }

        public CreateDTO() {};
    }


    @Getter
    public static class UpdateBodyDTO {
        @Size(max = 20, message = "body는 최대 20자까지 입력 가능합니다.")
        private String body;

        /** 테스트용 생성자*/
        public UpdateBodyDTO(String body) {
            this.body = body;
        }

        public UpdateBodyDTO() {};
    }


    @Getter
    public static class UpdateMusicDTO {
        @Size(max = 20, message = "music은 최대 20자까지 입력 가능합니다.")
        private String music;

        @Size(max = 2083, message = "musicUrl은 최대 2083자까지 입력 가능합니다.")
        private String musicUrl;

        /** 테스트용 생성자*/
        public UpdateMusicDTO(String music, String musicUrl) {
            this.music = music;
            this.musicUrl = musicUrl;
        }

        public UpdateMusicDTO() {};
    }


    @Getter
    public static class UpdateIsActiveDTO {
        private Boolean isActive;

        /** 테스트용 생성자*/
        public UpdateIsActiveDTO(Boolean isActive) {
            this.isActive = isActive;
        }

        public UpdateIsActiveDTO() {};
    }
}
