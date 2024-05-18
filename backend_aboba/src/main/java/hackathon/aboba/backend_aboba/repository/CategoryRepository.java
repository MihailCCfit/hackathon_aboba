package hackathon.aboba.backend_aboba.repository;

import java.util.List;

import hackathon.aboba.backend_aboba.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser_Id(@NonNull Long id);

    Category findByTitle(@NonNull String title);

    Category findByTitleAndUser_Id(@NonNull String title, @NonNull Long id);
}