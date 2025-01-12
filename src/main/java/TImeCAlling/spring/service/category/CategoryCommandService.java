package TImeCAlling.spring.service.category;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.category.CategoryRequestDTO;
import TImeCAlling.spring.web.dto.category.CategoryResponseDTO;

public interface CategoryCommandService {
    CategoryResponseDTO.CommandResultDto create(User user, CategoryRequestDTO.CreateOrUpdateDto request);

    CategoryResponseDTO.CommandResultDto delete(Long id);

    CategoryResponseDTO.CommandResultDto update(Long id, CategoryRequestDTO.CreateOrUpdateDto request);

}
