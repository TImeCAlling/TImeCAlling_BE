package TImeCAlling.spring.web.dto.user;

import lombok.*;

public class UserResponseDTO {
    
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserCreateDTO{

        Long id;
    }
    
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserDeleteDTO {
        
        Long id;
    }
    
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserUpdateDTO{
        
        Long id;
    }
    
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public static class UserMyPageDTO{
        
        String nickname;
        Integer avgPrepTime;
        String freeTime;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class UserSignUpResultDTO {
        Long userId;
        String accessToken;
    }

}
