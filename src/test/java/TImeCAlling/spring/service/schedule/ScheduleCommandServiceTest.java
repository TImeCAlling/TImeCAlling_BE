package TImeCAlling.spring.service.schedule;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.web.dto.schedule.ScheduleRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScheduleCommandServiceTest {
    @Autowired
    private ScheduleCommandService scheduleCommandService;
    @Autowired
    private ScheduleQueryService scheduleQueryService;
    @Autowired
    private UserQueryService userQueryService;

    @Test
    @Transactional
    public void 카테고리_수정() {
        /** id가 9번인 일정 수정해보기 **/
        // given
        User user = userQueryService.findOne(1L);

        List<String> repeatDays = new ArrayList<>();
        repeatDays.add("MONDAY");
        ScheduleRequestDTO.CategoryDTO category1 = new ScheduleRequestDTO.CategoryDTO("알바", 0);
        ScheduleRequestDTO.CategoryDTO category2 = new ScheduleRequestDTO.CategoryDTO("수업", 9);
        List<ScheduleRequestDTO.CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryDTOS.add(category1);
        categoryDTOS.add(category2);
        ScheduleRequestDTO.SchedulePatchDTO request = new ScheduleRequestDTO.SchedulePatchDTO(
                "테스트", "테스트용 입니다 ~", LocalDateTime.of(2025,1,19,5,35,39,698), "test", "111.111", "111.111", 30,
                "TIGHT", true, repeatDays, LocalDate.of(2025,1,19), LocalDate.of(2025,1,19), categoryDTOS);

        // when
        scheduleCommandService.patchSchedule(9L, user, request);
        // then
        /** 수정 시 기존에 존재하던 카테고리들은 전부 삭제되고, 다시 새로운 카테고리들로 세팅되는지 체크 **/
        assertThat(scheduleQueryService.getSchedule(9L, user).getCategories().size()).isEqualTo(2);

    }
}