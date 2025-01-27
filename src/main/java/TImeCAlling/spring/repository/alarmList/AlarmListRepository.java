package TImeCAlling.spring.repository.alarmList;

import TImeCAlling.spring.domain.AlarmList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmListRepository extends JpaRepository<AlarmList, Long>, AlarmListRepositoryCustom {

}
