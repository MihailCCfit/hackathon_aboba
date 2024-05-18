package hackathon.aboba.backend_aboba.repository;

import hackathon.aboba.backend_aboba.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}