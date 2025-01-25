package TImeCAlling.spring.web.dto.user;

import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.validation.annotation.ValidEnum;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class UserRequestDTO {
    
    @Getter
    public static class UserCreateDTO {
        
        String nickname;
        Integer avgPrepTime;
        @ValidEnum(enumClass = FreeTime.class)
        String freeTime;
    }
    
    @Getter
    public static class UserUpdateDTO {
        
        String nickname;
        Integer avgPrepTime;
        String freeTime;
    }

    @Getter
    public static class UserSignUpDTO {

        String kakaoAccessToken;
        String nickname;
        Integer avgPrepTime;
        @ValidEnum(enumClass = FreeTime.class)
        String freeTime;
    }

    @Getter
    public static class UserLoginDTO {

        String kakaoAccessToken;
    }

    @Getter
    public static class refreshTokenDTO {

        String accessToken;
        String refreshToken;
    }
}
