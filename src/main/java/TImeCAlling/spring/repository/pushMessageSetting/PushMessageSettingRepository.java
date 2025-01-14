package TImeCAlling.spring.repository.pushMessageSetting;

import TImeCAlling.spring.domain.PushMessageSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushMessageSettingRepository extends JpaRepository<PushMessageSetting, Long>, PushMessageSettingRepositoryCustom {

}
