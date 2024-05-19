package hackathon.aboba.backend_aboba;

import hackathon.aboba.backend_aboba.exception.ServerException;
import hackathon.aboba.backend_aboba.service.YandexIdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YandexIdServiceTest {

    @Autowired
    private YandexIdService yandexIdService;

    @Test
    void testGetId() {
        Assertions.assertThrows(ServerException.class, () -> yandexIdService.getId("aboba"));
    }

    @Test
    void testParseBadToken() {
        Assertions.assertThrows(ServerException.class, () -> yandexIdService.parseToken("aboba"));
    }

    @Test
    void testParseToken() {
        Assertions.assertEquals("aboba", yandexIdService.parseToken("Bearer aboba"));
    }
}
