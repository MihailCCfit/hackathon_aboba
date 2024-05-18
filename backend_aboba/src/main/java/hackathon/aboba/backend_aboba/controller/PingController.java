package hackathon.aboba.backend_aboba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ping")
@RequiredArgsConstructor
public class PingController {
    @GetMapping
    public String ping() {
        return "Pong!";
    }
}
