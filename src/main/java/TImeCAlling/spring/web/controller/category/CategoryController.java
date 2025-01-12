package TImeCAlling.spring.web.controller.category;

import TImeCAlling.spring.apiPayload.ApiResponse;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.service.category.CategoryCommandService;
import TImeCAlling.spring.service.category.CategoryQueryService;
import TImeCAlling.spring.service.user.UserQueryService;
import TImeCAlling.spring.web.dto.category.CategoryRequestDTO;
import TImeCAlling.spring.web.dto.category.CategoryResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryQueryService categoryQueryService;
    private final CategoryCommandService categoryCommandService;
    private final UserQueryService userQueryService;

    @PostMapping("/{id}")
    public ApiResponse<CategoryResponseDTO.CommandResultDto> create(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO.CreateOrUpdateDto request) {
        User user = userQueryService.findOne(id);
        return ApiResponse.onSuccess(categoryCommandService.create(user, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<CategoryResponseDTO.CommandResultDto> delete(@PathVariable("id") Long id) {
        return ApiResponse.onSuccess(categoryCommandService.delete(id));
    }

    @GetMapping("/{id}")
    public ApiResponse<List<CategoryResponseDTO.QueryResultDto>> read(@PathVariable("id") Long id) {
        User user = userQueryService.findOne(id);
        return ApiResponse.onSuccess(categoryQueryService.findAll(user));
    }

    @PatchMapping("/{id}")
    public ApiResponse<CategoryResponseDTO.CommandResultDto> update(@PathVariable("id") Long id, @RequestBody CategoryRequestDTO.CreateOrUpdateDto request) {
        return ApiResponse.onSuccess(categoryCommandService.update(id, request));
    }
}
