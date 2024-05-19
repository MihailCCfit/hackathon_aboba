package hackathon.aboba.backend_aboba.repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import hackathon.aboba.backend_aboba.model.Operation;
import hackathon.aboba.backend_aboba.model.enums.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
    List<Operation> findByUser_Id(@NonNull Long id);

    Operation findByDateAndSumAndUser_Id(@NonNull OffsetDateTime date, @NonNull BigDecimal sum, Long id);

    List<Operation> findByUser_IdAndDateBetweenAndOperationTypeAndCategory_Title(
            @NonNull Long id,
            @NonNull OffsetDateTime dateStart,
            @NonNull OffsetDateTime dateEnd,
            @Nullable OperationType operationType,
            @Nullable String title
    );

    List<Operation> findByDateBetweenAndUser_Id(@NonNull OffsetDateTime dateStart, @NonNull OffsetDateTime dateEnd,
                                                @NonNull Long id);

    List<Operation> findByCategory_TitleAndDateBetweenAndUser_Id(@NonNull String title,
                                                                 @NonNull OffsetDateTime dateStart,
                                                                 @NonNull OffsetDateTime dateEnd, @NonNull Long id);

    List<Operation> findByDateBetweenAndUser_IdAndOperationType(@NonNull OffsetDateTime dateStart,
                                                                @NonNull OffsetDateTime dateEnd, @NonNull Long id,
                                                                @NonNull OperationType operationType);
}