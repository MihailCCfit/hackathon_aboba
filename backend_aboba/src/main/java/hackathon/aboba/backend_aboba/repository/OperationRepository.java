package hackathon.aboba.backend_aboba.repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import hackathon.aboba.backend_aboba.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
    List<Operation> findByUser_Id(@NonNull Long id);

    Operation findByDateAndSumAndUser_Id(@NonNull OffsetDateTime date, @NonNull BigDecimal sum, Long id);
}