package TImeCAlling.spring.repository.pushMessageSetting;

import TImeCAlling.spring.domain.PushMessageSetting;

import java.util.List;

public interface PushMessageSettingRepositoryCustom {

    List<PushMessageSetting> findByUserIdOrThrow(Long userId);
}
