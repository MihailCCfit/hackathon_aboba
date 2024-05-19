package hackathon.aboba.backend_aboba.integration;

import com.auth0.jwt.JWT;
import hackathon.aboba.backend_aboba.service.UserService;
import hackathon.aboba.backend_aboba.utils.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Test
    public void testTokens() {
        var user = userService.findUserOrThrow("admin");
        jwtUtils.createTokensForUser(user);
        var token = user.getAccessToken();
        Assertions.assertEquals("admin", JWT.decode(token).getSubject());
        user = userService.findUserOrThrow("admin");
        Assertions.assertFalse(user.getAccessToken().isBlank());
        Assertions.assertFalse(user.getRefreshToken().isBlank());

    }
}
