package hackathon.aboba.backend_aboba.controller;

import java.util.List;

import hackathon.aboba.backend_aboba.dto.CategoryDto;
import hackathon.aboba.backend_aboba.model.Category;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto createCategory(
            @AuthenticationPrincipal User user,
            @RequestBody CategoryDto category
    ) {
        return categoryService.createCategory(user, category.toCategory()).toCategoryDto();
    }

    @GetMapping("/all")
    public List<CategoryDto> getAllCategories(
            @AuthenticationPrincipal User user
    ) {
        return categoryService.getAllCategoriesByUser(user).stream().map(Category::toCategoryDto).toList();
    }

    @DeleteMapping
    public CategoryDto deleteCategory(
            @AuthenticationPrincipal User user,
            @RequestBody CategoryDto categoryDto
    ) {
        return categoryService.removeCategory(user, categoryDto.toCategory()).toCategoryDto();
    }
}
