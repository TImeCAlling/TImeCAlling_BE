package TImeCAlling.spring.service.user;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.converter.user.UserConverter;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.FreeTime;
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
    public UserResponseDTO.UserDeleteDTO deleteUser(Long id) {
        
        User finduser = getFinduser(id);
        userRepository.delete(finduser);
        
        return UserResponseDTO.UserDeleteDTO.builder()
                .id(finduser.getId())
                .build();
    }
    
    @Override
    public UserResponseDTO.UserUpdateDTO updateUser(Long id, UserRequestDTO.UserUpdateDTO updateDTO) {
        
        User finduser = getFinduser(id);
        finduser.update(updateDTO.getNickname(), updateDTO.getAvgPrepTime(), FreeTime.valueOf(updateDTO.getFreeTime()));
        User saveduser = userRepository.save(finduser);
        
        return UserResponseDTO.UserUpdateDTO.builder()
                .id(saveduser.getId())
                .build();
    }
    
    @Override
    public UserResponseDTO.UserMyPageDTO findMyUsers(Long id) {
        
        User finduser = getFinduser(id);
        
        return UserResponseDTO.UserMyPageDTO.builder()
                .nickname(finduser.getNickname())
                .avgPrepTime(finduser.getAvgPrepTime())
                .freeTime(finduser.getFreeTime().toString())
                .build();
    }
    
    private User getFinduser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public User signUp(String kakaoAccessToken) {
        return null;
    }

}
