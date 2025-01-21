package TImeCAlling.spring.service.pushNotification;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.user.UserRepository;
import TImeCAlling.spring.service.pushMessageNotification.PushNotificationCommandServiceImpl;
import TImeCAlling.spring.web.dto.fcm.FcmTokenResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PushNotificationCommandServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PushNotificationCommandServiceImpl pushNotificationCommandService;

    @Test
    @DisplayName("fcm 토큰 업데이트 정상작동")
    void updateFcmTokenSuccess() {
        // Given
        Long userId = 1L;
        String oldFcmToken = "oldFcmToken";
        String newFcmToken = "newFcmToken";

        User user = User.builder()
                .id(userId)
                .fcmToken(oldFcmToken)
                .build();


        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        FcmTokenResponseDTO.UpdateDTO response =
                pushNotificationCommandService.updateFcmToken(user, newFcmToken);

        // Then
        //FCM 토큰이 정상적으로 반영되었는지 검증
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(newFcmToken, user.getFcmToken());

        //메서드 호출 횟수 검증
        verify(userRepository, times(1)).save(user);
    }
}
