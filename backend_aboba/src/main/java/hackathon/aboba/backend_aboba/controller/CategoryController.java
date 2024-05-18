package hackathon.aboba.backend_aboba.controller;

import hackathon.aboba.backend_aboba.dto.CategoryDto;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
}
