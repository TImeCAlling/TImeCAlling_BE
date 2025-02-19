package TImeCAlling.spring.converter.user;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.SocialType;
import TImeCAlling.spring.web.dto.user.UserAuthDTO;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;

public class UserConverter {
    
    public static User toUser(UserRequestDTO.UserCreateDTO userCreateDTO) {
        
        return User.builder()
                .nickname(userCreateDTO.getNickname())
                .socialType(SocialType.KAKAO)
                .avgPrepTime(userCreateDTO.getAvgPrepTime())
                .freeTime(FreeTime.valueOf(userCreateDTO.getFreeTime()))
                .fcmToken("기본값")
                .build();
    }

    public static UserResponseDTO.UserSignUpResultDTO toUserSignUpResultDTO(User user, String accessToken, String refreshToken) {
        return UserResponseDTO.UserSignUpResultDTO.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static UserResponseDTO.UserUpdateDTO toUserUpdateDTO(User user) {
        return UserResponseDTO.UserUpdateDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .avgPrepTime(user.getAvgPrepTime())
                .freeTime(String.valueOf(user.getFreeTime()))
                .profileImage(user.getProfileImage().getFileUrl())
                .build();
    }

    public static UserResponseDTO.UserMyPageDTO toUserMyPageDTO(User user) {
        return UserResponseDTO.UserMyPageDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .avgPrepTime(user.getAvgPrepTime())
                .freeTime(String.valueOf(user.getFreeTime()))
                .success(user.getSuccess())
                .failed(user.getFailed())
                .profileImage(user.getProfileImage().getFileUrl())
                .build();
    }

    public static User toUser(UserAuthDTO.KaKaoUserInfoDTO userInfo, UserRequestDTO.UserSignUpDTO request) {

        return User.builder()
                .nickname(request.getNickname())
                .socialId(userInfo.getId())
                .socialType(SocialType.KAKAO)
                .avgPrepTime(request.getAvgPrepTime())
                .freeTime(FreeTime.valueOf(request.getFreeTime()))
                .fcmToken("기본값")
                .build();
    }

    public static ScheduleResponseDTO.MyScheduleRateDTO toMyScheduleRateDTO(User user) {
        int success = user.getSuccess();
        int failed = user.getFailed();
        
        double percentage = (success + failed == 0) ? 0.0 : ((double) success / (success + failed)) * 100;
        
        return ScheduleResponseDTO.MyScheduleRateDTO.builder()
                .total(success + failed)
                .failed(failed)
                .success(success)
                .successRate(percentage)
                .build();
    }

    public static UserResponseDTO.RefreshTokenResultDTO toRefreshTokenResultDTO(User user, String accessToken) {
        return UserResponseDTO.RefreshTokenResultDTO.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .build();
    }

    public static UserResponseDTO.UserIdDTO toUserIdDTO(User user) {
        return UserResponseDTO.UserIdDTO.builder()
                .userId(user.getId())
                .build();
    }
}
