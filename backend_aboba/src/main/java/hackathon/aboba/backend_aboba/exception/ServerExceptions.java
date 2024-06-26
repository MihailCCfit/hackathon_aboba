package hackathon.aboba.backend_aboba.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ServerExceptions {
    NOT_FOUND_EXCEPTION(new ServerException(HttpStatus.NOT_FOUND, "NOT_FOUND_EXCEPTION")),
    ACCESS_TOKEN_EXPIRED(new ServerException(HttpStatus.UNAUTHORIZED, "ACCESS_TOKEN_EXPIRED")),
    REFRESH_TOKEN_EXPIRED(new ServerException(HttpStatus.UNAUTHORIZED, "REFRESH_TOKEN_EXPIRED")),
    ILLEGAL_ACCESS_TOKEN(new ServerException(HttpStatus.UNAUTHORIZED, "ILLEGAL_ACCESS_TOKEN")),
    ILLEGAL_REFRESH_TOKEN(new ServerException(HttpStatus.UNAUTHORIZED, "ILLEGAL_REFRESH_TOKEN")),
    UNAUTHORIZED(new ServerException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED")),
    INVALID_PARAMETER(new ServerException(HttpStatus.BAD_REQUEST, "INVALID_PARAMETER")),
    BAD_REQUEST(new ServerException(HttpStatus.BAD_REQUEST)),
    NO_ACCESS_TOKEN(new ServerException(HttpStatus.UNAUTHORIZED, "NO_ACCESS_TOKEN")),
    ACCESS_TOKEN_PROBLEM(new ServerException(HttpStatus.UNAUTHORIZED, "ACCESS_TOKEN_PROBLEM")),
    ILLEGAL_YANDEX_TOKEN(new ServerException(HttpStatus.UNAUTHORIZED, "ILLEGAL_YANDEX_TOKEN")),
    ROLE_NOT_EXISTS(new ServerException(HttpStatus.BAD_REQUEST, "ROLE_NOT_EXISTS")),
    USER_NOT_FOUND(new ServerException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND")),
    BAD_REFRESH_TOKEN(new ServerException(HttpStatus.BAD_REQUEST, "BAD_REFRESH_TOKEN")),
    NOT_CURRENT_REFRESH_TOKEN(new ServerException(HttpStatus.FORBIDDEN, "NOT_CURRENT_REFRESH_TOKEN"));

    private final ServerException serverException;

    ServerExceptions(ServerException serverException) {
        this.serverException = serverException;
    }

    public void throwException() throws ServerException {
        throw serverException;
    }

    public void throwException(String moreInfo) throws ServerException {
        throw getServerExceptionWithMoreInfo(this.getServerException(), moreInfo);
    }

    public static ServerException getServerExceptionWithMoreInfo(
            ServerException serverException,
            String moreInfo
    ) {
        return serverException.getServerExceptionWithMoreInfo(moreInfo);
    }
}
