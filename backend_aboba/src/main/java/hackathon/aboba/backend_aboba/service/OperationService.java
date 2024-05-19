package hackathon.aboba.backend_aboba.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import hackathon.aboba.backend_aboba.model.Category;
import hackathon.aboba.backend_aboba.model.Operation;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.model.enums.OperationType;
import hackathon.aboba.backend_aboba.repository.OperationRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public Operation createOperation(User user, Operation operation) {
        user.getOperations().add(operation);
        operation.setUser(user);
        operation.setCategory(categoryService.findCategoryOrThrow(operation.getCategory(), user));
        Operation newOperation = operationRepository.save(operation);
        userService.createOrUpdateUser(user);
        return newOperation;
    }

    public List<Operation> getAllOperationsByUser(User user) {
        return operationRepository.findByUser_Id(user.getId());
    }

    public Operation removeOperation(User user, Operation operation) {
        Operation operationToRemove = operationRepository.findByDateAndSumAndUser_Id(
                operation.getDate(),
                operation.getSum(),
                user.getId()
        );
        if (operationToRemove != null) {
            operationRepository.delete(operationToRemove);
        }
        return operationToRemove;
    }

    public List<Operation> filterOperations(
            User user,
            OffsetDateTime fromTime,
            OffsetDateTime toTime,
            @Nullable OperationType operationType,
            Optional<Category> category
    ) {
        if (operationType == null && category.isEmpty()) {
            return operationRepository.findByDateBetweenAndUser_Id(fromTime, toTime, user.getId());
        }
        if (operationType == null) {
            return operationRepository.findByCategory_TitleAndDateBetweenAndUser_Id(
                    category.get().getTitle(), fromTime, toTime, user.getId()
            );
        }
        if (category.isEmpty()) {
            return operationRepository.findByDateBetweenAndUser_IdAndOperationType(
                    fromTime, toTime, user.getId(), operationType
            );
        }
        return operationRepository.findByUser_IdAndDateBetweenAndOperationTypeAndCategory_Title(
                user.getId(), fromTime, toTime, operationType, category.map(Category::getTitle).orElse(null)
        );
    }
}
