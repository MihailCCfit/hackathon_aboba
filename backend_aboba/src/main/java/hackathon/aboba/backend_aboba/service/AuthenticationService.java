package hackathon.aboba.backend_aboba.service;

import hackathon.aboba.backend_aboba.exception.ServerExceptions;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.utils.JwtUtils;
import hackathon.aboba.backend_aboba.utils.SHA256Utils;
import hackathon.aboba.backend_aboba.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    public static final Set<String> NO_AUTH_ENDPOINTS = Set.of(
            "/api/v1/accounts/login",
            "/api/v1/accounts/refresh",
            "/error",
            "/api/v1/ping"
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

    public User refreshToken(String token) {
        validateToken(token);
        var refreshToken = token.substring(TokenUtils.BEARER_PREFIX.length());
        var decodedJWT = jwtUtils.decodeJWT(refreshToken);
        var username = decodedJWT.getSubject();
        var user = userService.findUserOrThrow(username);
        var oldToken = user.getRefreshToken();
        validateTokenIsNotEmpty(oldToken);
        if (!oldToken.equals(SHA256Utils.calculateSHA256(refreshToken))) {
            ServerExceptions.ACCESS_TOKEN_PROBLEM.throwException("It's not current refresh token");
        }
        jwtUtils.createTokensForUser(user);
        return user;
    }

    private void validateTokenIsNotEmpty(String token) {
        if (token == null || token.isEmpty()) {
            ServerExceptions.NOT_CURRENT_REFRESH_TOKEN.throwException();
        }
    }

    private void validateToken(String token) {
        if (token == null || !token.startsWith(TokenUtils.BEARER_PREFIX)) {
            ServerExceptions.ILLEGAL_REFRESH_TOKEN.throwException();
        }
    }

    public User logout(User user) {
        user.setAccessToken(null);
        user.setRefreshToken(null);
        return userService.createOrUpdateUser(user);
    }
}
