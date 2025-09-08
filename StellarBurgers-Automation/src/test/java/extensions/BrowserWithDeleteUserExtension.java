package extensions;

import clients.ApiClient;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import models.LoginUserRequest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;

import static driver.WebDriverCreator.createWebDriver;

public class BrowserWithDeleteUserExtension implements BeforeEachCallback, AfterEachCallback, TestWatcher {

    private WebDriver driver;
    private String accessToken;
    private String email;
    private String name;
    private String password;
    private final ApiClient apiClient = new ApiClient();
    private static final Faker faker = new Faker();

    @Override
    public void beforeEach(ExtensionContext context) {
        name = faker.name().firstName();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        driver = createWebDriver();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(email);
        loginUserRequest.setPassword(password);
        Response response = apiClient.login(loginUserRequest);
        accessToken = response.then().extract().path("accessToken");
        if (accessToken != null) {
            try {
                apiClient.deleteUser(accessToken);
            } catch (Exception e) {
            }
        }

        accessToken = null;
        driver.quit();
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}