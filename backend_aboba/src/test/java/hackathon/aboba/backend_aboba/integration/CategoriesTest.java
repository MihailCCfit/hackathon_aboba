package hackathon.aboba.backend_aboba.integration;

import java.util.List;

import hackathon.aboba.backend_aboba.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class CategoriesTest extends AbstractIntegrationTest {

    @Test
    void addCategoriesTest() throws Exception {
        var body = new CategoryDto("category", 5L, "emoji");
        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(print());

        mockMvc.perform(get("/api/v1/categories/all"))
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(body))));
    }
}
