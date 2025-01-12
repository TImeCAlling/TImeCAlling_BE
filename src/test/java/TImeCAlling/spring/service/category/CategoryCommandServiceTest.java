package TImeCAlling.spring.service.category;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.web.dto.category.CategoryRequestDTO;
import TImeCAlling.spring.web.dto.category.CategoryResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryCommandServiceTest {
    @Autowired
    private CategoryCommandService categoryCommandService;
    @Autowired
    private CategoryQueryService categoryQueryService;
    @Autowired
    private UserQueryService userQueryService;

    @Test
    @DisplayName("카테고리 생성")
    void 카테고리_생성() {
        // given
        CategoryRequestDTO.CreateOrUpdateDto request = new CategoryRequestDTO.CreateOrUpdateDto();
        request.setType("일상");

        User user = userQueryService.findOne(2L);

        // when
        CategoryResponseDTO.CommandResultDto result = categoryCommandService.create(user, request);

        // then
        CategoryResponseDTO.QueryResultDto target = categoryQueryService.findOne(result.getCategoryId());
        assertThat(target.getType()).isEqualTo(request.getType());
    }

    @Test
    @DisplayName("카테고리 수정")
    void 카테고리_수정() {
        // given
        CategoryRequestDTO.CreateOrUpdateDto request = new CategoryRequestDTO.CreateOrUpdateDto();
        request.setType("알바");
        Long categoryId = 4L;

        // when
        CategoryResponseDTO.CommandResultDto result = categoryCommandService.update(categoryId, request);

        // then
        CategoryResponseDTO.QueryResultDto target = categoryQueryService.findOne(categoryId);
        assertThat(target.getType()).isEqualTo(request.getType());
    }

    @Test
    @DisplayName("카테고리 삭제")
    void 카테고리_삭제() {
        // given
        Long deleteId = 4L;

        // when
        CategoryResponseDTO.CommandResultDto result = categoryCommandService.delete(deleteId);

        // then
        Boolean exist = categoryQueryService.isExist(result.getCategoryId());
        assertThat(exist).isFalse();
    }
}