package hackathon.aboba.backend_aboba;

public class TestUtils {
    private static final String BASE_URL = "/api/v1";
    private static final String CATEGORY_URL = "/categories";
    private static final String OPERATIONS_URL = "/operations";

    public static String getCategoryUrl() {
        return BASE_URL + CATEGORY_URL;
    }

    public static String getOperationsUrl() {
        return BASE_URL + OPERATIONS_URL;
    }
}
