package hackathon.aboba.backend_aboba.repository;

import hackathon.aboba.backend_aboba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
