package TImeCAlling.spring.repository.pushMessageSetting;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.domain.PushMessageSetting;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class PushMessageSettingRepositoryCustomImpl implements PushMessageSettingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PushMessageSetting> findByUserIdOrThrow(Long userId) {
        List<PushMessageSetting> settings = entityManager
                .createQuery("SELECT p FROM PushMessageSetting p JOIN p.user u WHERE u.id = :userId", PushMessageSetting.class)
                .setParameter("userId", userId)
                .getResultList();

        if (settings.isEmpty()) {
            throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        }

        return settings;
    }
}
