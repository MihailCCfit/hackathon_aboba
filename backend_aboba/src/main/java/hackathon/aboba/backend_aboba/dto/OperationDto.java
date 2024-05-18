package hackathon.aboba.backend_aboba.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import hackathon.aboba.backend_aboba.model.Operation;
import hackathon.aboba.backend_aboba.model.enums.OperationType;

/**
 * DTO for {@link hackathon.aboba.backend_aboba.model.Operation}
 */
public record OperationDto(
        CategoryDto categoryDto,
        OffsetDateTime date,
        BigDecimal sum,
        OperationType operationType
) implements Serializable {
    public Operation toOperation() {
        return new Operation(null, categoryDto.toCategory(), date, sum, operationType, null);
    }
}