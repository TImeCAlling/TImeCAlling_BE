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
}
