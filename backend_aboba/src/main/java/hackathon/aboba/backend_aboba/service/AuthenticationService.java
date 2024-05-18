package hackathon.aboba.backend_aboba.service;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public static final Set<String> NO_AUTH_ENDPOINTS = Set.of(
            "/api/v1/accounts/login",
            "/api/v1/accounts/token/refresh"
    );

    public boolean authIsNeeded(String path) {
        return !NO_AUTH_ENDPOINTS.contains(path);
    }
}
