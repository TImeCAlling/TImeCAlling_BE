package TImeCAlling.spring.web.dto.user;

import lombok.*;

public class UserResponseDTO {
    
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserCreateDTO{

        Long userId;
    }
    
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserDeleteDTO {
        
        Long userId;
    }
    
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserUpdateDTO {
        
        Long userId;
        String nickname;
        Integer avgPrepTime;
        String freeTime;
        String profileImage;
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserMyPageDTO {

        Long userId;
        String nickname;
        Integer avgPrepTime;
        String freeTime;
        Integer success;
        Integer failed;
        String profileImage;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class UserSignUpResultDTO {

        Long userId;
        String accessToken;
        String refreshToken;
    }

}
