package hackathon.aboba.backend_aboba.controller;

import hackathon.aboba.backend_aboba.dto.UserDto;
import hackathon.aboba.backend_aboba.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public UserDto login(
            @RequestHeader(value = "Authorization") String token
    ) {
        return authenticationService.login(token).toUserDto();
    }

    @PostMapping("/refresh")
    public UserDto refreshToken(
            @RequestHeader(value = "Authorization") String token
    ) {
        return authenticationService.refreshToken(token).toUserDto();
    }
}
