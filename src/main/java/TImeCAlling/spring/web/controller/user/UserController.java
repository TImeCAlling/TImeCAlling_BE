package TImeCAlling.spring.web.controller.user;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.auth.JwtUtil;
import TImeCAlling.spring.converter.user.UserConverter;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.user.UserCommandService;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    
    @DeleteMapping()
    public ApiResponse<UserResponseDTO.UserDeleteDTO> deleteUser(@AuthenticationPrincipal User user) {
        
        return ApiResponse.onSuccess(userCommandService.deleteUser(user.getId()));
    }
    
    @PutMapping()
    public ApiResponse<UserResponseDTO.UserUpdateDTO> updateUser(@AuthenticationPrincipal User user,
                                                                 @RequestBody UserRequestDTO.UserUpdateDTO userUpdateDTO) {
        
        return ApiResponse.onSuccess(userCommandService.updateUser(user.getId(), userUpdateDTO));
    }
    
    @GetMapping()
    public ApiResponse<UserResponseDTO.UserMyPageDTO> getUserMyPage(@AuthenticationPrincipal User user) {
        
        return ApiResponse.onSuccess(userCommandService.findMyUsers(user.getId()));
    }

    @PostMapping("/kakao/signup")
    @Operation(summary = "카카오 로그인", description = "accessToken을 입력하세요.")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> kakaoLogin (@RequestBody @Valid UserRequestDTO.UserSignUpDTO request) {

        User user = userCommandService.kakaoSignUp(request);
        String accessToken = jwtUtil.createAccessToken(user.getId());
        return ApiResponse.onSuccess(UserConverter.toUserSignUpResultDTO(user, accessToken));
    }

    @GetMapping("/kakao/token")
    @Operation(summary = "(개발용) kakao accessToken 받기", description = "https://kauth.kakao.com/oauth/authorize?client_id=594ea4c05c1c31d5b7d8071cec4b8373&redirect_uri=http://localhost:8080/oauth&response_type=code")
    public ApiResponse<String> getAccessToken(String code) {
        String token = userCommandService.getAccessToken(code);
        return ApiResponse.onSuccess(token);
    }

    @PostMapping("/token")
    @Operation(summary = "(개발용) jwt 토큰 받기")
    public ApiResponse<String> createJWT(@RequestParam Long userId) {
        userCommandService.loadUserByUserId(userId);
        String token = jwtUtil.createAccessToken(userId);
        return ApiResponse.onSuccess(token);
    }
    
}
