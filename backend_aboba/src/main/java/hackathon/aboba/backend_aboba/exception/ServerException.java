package hackathon.aboba.backend_aboba.exception;


import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ServerException extends RuntimeException {
    private final HttpStatus status;
    private final String code;
    private final String message;

    public ServerException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ServerException(HttpStatus status, String code) {
        this(status, code, code);
    }

    public ServerException(HttpStatus status) {
        this(status, status.name(), status.name());
    }

    public static void throwException(ServerException serverException, String moreInfo) {
        throw new ServerException(serverException.status, serverException.code, moreInfo);
    }

    public Map<String, Object> getAnswer() {
        return Map.of("code", code, "message", message, "status", status.value());
    }
}
