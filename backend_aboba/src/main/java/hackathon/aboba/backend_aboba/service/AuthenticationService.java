package hackathon.aboba.backend_aboba.service;

import java.util.Set;

import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    public static final Set<String> NO_AUTH_ENDPOINTS = Set.of(
            "/api/v1/accounts/login",
            "/api/v1/accounts/token/refresh",
            "/error"
    );

    private final YandexIdService yandexIdService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public boolean authIsNeeded(String path) {
        return !NO_AUTH_ENDPOINTS.contains(path);
    }

    public User login(String token) {
        var response = yandexIdService.getId(yandexIdService.parseToken(token));
        String username = response.id();
        User user = userService.findUser(username);
        if (user == null) {
            User newUser = new User();
            newUser.setUsername(username);
            user = userService.createOrUpdateUser(newUser);
        }
        jwtUtils.createTokensForUser(user);
        return user;
    }
}
