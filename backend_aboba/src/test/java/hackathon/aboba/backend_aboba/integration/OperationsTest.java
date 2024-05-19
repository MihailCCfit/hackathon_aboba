package hackathon.aboba.backend_aboba.integration;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import hackathon.aboba.backend_aboba.dto.CategoryDto;
import hackathon.aboba.backend_aboba.dto.OperationDto;
import hackathon.aboba.backend_aboba.model.enums.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static hackathon.aboba.backend_aboba.TestUtils.getCategoryUrl;
import static hackathon.aboba.backend_aboba.TestUtils.getOperationsUrl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OperationsTest extends AbstractIntegrationTest {

    private static final CategoryDto category = new CategoryDto("category", 5L, "emoji");
    private static final OperationDto operation = new OperationDto(
            category,
            OffsetDateTime.parse("2024-05-15T10:15:30Z"),
            BigDecimal.valueOf(15),
            OperationType.OUTGOING
    );

    @BeforeEach
    void setup() throws Exception {
        mockMvc.perform(post(getCategoryUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)));

        addOperation(operation);
    }

    @Test
    void addOperationTest() throws Exception {
        mockMvc.perform(get(getOperationsUrl() + "/all"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(operation))));
    }

    @Test
    void addOperationBadCategoryTest() throws Exception {
        OperationDto operation = new OperationDto(
                new CategoryDto("bad-title", 6L, "bad-emoji"),
                OffsetDateTime.parse("2024-05-15T10:15:30Z"),
                BigDecimal.valueOf(15),
                OperationType.OUTGOING
        );
        mockMvc.perform(post(getOperationsUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category not found"));
    }

    @Test
    void deleteOperationTest() throws Exception {
        mockMvc.perform(delete(getOperationsUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operation)))
                .andExpect(content().string(objectMapper.writeValueAsString(operation)));

        mockMvc.perform(get(getOperationsUrl() + "/all"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));
    }

    private void addOperation(OperationDto operationDto) throws Exception {
        mockMvc.perform(post(getOperationsUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operationDto)));
    }
}
