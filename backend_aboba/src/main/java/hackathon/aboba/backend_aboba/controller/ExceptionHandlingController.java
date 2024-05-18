package hackathon.aboba.backend_aboba.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hackathon.aboba.backend_aboba.exception.ServerException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlingController {
    @ExceptionHandler(ServerException.class)
    public void handle(HttpServletResponse response, ServerException serverException) throws IOException {
        response.setStatus(serverException.getStatus().value());
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            bw.write(mapper.writeValueAsString(serverException.getAnswer()));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }
    }

}