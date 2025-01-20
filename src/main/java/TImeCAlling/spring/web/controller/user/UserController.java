package TImeCAlling.spring.web.controller.user;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.auth.JwtUtil;
import TImeCAlling.spring.converter.user.UserConverter;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.user.UserCommandService;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    
    private final UserCommandService userCommandService;
    private final JwtUtil jwtUtil;
    
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
    
    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDTO.UserMyPageDTO> getUserMyPage(@PathVariable Long userId) {
        
        return ApiResponse.onSuccess(userCommandService.findMyUsers(userId));
    }

    @PostMapping("/kakao/signup")
    @Operation(summary = "카카오 로그인", description = "accessToken을 입력하세요.")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> kakaoLogin (@RequestParam String kakaoAccessToken) {

        User user = userCommandService.kakaoLogin(kakaoAccessToken);
        String accessToken = jwtUtil.createAccessToken(user.getId());
        return ApiResponse.onSuccess(UserConverter.toUserSignUpResultDTO(user, accessToken));
    }
    @GetMapping("/kakao/token")
    @Operation(summary = "(테스트용) kakao accessToken 받기")
    public ApiResponse<String> getAccessToken(String code) {
        String token = userCommandService.getAccessToken(code);
        return ApiResponse.onSuccess(token);
    }
    
}
