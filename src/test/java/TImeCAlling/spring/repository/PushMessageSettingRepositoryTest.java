package TImeCAlling.spring.repository;

import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.domain.PushMessageSetting;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.domain.enums.FreeTime;
import TImeCAlling.spring.domain.enums.SocialType;
import TImeCAlling.spring.repository.pushMessageSetting.PushMessageSettingRepository;
import TImeCAlling.spring.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class PushMessageSettingRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PushMessageSettingRepository pushMessageSettingRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback
    @DisplayName("유저가 존재하고 푸시 메시지 설정이 있는 경우")
    void findByUserIdOrThrow_WhenUserExistsAndSettingsExist() {
        // given
        User user = User.builder()
                .nickname("예시유저")
                .socialType(SocialType.KAKAO)
                .avgPrepTime(30)
                .freeTime(FreeTime.RELAXED)
                .fcmToken("exampleFCMToken")
                .build();

        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        PushMessageSetting pushMessageSetting1 = PushMessageSetting.builder()
                .offset(30)
                .body("example body")
                .music("example music")
                .musicUrl("url")
                .isActive(true)
                .user(user)
                .build();

        PushMessageSetting pushMessageSetting2 = PushMessageSetting.builder()
                .offset(20)
                .body("example body2")
                .music("example music")
                .musicUrl("url2")
                .isActive(false)
                .user(user)
                .build();

        pushMessageSettingRepository.save(pushMessageSetting1);
        pushMessageSettingRepository.save(pushMessageSetting2);
        entityManager.flush();
        entityManager.clear();

        // when
        List<PushMessageSetting> result = pushMessageSettingRepository.findByUserIdOrThrow(user.getId());

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @Rollback
    @DisplayName("유저가 존재하지만 푸시 메시지 설정이 없는 경우")
    void findByUserIdOrThrow_WhenUserExistsButNoSettings() {
        // given
        User user = User.builder()
                .nickname("예시유저")
                .socialType(SocialType.GOOGLE)
                .avgPrepTime(15)
                .freeTime(FreeTime.RELAXED)
                .fcmToken("noSettingsFCMToken")
                .build();

        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // when
        List<PushMessageSetting> result = pushMessageSettingRepository.findByUserIdOrThrow(user.getId());

        // then
        assertThat(result).isEmpty(); // 푸시 메시지 설정이 없으므로 결과는 빈 리스트
    }


    @Test
    @Rollback
    @DisplayName("유저가 존재하지 않는 경우")
    void findByUserIdOrThrow_WhenUserDoesNotExist() {
        // given
        Long nonExistentUserId = 9999L; // 존재하지 않는 유저 ID

        // when & then
        assertThrows(UserHandler.class, () -> {
            pushMessageSettingRepository.findByUserIdOrThrow(nonExistentUserId);
        }); // 존재하지 않는 유저에 대해 UserHandler 예외 발생
    }

}
