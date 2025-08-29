import clients.ApiClient;
import extensions.BrowserExtension;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.ProfileAccountPage;
import pages.ProfilePage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Регистрация и авторизация")
@Feature("Авторизация пользователя")
public class LogoutTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private ProfileAccountPage profileAccountPage;
    protected ApiClient apiClient;
    protected String email;
    protected String password;
    protected WebDriverWait wait;

    @RegisterExtension
    public BrowserExtension browserExtension = new BrowserExtension();


    @BeforeEach
    public void setUp() {
        this.email = browserExtension.getEmail();
        this.password = browserExtension.getPass();
        this.apiClient = new ApiClient();
    }

    @ParameterizedTest(name = "Тест выхода из аккаунта в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    @Story("Выход из аккаунта")
    @DisplayName("Пользователь может выйти из аккаунта")
    @Severity(SeverityLevel.NORMAL)
    public void testLogoutSuccessfully(String browser) {
        System.setProperty("browser", browser);

        driver = browserExtension.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String email = browserExtension.getEmail();
        String password = browserExtension.getPass();

        loginPage = new LoginPage(driver);
        profileAccountPage = new ProfileAccountPage(driver);

        loginPage.open();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));

        profilePage = new ProfilePage(driver);
        profilePage.open();
        profilePage.clickLinkByText("Личный Кабинет");

        wait.until(ExpectedConditions.urlContains("/account/profile"));

        profileAccountPage.clickBtnByText("Выход");
        wait.until(ExpectedConditions.urlContains("/account/profile"));
        profileAccountPage.clickLogoutBtn();
        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}