package TImeCAlling.spring.repository.alarmList;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.domain.AlarmList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class AlarmListRepositoryCustomImpl implements AlarmListRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AlarmList> findByUserIdOrThrow(Long userId) {

//        유저의 존재 여부 확인
        Long userCount = entityManager
                .createQuery("SELECT COUNT(u) FROM User u WHERE u.id = :userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();

        if (userCount == 0) {
            throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        }

        return entityManager
                .createQuery("SELECT p FROM AlarmList p JOIN p.user u WHERE u.id = :userId", AlarmList.class)
                .setParameter("userId", userId)
                .getResultList();

//        bugfix: 유저가 있음에도 푸시 메세지 세팅이 없는 경우 -> 정상 (있을 수 있다! 그냥 빈 리스트 출력)
//        if (settings.isEmpty()) {
//            throw new PushMessageSettingHandler(ErrorStatus.PUSH_SETTING_NOT_FOUND);
//        }
    }
}
