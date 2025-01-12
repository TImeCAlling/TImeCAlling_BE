package TImeCAlling.spring.service.user;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.converter.user.UserConverter;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.repository.user.UserRepository;
import TImeCAlling.spring.service.category.CategoryCommandService;
import TImeCAlling.spring.web.dto.category.CategoryRequestDTO;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    
    
    private final UserRepository userRepository;
    private final CategoryCommandService categoryCommandService;
    
    @Override
    public UserResponseDTO.UserCreateDTO createUser(UserRequestDTO.UserCreateDTO userCreateDTO) {
        
        User newUser = UserConverter.toUser(userCreateDTO);
        User saveduser = userRepository.save(newUser);
        createDefaultCategory(saveduser); // 기본 카테고리 4가지 생성 메서드
        return UserResponseDTO.UserCreateDTO.builder()
                .id(saveduser.getId())
                .build();
    }

    private void createDefaultCategory(User user) {
        List<String> defaultCategories = List.of("일상", "공부", "중요", "알바");

        defaultCategories.forEach(type -> {
            CategoryRequestDTO.CreateOrUpdateDto request = new CategoryRequestDTO.CreateOrUpdateDto();
            request.setType(type);
            categoryCommandService.create(user, request);
        });
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
}
