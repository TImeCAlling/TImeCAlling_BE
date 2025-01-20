package TImeCAlling.spring.converter.user;

import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.SocialType;
import TImeCAlling.spring.web.dto.schedule.ScheduleResponseDTO;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;

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
    
    public static ScheduleResponseDTO.MyScheduleRateDTO toMyScheduleRateDTO(User user) {
        int success = user.getSuccess();
        int failed = user.getFailed();
        
        double percentage = (success + failed == 0) ? 0.0 : ((double) success / (success + failed)) * 100;
        
        return ScheduleResponseDTO.MyScheduleRateDTO.builder()
                .total(success + failed)
                .failed(success)
                .success(success)
                .successRate(percentage)
                .build();
    }
}
