package TImeCAlling.spring.service.user;

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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    
    
    private final UserRepository userRepository;
    private final Gson gson;
    
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
    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {

        return userRepository.findById(id).orElseThrow(
                () -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public User kakaoLogin(String kakaoAccessToken) {

        UserAuthDTO.KaKaoUserInfoDTO userInfo = getUserInfo(kakaoAccessToken);

        Long socialId = userInfo.getId();
        Optional<User> findUser = userRepository.findBySocialId(socialId);

        User newUser = findUser.orElseGet(
                () -> UserConverter.toUser(userInfo) // DB에 없는 유저면 회원가입
        );

        return userRepository.save(newUser);
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
            throw new UserHandler(ErrorStatus.NOT_VALID_TOKEN);
        }

        return gson.fromJson(result.toString(), UserAuthDTO.KaKaoUserInfoDTO.class);
    }

}
