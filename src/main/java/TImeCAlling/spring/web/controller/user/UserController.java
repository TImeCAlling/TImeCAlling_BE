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
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    
    private final UserCommandService userCommandService;
    private final JwtUtil jwtUtil;
    
    @PostMapping
    @Operation(summary = "(테스트용) 회원 생성", description = "회원 정보를 입력하여 새 회원을 생성합니다.")
    public ApiResponse<UserResponseDTO.UserCreateDTO> createUser(@RequestBody UserRequestDTO.UserCreateDTO userCreateDTO) {
        
        UserResponseDTO.UserCreateDTO response = userCommandService.createUser(userCreateDTO);
        return ApiResponse.onSuccess(response);
    }
    
    @DeleteMapping()
    @Operation(summary = "회원 탈퇴")
    public ApiResponse<UserResponseDTO.UserDeleteDTO> deleteUser(@AuthenticationPrincipal User user) {
        
        return ApiResponse.onSuccess(userCommandService.deleteUser(user.getId()));
    }
    
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "회원정보 수정", description = "수정할 회원의 정보를 입력합니다.")
    public ApiResponse<UserResponseDTO.UserUpdateDTO> updateUser(@AuthenticationPrincipal User user,
                                                                 @RequestPart(required = false) MultipartFile profileImage,
                                                                 @RequestPart UserRequestDTO.UserUpdateDTO userUpdateDTO) {
        
        return ApiResponse.onSuccess(userCommandService.updateUser(user.getId(), profileImage, userUpdateDTO));
    }
    
    @GetMapping()
    @Operation(summary = "회원 조회", description = "로그인한 회원의 정보를 조회합니다.")
    public ApiResponse<UserResponseDTO.UserMyPageDTO> getUserMyPage(@AuthenticationPrincipal User user) {
        
        return ApiResponse.onSuccess(userCommandService.findMyUsers(user.getId()));
    }

    @PostMapping(value = "/kakao/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "카카오 회원가입", description = "카카오 엑세스 토큰과 가입할 회원의 정보를 입력하세요.")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> kakaoSignUp (@RequestPart MultipartFile profileImage,
                                                                         @RequestPart @Valid UserRequestDTO.UserSignUpDTO request) {
        UserResponseDTO.UserSignUpResultDTO response = userCommandService.kakaoSignUp(profileImage, request);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/kakao/login")
    @Operation(summary = "카카오 로그인", description = "카카오 액세스 토큰을 입력하세요.")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> kakaoLogin (@RequestBody @Valid UserRequestDTO.UserLoginDTO request) {

        UserResponseDTO.UserSignUpResultDTO response = userCommandService.kakaoLogin(request);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/token/refresh")
    @Operation(summary = "액세스 토큰 재발급", description = "만료된 accessToken과 해당 회원의 refreshToken을 입력하세요.")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> refreshToken(@RequestBody UserRequestDTO.refreshTokenDTO request) {
        UserResponseDTO.UserSignUpResultDTO response = userCommandService.refreshToken(request);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/test/kakao")
    @Operation(summary = "(테스트용) kakao accessToken 받기",
            description = "https://kauth.kakao.com/oauth/authorize?client_id=594ea4c05c1c31d5b7d8071cec4b8373&redirect_uri=http://localhost:8080/oauth&response_type=code <br><br> 주소 접속 후 리다이렉트된 url의 code를 입력하세요")
    public ApiResponse<String> getAccessToken(String code) {
        String token = userCommandService.getAccessToken(code);
        return ApiResponse.onSuccess(token);
    }

    @PostMapping("/test/jwt")
    @Operation(summary = "(테스트용) jwt 토큰 받기", description = "유저 id로 해당 유저의 토큰을 발급합니다.")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> createJWT(@RequestParam Long userId) {
        User user = (User) userCommandService.loadUserByUserId(userId);
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = user.getRefreshToken();
        return ApiResponse.onSuccess(UserConverter.toUserSignUpResultDTO(user, accessToken, refreshToken));
    }
    
}
