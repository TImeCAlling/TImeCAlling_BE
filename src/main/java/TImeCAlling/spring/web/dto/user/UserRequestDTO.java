package TImeCAlling.spring.web.dto.user;

import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.validation.annotation.ValidEnum;
import lombok.Getter;

public class UserRequestDTO {
    
    @Getter
    public static class UserCreateDTO {
        
        String nickname;
        String socialType;
        Integer avgPrepTime;
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
        String profileUrl;
        String nickname;
        Integer avgPrepTime;
        @ValidEnum(enumClass = FreeTime.class)
        String freeTime;
    }

    @Getter
    public static class UserLoginDTO {

        String kakaoAccessToken;
    }
}
