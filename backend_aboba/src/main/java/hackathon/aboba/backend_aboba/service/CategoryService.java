package hackathon.aboba.backend_aboba.service;

import java.util.List;

import hackathon.aboba.backend_aboba.model.Category;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public List<Category> getAllCategoriesByUser(User user) {
        throw new UnsupportedOperationException();
    }

    public Category createCategory(User user, Category category) {
        user.getCategories().add(category);
        category.setUser(user);
        Category newCategory = categoryRepository.save(category);
        userService.createOrUpdateUser(user);
        return newCategory;
    }
}