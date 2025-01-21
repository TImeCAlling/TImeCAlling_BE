package TImeCAlling.spring.service.pushMessageNotification;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.user.UserRepository;
import TImeCAlling.spring.web.dto.fcm.FcmTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PushNotificationCommandServiceImpl implements PushNotificationCommandService {

    private final UserRepository userRepository;

    @Override
    public FcmTokenResponseDTO.UpdateDTO updateFcmToken(User user, String fcmToken) {

        user.updateFcmToken(fcmToken);
        userRepository.save(user);

        return FcmTokenResponseDTO.UpdateDTO.builder()
                .userId(user.getId())
                .build();
    }
}
