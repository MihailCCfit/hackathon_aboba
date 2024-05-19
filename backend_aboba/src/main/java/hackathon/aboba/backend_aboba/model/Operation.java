package hackathon.aboba.backend_aboba.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import hackathon.aboba.backend_aboba.dto.OperationDto;
import hackathon.aboba.backend_aboba.model.enums.OperationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Operation", indexes = {
        @Index(name = "idx_operation_category_id", columnList = "category_id, date, operationType, user_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER/*, cascade = CascadeType.MERGE*/)
    @JoinColumn(name = "category_id")
    private Category category;

    private OffsetDateTime date;

    private BigDecimal sum;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @ManyToOne(fetch = FetchType.EAGER/*, cascade = CascadeType.MERGE*/)
    @JoinColumn(name = "user_id")
    private User user;

    public OperationDto toOperationDto() {
        return new OperationDto(
                category.toCategoryDto(),
                date,
                sum,
                operationType
        );
    }
}
