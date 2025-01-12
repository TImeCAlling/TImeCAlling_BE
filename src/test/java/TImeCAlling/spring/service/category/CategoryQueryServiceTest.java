package TImeCAlling.spring.service.category;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.web.dto.category.CategoryResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryQueryServiceTest {
    @Autowired
    private CategoryQueryService categoryQueryService;
    @Autowired
    private UserQueryService userQueryService;

    @Test
    @DisplayName("카테고리 조회")
    void 카테고리_조회() {
        // given
        User user = userQueryService.findOne(2L);

        // when
        List<CategoryResponseDTO.QueryResultDto> result = categoryQueryService.findAll(user);

        // then
        assertThat(result.size()).isEqualTo(2);
    }
}