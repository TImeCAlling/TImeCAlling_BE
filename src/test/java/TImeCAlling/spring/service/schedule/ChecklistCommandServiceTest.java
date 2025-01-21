package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.Checklist;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.schedule.checklist.ChecklistCommandService;
import TImeCAlling.spring.service.schedule.checklist.ChecklistQueryService;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.web.dto.checklist.ChecklistRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ChecklistCommandServiceTest {
    @Autowired
    private ChecklistCommandService checklistCommandService;
    @Autowired
    private ChecklistQueryService checklistQueryService;
    @Autowired
    private UserQueryService userQueryService;

    @Test
    @Transactional
    public void 체크리스트_값_추가() {
        /** id가 3번 일정 1/20일에 대한 체크리스트 값 추가해보기 **/
        // given
        ChecklistRequestDTO.UpdateDTO request = ChecklistRequestDTO.UpdateDTO.builder()
                .isSuccess(true)
                .isFit(true)
                .spare("10분 이상")
                .reason("교통 체증")
                .late("5분 ~ 10분")
                .external("기타")
                .date(LocalDate.of(2025, 1, 20))
                .build();
        // when
        Long resultId = checklistCommandService.updateChecklist(3L, 3L, request);

        // then
        User user = userQueryService.findOne(3L);
        assertThat(user.getSuccess()).isEqualTo(1);

        Checklist checklist = checklistQueryService.findOne(resultId);
        assertThat(checklist.getIsWritten()).isTrue();
    }

}