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
import static hackathon.aboba.backend_aboba.TestUtils.getStatisticsUrl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class StatisticsTest extends AbstractIntegrationTest {
    private static final CategoryDto category = new CategoryDto("category", 5L, "emoji");
    private static final OperationDto operation = new OperationDto(
            category,
            OffsetDateTime.parse("2024-05-15T10:15:30Z"),
            BigDecimal.valueOf(15),
            OperationType.OUTGOING

    );

    @BeforeEach
    void setup() throws Exception {
        addCategory();
        mockMvc.perform(post(getOperationsUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operation)));
    }

    @Test
    void getStatisticsBetweenDatesTest() throws Exception {
        mockMvc.perform(post(getStatisticsUrl())
                        .param("fromTime", "2024-05-14T10:15:30Z")
                        .param("toTime", "2024-05-16T10:15:30Z"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(operation))));
    }

    @Test
    void getStatisticsBadDatesTest() throws Exception {
        mockMvc.perform(post(getStatisticsUrl())
                        .param("fromTime", "2024-05-13T10:15:30Z")
                        .param("toTime", "2024-05-14T10:15:30Z"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));

        mockMvc.perform(post(getStatisticsUrl())
                        .param("fromTime", "2024-05-17T10:15:30Z")
                        .param("toTime", "2024-05-18T10:15:30Z"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));
    }

    @Test
    void getStatisticsByOperationTypeTest() throws Exception {
        mockMvc.perform(post(getStatisticsUrl())
                        .param("fromTime", "2024-05-14T10:15:30Z")
                        .param("toTime", "2024-05-16T10:15:30Z")
                        .param("operationType", "OUTGOING"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(operation))));

        mockMvc.perform(post(getStatisticsUrl())
                        .param("fromTime", "2024-05-14T10:15:30Z")
                        .param("toTime", "2024-05-16T10:15:30Z")
                        .param("operationType", "INCOMING"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));
    }

    @Test
    void getStatisticsByCategoryTest() throws Exception {
        mockMvc.perform(post(getStatisticsUrl())
                        .param("fromTime", "2024-05-14T10:15:30Z")
                        .param("toTime", "2024-05-16T10:15:30Z")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(operation))));

        mockMvc.perform(post(getStatisticsUrl())
                        .param("fromTime", "2024-05-14T10:15:30Z")
                        .param("toTime", "2024-05-16T10:15:30Z")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CategoryDto("bad-title", 6L, "bad-emoji")
                        )))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));
    }

    @Test
    void getStatisticsAllFiltersTest() throws Exception {
        mockMvc.perform(post(getStatisticsUrl())
                        .param("fromTime", "2024-05-14T10:15:30Z")
                        .param("toTime", "2024-05-16T10:15:30Z")
                        .param("operationType", "OUTGOING")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(operation))));
    }

    private void addCategory() throws Exception {
        mockMvc.perform(post(getCategoryUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)));
    }
}
