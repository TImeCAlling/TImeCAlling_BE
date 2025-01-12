package TImeCAlling.spring.service.category;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.CategoryHandler;
import TImeCAlling.spring.converter.category.CategoryConverter;
import TImeCAlling.spring.domain.Category;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.category.CategoryRepository;
import TImeCAlling.spring.web.dto.category.CategoryRequestDTO;
import TImeCAlling.spring.web.dto.category.CategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponseDTO.CommandResultDto create(User user, CategoryRequestDTO.CreateOrUpdateDto request) {
        Category category = CategoryConverter.toCategory(user, request);
        if (categoryRepository.findByType(category.getType()) != null) {
            throw new CategoryHandler(ErrorStatus.CATEGORY_ALREADY_EXIST);
        }
        Long id = categoryRepository.save(category).getId();
        return CategoryConverter.toCommandResultDto(id);
    }

    @Override
    @Transactional
    public CategoryResponseDTO.CommandResultDto delete(Long id) {
        categoryRepository.deleteById(id);
        return CategoryConverter.toCommandResultDto(id);
    }

    @Override
    @Transactional
    public CategoryResponseDTO.CommandResultDto update(Long id, CategoryRequestDTO.CreateOrUpdateDto request) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.changeType(request.getType());
        return CategoryConverter.toCommandResultDto(category.getId());
    }
}
