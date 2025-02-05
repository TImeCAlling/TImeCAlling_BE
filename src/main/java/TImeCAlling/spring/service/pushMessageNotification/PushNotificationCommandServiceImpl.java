package TImeCAlling.spring.service.pushMessageNotification;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.ScheduleHandler;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.MessageStatus;
import TImeCAlling.spring.repository.schedule.ScheduleRepository;
import TImeCAlling.spring.repository.user.UserRepository;
import TImeCAlling.spring.web.dto.fcm.FcmMessageDTO;
import TImeCAlling.spring.web.dto.fcm.FcmTokenResponseDTO;
import TImeCAlling.spring.web.dto.pushMessageNotification.PushNotificationRequestDTO;
import TImeCAlling.spring.web.dto.pushMessageNotification.PushNotificationResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PushNotificationCommandServiceImpl implements PushNotificationCommandService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Value("${fcm.firebase_config_path}")
    private String firebaseConfigPath;

    @Value("${fcm.api_url}")
    private String fcmApiUrl;

    @Override
    public FcmTokenResponseDTO.UpdateDTO updateFcmToken(User user, String fcmToken) {

        user.updateFcmToken(fcmToken);
        userRepository.save(user);

        return FcmTokenResponseDTO.UpdateDTO.builder()
                .userId(user.getId())
                .build();
    }

    @Override
    public PushNotificationResponseDTO.NotificationDetails notifyUser(User user,
                          PushNotificationRequestDTO.NotificationDetails notificationDTO) throws IOException {

        String receiverFcmToken = userRepository.findFcmTokenByUserId(notificationDTO.getReceiverId())
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        Schedule schedule = scheduleRepository.findByShareIdAndUser(notificationDTO.getShareId(), user)
                .orElseThrow(() -> new ScheduleHandler(ErrorStatus.SCHEDULE_NOT_FOUND));

        String message = makeMessage(receiverFcmToken, schedule, notificationDTO, user);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(message, headers);

        ResponseEntity<String> response = restTemplate.exchange(fcmApiUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return PushNotificationResponseDTO.NotificationDetails.builder()
                    .receiverId(notificationDTO.getReceiverId())
                    .shareId(notificationDTO.getShareId())
                    .status(MessageStatus.SENT)
                    .build();
        } else {
            return PushNotificationResponseDTO.NotificationDetails.builder()
                    .receiverId(notificationDTO.getReceiverId())
                    .shareId(notificationDTO.getShareId())
                    .status(MessageStatus.FAILED)
                    .build();
        }
    }

    /**
     * Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰을 발급 받습니다.
     *
     * @return Bearer token
     */
    private String getAccessToken() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        //토큰 생성 확인용
//        System.out.println(googleCredentials.getAccessToken().getTokenValue());
        return googleCredentials.getAccessToken().getTokenValue();
    }

    /**
     * FCM 전송 정보를 기반으로 메시지를 구성합니다. (Object -> String)
     *
     * @param receiverFcmToken 수신자 fcm 토큰
     * @param schedule 스케줄
     * @param notificationDTO PushNotificationRequestDTO.NotificationDetails
     * @param user 발신자 정보
     * @return String
     */
    private String makeMessage(String receiverFcmToken, Schedule schedule,
           PushNotificationRequestDTO.NotificationDetails notificationDTO, User user) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        String body = String.format("%s님이 %s라고 하셨어!", user.getNickname(), notificationDTO.getBody());

        FcmMessageDTO fcmMessageDTO = FcmMessageDTO.builder()
                .message(FcmMessageDTO.Message.builder()
                        .token(receiverFcmToken)
                        .notification(FcmMessageDTO.Notification.builder()
                                .title(schedule.getName())
                                .body(body)
                                .build()
                        ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessageDTO);
    }

}
