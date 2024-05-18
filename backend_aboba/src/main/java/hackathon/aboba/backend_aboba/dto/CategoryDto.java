package hackathon.aboba.backend_aboba.dto;

import java.io.Serializable;

import hackathon.aboba.backend_aboba.model.Category;

/**
 * DTO for {@link hackathon.aboba.backend_aboba.model.Category}
 */
public record CategoryDto(
        String title,
        Long color,
        String emoji
) implements Serializable {
    public Category toCategory() {
        return new Category(null, title, color, emoji, null);
    }
}