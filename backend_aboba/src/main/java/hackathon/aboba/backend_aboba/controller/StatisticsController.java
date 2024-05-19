package hackathon.aboba.backend_aboba.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import hackathon.aboba.backend_aboba.dto.CategoryDto;
import hackathon.aboba.backend_aboba.dto.OperationDto;
import hackathon.aboba.backend_aboba.model.Operation;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.model.enums.OperationType;
import hackathon.aboba.backend_aboba.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class StatisticsController {
    private final OperationService operationService;

    @PostMapping
    public List<OperationDto> getStatistics(
            @AuthenticationPrincipal User user,
            @RequestParam OffsetDateTime fromTime,
            @RequestParam OffsetDateTime toTime,
            @RequestParam(required = false) OperationType operationType,
            @RequestBody(required = false) Optional<CategoryDto> category
    ) {
        return operationService.filterOperations(
                user,
                fromTime,
                toTime,
                operationType,
                category.map(CategoryDto::toCategory)
        ).stream().map(Operation::toOperationDto).toList();
    }
}
