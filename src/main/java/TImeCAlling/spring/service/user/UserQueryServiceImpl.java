package TImeCAlling.spring.service.user;

import TImeCAlling.spring.converter.user.UserConverter;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.user.UserRepository;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserResponseDTO.UserCreateDTO createUser(UserRequestDTO.UserCreateDTO userCreateDTO) {
        
        User newUser = UserConverter.toUser(userCreateDTO);
        User saveduser = userRepository.save(newUser);
        
        return UserResponseDTO.UserCreateDTO.builder()
                .id(saveduser.getId())
                .build();
    }
}
