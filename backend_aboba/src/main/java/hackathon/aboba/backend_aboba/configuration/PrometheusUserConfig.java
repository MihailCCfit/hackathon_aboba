package hackathon.aboba.backend_aboba.configuration;

import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.service.UserService;
import hackathon.aboba.backend_aboba.utils.SHA256Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("!testing")
@Configuration
@Component
@RequiredArgsConstructor
public class PrometheusUserConfig implements ApplicationListener<ApplicationReadyEvent> {
    private final UserService userService;
    @Value("${name.prometheus}")
    public String name;
    @Value("${jwt.prometheus}")
    public String jwt;


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        var res = userService.findUser(name);
        if (res == null) {
            User user = new User();
            user.setUsername(name);
            res = userService.createOrUpdateUser(user);
        }
        userService.updateAccessToken(res, SHA256Utils.calculateSHA256(jwt));
    }
}
