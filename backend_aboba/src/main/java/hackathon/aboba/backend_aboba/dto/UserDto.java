package hackathon.aboba.backend_aboba.dto;

import java.io.Serializable;

import hackathon.aboba.backend_aboba.model.User;

/**
 * DTO for {@link User}
 */
public record UserDto(
        String username,
        String accessToken,
        String refreshToken
) implements Serializable {
}