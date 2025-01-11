package TImeCAlling.spring.service.user;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
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
public class UserCommandServiceImpl implements UserCommandService {
    
    
    private final UserRepository userRepository;
    
    @Override
    public UserResponseDTO.UserCreateDTO createUser(UserRequestDTO.UserCreateDTO userCreateDTO) {
        
        User newUser = UserConverter.toUser(userCreateDTO);
        User saveduser = userRepository.save(newUser);
        
        return UserResponseDTO.UserCreateDTO.builder()
                .id(saveduser.getId())
                .build();
    }
    
    @Override
    public Long deleteUser(Long id) {
        User finduser = userRepository.findById(id).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        
        userRepository.delete(finduser);
        
        return finduser.getId();
    }
}
