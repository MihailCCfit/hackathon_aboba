package hackathon.aboba.backend_aboba.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@Transactional
public abstract class AbstractIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;



    @BeforeEach
    void setupAuthPrincipal() {
        var user = new User();
        user.setUsername("admin");
        user = userRepository.save(user);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        var authentication =
                new UsernamePasswordAuthenticationToken(user, null, null);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
