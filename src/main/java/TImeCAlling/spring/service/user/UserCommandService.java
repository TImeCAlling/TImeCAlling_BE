package TImeCAlling.spring.service.user;

import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;

public interface UserCommandService {
    
    UserResponseDTO.UserCreateDTO createUser(UserRequestDTO.UserCreateDTO userCreateDTO);
    
    Long deleteUser(Long id);
}
