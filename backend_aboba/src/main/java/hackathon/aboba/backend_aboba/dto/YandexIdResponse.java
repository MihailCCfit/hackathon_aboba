package hackathon.aboba.backend_aboba.dto;

public record YandexIdResponse(
        String id,
        String login,
        String client_id,
        String display_name,
        String real_name,
        String first_name,
        String last_name,
        String sex,
        String default_email,
        String emails,
        String default_avatar_id,
        String is_avatar_empty,
        String psuid
) {
}