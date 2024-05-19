package hackathon.aboba.backend_aboba.integration;

import java.util.List;

import hackathon.aboba.backend_aboba.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class CategoriesTest extends AbstractIntegrationTest {

    @Test
    void addCategoriesTest() throws Exception {
        var body = new CategoryDto("category", 5L, "emoji");
        addCategory(body);

        mockMvc.perform(get("/api/v1/categories/all"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(body))));
    }

    @Test
    void deleteCategoryTest() throws Exception {
        var body = new CategoryDto("category", 5L, "emoji");
        addCategory(body);

        mockMvc.perform(delete("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(content().string(objectMapper.writeValueAsString(body)));

        mockMvc.perform(get("/api/v1/categories/all"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));
    }

    private void addCategory(CategoryDto category) throws Exception {
        mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)));
    }
}
