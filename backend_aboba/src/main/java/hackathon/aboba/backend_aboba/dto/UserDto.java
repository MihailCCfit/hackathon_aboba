package hackathon.aboba.backend_aboba.dto;

public record UserDto(
        String username,
        String accessToken,
        String refreshToken
) {
}
