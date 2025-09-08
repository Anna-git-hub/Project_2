import clients.ApiClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import models.RegisterUserRequest;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected WebDriver driver;
    protected ApiClient apiClient;
    protected String accessToken;
    protected String email;
    protected String password = "ValidPass123";

    @BeforeEach
    public void setUp() {
        email = "test_" + System.currentTimeMillis() + "@example.com";
        apiClient = new ApiClient();

        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setEmail(email);
        userRequest.setPassword(password);
        userRequest.setName("AutoTest");

        Response registerResp = apiClient.register(userRequest);
        accessToken = registerResp.then().extract().path("accessToken");

        driver = new ChromeDriver();
        driver.get("https://stellarburgers.nomoreparties.site");
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            apiClient.deleteUser(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }
}