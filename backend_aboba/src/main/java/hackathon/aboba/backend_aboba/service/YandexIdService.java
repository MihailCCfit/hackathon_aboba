package hackathon.aboba.backend_aboba.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import hackathon.aboba.backend_aboba.dto.YandexIdResponse;
import hackathon.aboba.backend_aboba.exception.ServerExceptions;
import hackathon.aboba.backend_aboba.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class YandexIdService {
    private final OkHttpClient client = new OkHttpClient();

    @Value("${yandex.id.path}")
    private String yandexIdPath;

    public YandexIdResponse getId(String token) {
        var request = new Request.Builder()
                .url(yandexIdPath)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        var call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            ServerExceptions.ILLEGAL_YANDEX_TOKEN.throwException();
        }
        try (ResponseBody body = response.body()) {
            if (response.code() != 200) {
                log.info("Error with yandex token: {}", body.toString());
                ServerExceptions.ILLEGAL_YANDEX_TOKEN.throwException();
            }
            var responseString = body.string();
            return new ObjectMapper().readValue(
                    responseString,
                    YandexIdResponse.class
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            ServerExceptions.ILLEGAL_YANDEX_TOKEN.throwException();
            return null;
        }
    }

    public String parseToken(
            String authorization
    ) {
        if (authorization == null || !authorization.contains(TokenUtils.BEARER_PREFIX)) {
            ServerExceptions.ILLEGAL_YANDEX_TOKEN.throwException();
            return null;
        }
        return authorization.substring(TokenUtils.BEARER_PREFIX.length());
    }
}
