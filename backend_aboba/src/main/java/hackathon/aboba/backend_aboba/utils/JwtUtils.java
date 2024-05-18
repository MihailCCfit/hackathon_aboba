package hackathon.aboba.backend_aboba.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Getter
public class JwtUtils {
    private final UserService userService;

    @Value("${security.access.token.lifetime}")
    public Long accessTokenLifetime;

    @Value("${security.refresh.token.lifetime}")
    private Long refreshTokenLifetime;

    @Value("${security.secret}")
    private String secret;

    public void createTokensForUser(User user) {
        createTokensForUser(user, accessTokenLifetime, refreshTokenLifetime);
    }
    public void createTokensForUser(User user, Long accessTokenLifetime, Long refreshTokenLifetime) {
        var username = user.getUsername();
        var algorithm = Algorithm.HMAC256(secret.getBytes());
        var accessToken =
                JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenLifetime))
                        .withIssuer("access")
                        .withIssuedAt(new Date())
                        .sign(algorithm);
        var refreshToken =
                JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenLifetime))
                        .withIssuer("refresh")
                        .withIssuedAt(new Date())
                        .sign(algorithm);
        userService.updateAccessToken(user, SHA256Utils.calculateSHA256(accessToken));
        userService.updateRefreshToken(user, SHA256Utils.calculateSHA256(refreshToken));
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);
    }

    public DecodedJWT decodeJWT(String token) {
        var algorithm = Algorithm.HMAC256(secret);
        var verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
