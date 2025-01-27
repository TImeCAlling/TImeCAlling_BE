package TImeCAlling.spring.repository.alarmList;

import TImeCAlling.spring.domain.AlarmList;

import java.util.List;

public interface AlarmListRepositoryCustom {

    List<AlarmList> findByUserIdOrThrow(Long userId);
}
