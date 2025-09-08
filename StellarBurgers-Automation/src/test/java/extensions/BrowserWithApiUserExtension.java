package extensions;

import clients.ApiClient;
import models.RegisterUserRequest;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import com.github.javafaker.Faker;

import static driver.WebDriverCreator.createWebDriver;

public class BrowserWithApiUserExtension implements BeforeEachCallback, AfterEachCallback, TestWatcher {

    private WebDriver driver;
    private String accessToken;
    private String email;
    private String password;
    private final ApiClient apiClient = new ApiClient();
    private static final Faker faker = new Faker();

    @Override
    public void beforeEach(ExtensionContext context) {
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        String name = faker.name().firstName();

        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setEmail(email);
        userRequest.setPassword(password);
        userRequest.setName(name);

        var response = apiClient.register(userRequest);
        accessToken = response.then().extract().path("accessToken");

        driver = createWebDriver();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        driver.quit();
        if (accessToken != null) {
            try {
                apiClient.deleteUser(accessToken);
                System.out.println("Пользователь удалён через API");
            } catch (Exception e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
            accessToken = null;
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.err.println("Тест упал: " + context.getDisplayName());
        System.err.println("Причина: " + cause.getMessage());
    }

    public WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver ещё не создан или уже закрыт");
        }
        return driver;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return password;
    }

    public String getAccessToken() {
        return accessToken;
    }
}