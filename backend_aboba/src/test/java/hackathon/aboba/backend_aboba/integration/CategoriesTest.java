package hackathon.aboba.backend_aboba.integration;

import java.util.List;

import hackathon.aboba.backend_aboba.dto.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static hackathon.aboba.backend_aboba.TestUtils.getCategoryUrl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class CategoriesTest extends AbstractIntegrationTest {
    private static final CategoryDto category = new CategoryDto("category", 5L, "emoji");

    @BeforeEach
    void setup() throws Exception {
        addCategory();
    }

    @Test
    void addCategoriesTest() throws Exception {
        mockMvc.perform(get(getCategoryUrl() + "/all"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(category))));
    }

    @Test
    void deleteCategoryTest() throws Exception {
        mockMvc.perform(delete(getCategoryUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(content().string(objectMapper.writeValueAsString(category)));

        mockMvc.perform(get(getCategoryUrl() + "/all"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));
    }

    private void addCategory() throws Exception {
        mockMvc.perform(post(getCategoryUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)));
    }
}
