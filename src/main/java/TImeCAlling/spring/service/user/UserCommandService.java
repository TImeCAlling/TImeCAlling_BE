package TImeCAlling.spring.service.user;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface UserCommandService {
    
    UserResponseDTO.UserCreateDTO createUser(UserRequestDTO.UserCreateDTO userCreateDTO);
    
    UserResponseDTO.UserDeleteDTO deleteUser(Long id);
    
    UserResponseDTO.UserUpdateDTO updateUser(Long id, MultipartFile profileImage, UserRequestDTO.UserUpdateDTO updateDTO);
    
    UserResponseDTO.UserMyPageDTO findMyUsers(Long id);
    UserDetails loadUserByUserId(Long id);

    UserResponseDTO.UserSignUpResultDTO kakaoSignUp(MultipartFile profileImage, UserRequestDTO.UserSignUpDTO request);
    UserResponseDTO.UserSignUpResultDTO kakaoLogin(UserRequestDTO.UserLoginDTO request);
    UserResponseDTO.UserSignUpResultDTO refreshToken(UserRequestDTO.refreshTokenDTO request);
    String getAccessToken(String code);
}
