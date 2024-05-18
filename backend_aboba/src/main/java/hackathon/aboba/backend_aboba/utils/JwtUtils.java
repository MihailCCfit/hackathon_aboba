package hackathon.aboba.backend_aboba.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private static String secret;

    public Tokens createTokens(User user) {
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
        userService.updateAccessToken(user, accessToken);
        userService.updateRefreshToken(user, refreshToken);
        return new Tokens(accessToken, refreshToken);
    }

    public DecodedJWT decodeJWT(String token) {
        var algorithm = Algorithm.HMAC256(secret);
        var verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public record Tokens(
            String accessToken,
            String refreshToken
    ) {
    }
}
