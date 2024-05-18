package hackathon.aboba.backend_aboba.service;

import java.util.List;

import hackathon.aboba.backend_aboba.model.Operation;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;
    private final UserService userService;

    public Operation createOperation(User user, Operation operation) {
        user.getOperations().add(operation);
        operation.setUser(user);
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
}
