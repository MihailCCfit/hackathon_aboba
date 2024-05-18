package hackathon.aboba.backend_aboba.controller;

import java.util.List;

import hackathon.aboba.backend_aboba.dto.OperationDto;
import hackathon.aboba.backend_aboba.model.Operation;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.service.OperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/operations")
@Slf4j
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;

    @PostMapping
    public OperationDto createOperation(
            @AuthenticationPrincipal User user,
            @RequestBody OperationDto operationDto
    ) {
        return operationService.createOperation(user, operationDto.toOperation()).toOperationDto();
    }

    @GetMapping("/all")
    public List<OperationDto> getAllCategories(
            @AuthenticationPrincipal User user
    ) {
        return operationService.getAllOperationsByUser(user).stream().map(Operation::toOperationDto).toList();
    }

    @DeleteMapping
    public OperationDto deleteOperation(
            @AuthenticationPrincipal User user,
            OperationDto operationDto
    ) {
        return operationService.removeOperation(user, operationDto.toOperation()).toOperationDto();
    }
}
