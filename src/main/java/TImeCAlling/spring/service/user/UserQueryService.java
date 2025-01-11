package TImeCAlling.spring.service.user;

import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;

public interface UserQueryService {
    
    UserResponseDTO.UserCreateDTO createUser(UserRequestDTO.UserCreateDTO userCreateDTO);
}
