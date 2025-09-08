package extensions;

import clients.ApiClient;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;

import static driver.WebDriverCreator.createWebDriver;

public class BrowserExtension implements BeforeEachCallback, AfterEachCallback, TestWatcher {

    private WebDriver driver;
    private String accessToken;
    private String email;
    private String password;
    private final ApiClient apiClient = new ApiClient();
    private static final Faker faker = new Faker();

    @Override
    public void beforeEach(ExtensionContext context) {
        driver = createWebDriver();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
    }

    @Override
    public void afterEach(ExtensionContext context) {
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