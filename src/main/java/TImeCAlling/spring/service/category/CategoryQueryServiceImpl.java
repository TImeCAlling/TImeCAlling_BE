package TImeCAlling.spring.service.category;

import TImeCAlling.spring.converter.category.CategoryConverter;
import TImeCAlling.spring.domain.Category;
import TImeCAlling.spring.domain.User;

import TImeCAlling.spring.repository.category.CategoryRepository;
import TImeCAlling.spring.web.dto.category.CategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDTO.QueryResultDto> findAll(User member) {
        List<Category> categories = categoryRepository.findAllByUserId(member.getId());
        return CategoryConverter.toQueryResultDtoAll(categories);
    }

    @Override
    public CategoryResponseDTO.QueryResultDto findOne(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return CategoryConverter.toQueryResultDtoOne(category);
    }

    @Override
    public Boolean isExist(Long id) {
        return categoryRepository.existsById(id);
    }
}
