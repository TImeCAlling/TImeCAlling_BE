package TImeCAlling.spring.service.user;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;

public interface UserCommandService {
    
    UserResponseDTO.UserCreateDTO createUser(UserRequestDTO.UserCreateDTO userCreateDTO);
    
    UserResponseDTO.UserDeleteDTO deleteUser(Long id);
    
    UserResponseDTO.UserUpdateDTO updateUser(Long id, UserRequestDTO.UserUpdateDTO updateDTO);
    
    UserResponseDTO.UserMyPageDTO findMyUsers(Long id);

    User signUp(String kakaoAccessToken);
    
}
