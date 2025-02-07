package TImeCAlling.spring.service.user;

import TImeCAlling.spring.apiPayload.exception.handler.S3Handler;
import TImeCAlling.spring.apiPayload.exception.handler.TokenHandler;
import TImeCAlling.spring.auth.JwtUtil;
import TImeCAlling.spring.converter.user.ProfileImageConverter;
import TImeCAlling.spring.domain.ProfileImage;
import TImeCAlling.spring.repository.user.ProfileImageRepository;
import TImeCAlling.spring.service.s3.S3Service;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    
    
    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
    private final S3Service s3Service;
    private final JwtUtil jwtUtil;
    private final Gson gson;
    
    @Override
    public UserResponseDTO.UserSignUpResultDTO createUser(MultipartFile profileImage, UserRequestDTO.UserCreateDTO userCreateDTO) {

        User newUser = UserConverter.toUser(userCreateDTO);
        User savedUser = userRepository.save(newUser);

        String accessToken = jwtUtil.createAccessToken(savedUser.getId());
        String refreshToken = jwtUtil.createRefreshToken(savedUser.getId());
        savedUser.setRefreshToken(refreshToken);
        userRepository.save(savedUser);

        String imageUrl = s3Service.uploadFile(profileImage);
        String fileName = getFileName(imageUrl);
        ProfileImage savedProfileImage = ProfileImageConverter.toProfileImage(savedUser, imageUrl, fileName);
        profileImageRepository.save(savedProfileImage);

        return UserConverter.toUserSignUpResultDTO(savedUser, accessToken, refreshToken);
    }
    
    @Override
    public UserResponseDTO.UserIdDTO deleteUser(Long id) {
        
        User findUser = getFindUser(id);

        String imageUrl = findUser.getProfileImage().getFileUrl();
        s3Service.deleteImageFromS3(imageUrl);

        userRepository.delete(findUser);
        
        return UserResponseDTO.UserIdDTO.builder()
                .userId(findUser.getId())
                .build();
    }
    
    @Override
    public UserResponseDTO.UserUpdateDTO updateUser(Long id, MultipartFile profileImage, UserRequestDTO.UserUpdateDTO updateDTO) {
        
        User findUser = getFindUser(id);

        FreeTime freeTime = updateDTO.getFreeTime() != null ? FreeTime.valueOf(updateDTO.getFreeTime()) : null;

        if (profileImage != null) {
            ProfileImage image = findUser.getProfileImage();
            s3Service.deleteImageFromS3(image.getFileUrl());

            String newImageUrl = s3Service.uploadFile(profileImage);
            String fileName = getFileName(newImageUrl);
            image.update(newImageUrl, fileName);
        }

        findUser.update(updateDTO.getNickname(), updateDTO.getAvgPrepTime(), freeTime);

        return UserConverter.toUserUpdateDTO(userRepository.save(findUser));
    }
    
    @Override
    public UserResponseDTO.UserMyPageDTO findMyUsers(Long id) {
        
        User findUser = getFindUser(id);

        return UserConverter.toUserMyPageDTO(findUser);
    }
    
    private User getFindUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public UserResponseDTO.UserSignUpResultDTO kakaoSignUp(MultipartFile profileImage, UserRequestDTO.UserSignUpDTO request) {

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

        String imageUrl = s3Service.uploadFile(profileImage);
        String fileName = getFileName(imageUrl);
        ProfileImage savedProfileImage = ProfileImageConverter.toProfileImage(savedUser, imageUrl, fileName);
        profileImageRepository.save(savedProfileImage);

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
            throw new TokenHandler(ErrorStatus.INVALID_KAKAO_TOKEN);
        }

        return gson.fromJson(result.toString(), UserAuthDTO.KaKaoUserInfoDTO.class);
    }

    private String getFileName(String imageUrl) {

        String fileName;

        try {
            URL url = new URL(imageUrl);
            String path = url.getPath();
            fileName = path.substring(path.lastIndexOf("/") + 1);

        } catch (MalformedURLException e) {
            throw new S3Handler(ErrorStatus.INVALID_URL);
        }

        return fileName;
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
    public UserResponseDTO.RefreshTokenResultDTO refreshToken(UserRequestDTO.RefreshTokenDTO request) {

        String accessToken = request.getAccessToken();
        String refreshToken = request.getRefreshToken();

        // accessToken: 만료, refreshToken: 유효 인지 확인
        if (!jwtUtil.isExpired(accessToken))
            throw new TokenHandler(ErrorStatus.ACCESS_TOKEN_NOT_EXPIRED);
        if (jwtUtil.isExpired(refreshToken))
            throw new TokenHandler(ErrorStatus.REFRESH_TOKEN_EXPIRED);

        // DB의 리프레시 토큰과 일치하는지 확인
        Long userId = jwtUtil.getUserId(refreshToken);
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        if (findUser.getRefreshToken() == null)
            throw new UserHandler(ErrorStatus.LOGGED_OUT_USER);
        if (!Objects.equals(findUser.getRefreshToken(), refreshToken))
            throw new TokenHandler(ErrorStatus.REFRESH_TOKEN_MISMATCH);

        // accessToken 재발급
        String newAccessToken = jwtUtil.createAccessToken(findUser.getId());

        return UserConverter.toRefreshTokenResultDTO(findUser, newAccessToken);
    }

    @Override
    public UserResponseDTO.UserSignUpResultDTO createToken(Long userId) {

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);
        findUser.setRefreshToken(refreshToken);
        userRepository.save(findUser);

        return UserConverter.toUserSignUpResultDTO(findUser, accessToken, refreshToken);
    }

    @Override
    public User logout(User user) {

        // DB에 저장된 refreshToken 삭제
        user.setRefreshToken(null);

        return userRepository.save(user);
    }
}
