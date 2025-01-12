package TImeCAlling.spring.service.category;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.web.dto.category.CategoryResponseDTO;

import java.util.List;

public interface CategoryQueryService {
    List<CategoryResponseDTO.QueryResultDto> findAll(User user);

    CategoryResponseDTO.QueryResultDto findOne(Long id);

    Boolean isExist(Long id);
}
