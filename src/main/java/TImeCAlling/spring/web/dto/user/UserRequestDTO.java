package TImeCAlling.spring.web.dto.user;

import lombok.Getter;

public class UserRequestDTO {
    
    @Getter
    public static class UserCreateDTO {
        
        String nickname;
        String socialType;
        Integer avgPrepTime;
        String freeTime;
    }
}
