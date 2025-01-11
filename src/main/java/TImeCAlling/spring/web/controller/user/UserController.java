package TImeCAlling.spring.web.controller.user;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.service.user.UserCommandService;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    
    private final UserCommandService userCommandService;
    
    @PostMapping
    public ApiResponse<UserResponseDTO.UserCreateDTO> createUser(@RequestBody UserRequestDTO.UserCreateDTO userCreateDTO) {
        
        UserResponseDTO.UserCreateDTO response = userCommandService.createUser(userCreateDTO);
        return ApiResponse.onSuccess(response);
    }
    
    @DeleteMapping("/{userId}")
    public ApiResponse<UserResponseDTO.UserDeleteDTO> deleteUser(@RequestParam Long userId) {
        
        return ApiResponse.onSuccess(userCommandService.deleteUser(userId));
    }
    
    @PutMapping("/{userId}")
    public ApiResponse<UserResponseDTO.UserUpdateDTO> updateUser(@PathVariable Long userId,
                                                                 @RequestBody UserRequestDTO.UserUpdateDTO userUpdateDTO) {
        
        return ApiResponse.onSuccess(userCommandService.updateUser(userId, userUpdateDTO));
    }
    
    
}
