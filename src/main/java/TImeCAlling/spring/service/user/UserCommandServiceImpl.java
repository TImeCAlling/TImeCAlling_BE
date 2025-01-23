package TImeCAlling.spring.service.user;

import TImeCAlling.spring.auth.JwtUtil;
import TImeCAlling.spring.converter.user.ProfileImageConverter;
import TImeCAlling.spring.domain.ProfileImage;
import TImeCAlling.spring.repository.user.ProfileImageRepository;
import TImeCAlling.spring.web.dto.user.UserAuthDTO;
import com.google.gson.Gson;
import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.converter.user.UserConverter;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.repository.user.UserRepository;
import TImeCAlling.spring.web.dto.user.UserRequestDTO;
import TImeCAlling.spring.web.dto.user.UserResponseDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    
    
    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
    private final JwtUtil jwtUtil;
    private final Gson gson;
    
    @Override
    public UserResponseDTO.UserCreateDTO createUser(UserRequestDTO.UserCreateDTO userCreateDTO) {
        
        User newUser = UserConverter.toUser(userCreateDTO);
        User saveduser = userRepository.save(newUser);
        
        return UserResponseDTO.UserCreateDTO.builder()
                .userId(saveduser.getId())
                .build();
    }
    
    @Override
    public UserResponseDTO.UserDeleteDTO deleteUser(Long id) {
        
        User finduser = getFinduser(id);
        userRepository.delete(finduser);
        
        return UserResponseDTO.UserDeleteDTO.builder()
                .userId(finduser.getId())
                .build();
    }
    
    @Override
    public UserResponseDTO.UserUpdateDTO updateUser(Long id, UserRequestDTO.UserUpdateDTO updateDTO) {
        
        User finduser = getFinduser(id);
        finduser.update(updateDTO.getNickname(), updateDTO.getAvgPrepTime(), FreeTime.valueOf(updateDTO.getFreeTime()));
        User saveduser = userRepository.save(finduser);
        
        return UserResponseDTO.UserUpdateDTO.builder()
                .userId(saveduser.getId())
                .build();
    }
    
    @Override
    public UserResponseDTO.UserMyPageDTO findMyUsers(Long id) {
        
        User finduser = getFinduser(id);
        
        return UserResponseDTO.UserMyPageDTO.builder()
                .userId(finduser.getId())
                .nickname(finduser.getNickname())
                .avgPrepTime(finduser.getAvgPrepTime())
                .freeTime(String.valueOf(finduser.getFreeTime()))
                .build();
    }
    
    private User getFinduser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUserId(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public UserResponseDTO.UserSignUpResultDTO kakaoSignUp(UserRequestDTO.UserSignUpDTO request) {

        UserAuthDTO.KaKaoUserInfoDTO userInfo = getUserInfo(request.getKakaoAccessToken());

        Long socialId = userInfo.getId();
        Optional<User> findUser = userRepository.findBySocialId(socialId);

        if (findUser.isPresent()) {
            throw new UserHandler(ErrorStatus.USER_ALREADY_EXIST);
        }

        User newUser = UserConverter.toUser(userInfo, request);
        User savedUser = userRepository.save(newUser);

        String accessToken = jwtUtil.createAccessToken(savedUser.getId());
        String refreshToken = jwtUtil.createRefreshToken(savedUser.getId());
        savedUser.setRefreshToken(refreshToken);
        userRepository.save(savedUser);

        ProfileImage profileImage = ProfileImageConverter.toProfileImage(savedUser, request.getProfileUrl());
        profileImageRepository.save(profileImage);

        return UserConverter.toUserSignUpResultDTO(savedUser, accessToken, refreshToken);
    }

    @Override
    public UserResponseDTO.UserSignUpResultDTO kakaoLogin(UserRequestDTO.UserLoginDTO request) {

        UserAuthDTO.KaKaoUserInfoDTO userInfo = getUserInfo(request.getKakaoAccessToken());

        Long socialId = userInfo.getId();
        User findUser = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        String accessToken = jwtUtil.createAccessToken(findUser.getId());
        String refreshToken = jwtUtil.createRefreshToken(findUser.getId());
        findUser.setRefreshToken(refreshToken);
        userRepository.save(findUser);

        return UserConverter.toUserSignUpResultDTO(findUser, accessToken, refreshToken);
    }

    private UserAuthDTO.KaKaoUserInfoDTO getUserInfo(String accessToken) {

        String getURL = "https://kapi.kakao.com/v2/user/me";
        StringBuilder result;

        try {
            URL url = new URL(getURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();  // 응답 코드
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

        } catch (IOException exception) {
            throw new UserHandler(ErrorStatus.NOT_VALID_KAKAO_TOKEN);
        }

        return gson.fromJson(result.toString(), UserAuthDTO.KaKaoUserInfoDTO.class);
    }

    @Override
    public String getAccessToken(String code) {

        String getURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=594ea4c05c1c31d5b7d8071cec4b8373&redirect_uri=http://localhost:8080/oauth&code="+code;
        String accessToken = "";

        try {
            URL url = new URL(getURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();  // 응답 코드
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            JsonObject jsonObject = JsonParser.parseString(result.toString()).getAsJsonObject();
            accessToken = jsonObject.get("access_token").getAsString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    @Override
    public UserResponseDTO.UserSignUpResultDTO refreshToken(UserRequestDTO.refreshTokenDTO request) {

        String accessToken = request.getAccessToken();
        String refreshToken = request.getRefreshToken();

        if (jwtUtil.validateToken(accessToken))
            throw new UserHandler(ErrorStatus.ACCESS_TOKEN_NOT_EXPIRED);
        jwtUtil.validateToken(refreshToken);

        Long userId = jwtUtil.getUserId(refreshToken);
        User findUser = userRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new UserHandler(ErrorStatus.NOT_VALID_TOKEN));

        if (!Objects.equals(findUser.getId(), userId))
            throw new UserHandler(ErrorStatus.NOT_VALID_TOKEN);

        String newAccessToken = jwtUtil.createAccessToken(findUser.getId());
        String newRefreshToken = jwtUtil.createRefreshToken(findUser.getId());
        findUser.setRefreshToken(newRefreshToken);
        userRepository.save(findUser);

        return UserConverter.toUserSignUpResultDTO(findUser, newAccessToken, newRefreshToken);
    }
}
