package TImeCAlling.spring.converter.user;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.SocialType;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;

public class UserConverter {
    
    public static User toUser(UserRequestDTO.UserCreateDTO userCreateDTO) {
        
        return User.builder()
                .nickname(userCreateDTO.getNickname())
                .socialType(SocialType.valueOf(userCreateDTO.getSocialType()))
                .avgPrepTime(userCreateDTO.getAvgPrepTime())
                .freeTime(FreeTime.valueOf(userCreateDTO.getFreeTime()))
                .fcmToken("기본값")
                .build();
    }

    public static UserResponseDTO.UserSignUpResultDTO toUserSignUpResultDTO(User user, String accessToken) {
        return UserResponseDTO.UserSignUpResultDTO.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .build();
    }

    public static User toUser(String nickname) {

        return User.builder()
                .nickname(nickname)
                .socialType(SocialType.KAKAO)
                .build();
    }

}
