package TImeCAlling.spring.converter.category;

import TImeCAlling.spring.domain.Category;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.category.CategoryRequestDTO;
import TImeCAlling.spring.web.dto.category.CategoryResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {
    public static Category toCategory(User user, CategoryRequestDTO.CreateOrUpdateDto request) {
        return Category.builder().user(user).type(request.getType()).build();
    }

    public static CategoryResponseDTO.CommandResultDto toCommandResultDto(Long id) {
        return CategoryResponseDTO.CommandResultDto.builder().categoryId(id).build();
    }

    public static List<CategoryResponseDTO.QueryResultDto> toQueryResultDtoAll(List<Category> categories) {
        List<CategoryResponseDTO.QueryResultDto> result = categories.stream().map(c -> CategoryResponseDTO.QueryResultDto.builder().categoryId(c.getId()).type(c.getType()).build()).collect(Collectors.toList());
        return result;
    }

    public static CategoryResponseDTO.QueryResultDto toQueryResultDtoOne(Category category) {
        return CategoryResponseDTO.QueryResultDto.builder().categoryId(category.getId()).type(category.getType()).build();
    }
}
