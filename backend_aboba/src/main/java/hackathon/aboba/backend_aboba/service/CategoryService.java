package hackathon.aboba.backend_aboba.service;

import java.util.List;

import hackathon.aboba.backend_aboba.exception.ServerExceptions;
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
        return categoryRepository.findByUser_Id(user.getId());
    }

    public Category createCategory(User user, Category category) {
        user.getCategories().add(category);
        category.setUser(user);
        Category newCategory = categoryRepository.save(category);
        userService.createOrUpdateUser(user);
        return newCategory;
    }

    public Category removeCategory(User user, Category category) {
        Category categoryToRemove = categoryRepository.findByTitleAndUser_Id(category.getTitle(), user.getId());
        if (categoryToRemove != null) {
            categoryRepository.delete(categoryToRemove);
        }
        return categoryToRemove;
    }

    public Category findCategoryOrThrow(Category category, User user) {
        Category foundCategory = categoryRepository.findByTitleAndUser_Id(category.getTitle(), user.getId());
        if (foundCategory == null) {
            ServerExceptions.NOT_FOUND_EXCEPTION.throwException("Category not found");
        }
        return foundCategory;
    }
}
