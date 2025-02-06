package TImeCAlling.spring.service.user;

import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserCommandService {
    
    UserResponseDTO.UserSignUpResultDTO createUser(MultipartFile profileImage, UserRequestDTO.UserCreateDTO userCreateDTO);
    UserResponseDTO.UserIdDTO deleteUser(Long id);
    UserResponseDTO.UserUpdateDTO updateUser(Long id, MultipartFile profileImage, UserRequestDTO.UserUpdateDTO updateDTO);
    UserResponseDTO.UserMyPageDTO findMyUsers(Long id);
    UserResponseDTO.UserSignUpResultDTO kakaoSignUp(MultipartFile profileImage, UserRequestDTO.UserSignUpDTO request);
    UserResponseDTO.UserSignUpResultDTO kakaoLogin(UserRequestDTO.UserLoginDTO request);
    UserResponseDTO.RefreshTokenResultDTO refreshToken(UserRequestDTO.RefreshTokenDTO request);
    UserResponseDTO.UserSignUpResultDTO createToken(Long userId);
    String getAccessToken(String code);
}
