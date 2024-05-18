package hackathon.aboba.backend_aboba.configuration;

import java.io.IOException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import hackathon.aboba.backend_aboba.controller.ExceptionHandlingController;
import hackathon.aboba.backend_aboba.exception.ServerExceptions;
import hackathon.aboba.backend_aboba.service.AuthenticationService;
import hackathon.aboba.backend_aboba.service.UserService;
import hackathon.aboba.backend_aboba.utils.JwtUtils;
import hackathon.aboba.backend_aboba.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final JwtUtils jwtUtils;

    private final ExceptionHandlingController exceptionHandlingController;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (!authenticationService.authIsNeeded(request.getServletPath())) {
            log.info("Without authentication {}", request.getServletPath());
            filterChain.doFilter(request, response);
            return;
        }
        log.info("With authorization {}", request.getServletPath());
        var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!validateAuthorizationHeader(authorizationHeader, response)) {
            return;
        }
        try {
            var token = authorizationHeader.substring(TokenUtils.BEARER_PREFIX.length());
            var decodedJWT = jwtUtils.decodeJWT(token);
            var username = decodedJWT.getSubject();
            log.info("User trying authorize: {}", username);
            var user = userService.findUserOrThrow(username);
            var oldToken = user.getAccessToken();
            if (!validateToken(oldToken, response)) {
                return;
            }

            if (!oldToken.equals(token)) {
                log.warn("It's not current access token {}", username);
                exceptionHandlingController.handle(
                        response,
                        ServerExceptions.ACCESS_TOKEN_PROBLEM
                                .getServerExceptionWithMoreInfo("It's not current access token")
                );
            }
            var authentication =
                    new UsernamePasswordAuthenticationToken(user, null, null);

            var securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            log.info("User {} is authenticated", username);
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            exceptionHandlingController.handle(response, ServerExceptions.ACCESS_TOKEN_EXPIRED.getServerException());
        } catch (JWTVerificationException e) {
            exceptionHandlingController.handle(response, ServerExceptions.ILLEGAL_ACCESS_TOKEN.getServerException());
        }
    }

    private boolean validateAuthorizationHeader(
            String authHeader,
            HttpServletResponse response
    ) throws IOException {
        if (authHeader == null || !authHeader.startsWith(TokenUtils.BEARER_PREFIX)) {
            exceptionHandlingController.handle(response, ServerExceptions.NO_ACCESS_TOKEN.getServerException());
            return false;
        }
        return true;
    }

    private boolean validateToken(String token, HttpServletResponse response) throws IOException {
        if (token == null || token.isEmpty()) {
            exceptionHandlingController.handle(response, ServerExceptions.NO_ACCESS_TOKEN.getServerException());
            return false;
        }
        return true;
    }
}
