import clients.ApiClient;
import com.github.javafaker.Faker;
import extensions.BrowserWithDeleteUserExtension;
import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.ProfilePage;
import pages.RegisterPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Регистрация")
@Feature("Регистрация пользователя")
public class RegisterTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private RegisterPage registerPage;

    protected ApiClient apiClient;
    protected String name;
    protected String email;
    protected String password;
    protected String accessToken;

    protected WebDriverWait wait;

    private static final Faker faker = new Faker();


    @RegisterExtension
    public BrowserWithDeleteUserExtension browserWithDeleteUserExtension = new BrowserWithDeleteUserExtension();


    @BeforeEach
    public void setUp() {
        this.apiClient = new ApiClient();
        this.email = browserWithDeleteUserExtension.getEmail();
        this.name = browserWithDeleteUserExtension.getName();
        this.password = browserWithDeleteUserExtension.getPassword();
    }

    @Test
    @Story("Регистрация пользователя")
    @DisplayName("Позитивный сценарий: регистрация пользователя")
    @Description("Пользователь регистрируется")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Документация", url = "https://stellarburgers.nomoreparties.site/docs")
    public void testLoginViaLoginButton() {
        driver = browserWithDeleteUserExtension.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        registerPage = new RegisterPage(driver);

        loginPage.open();

        loginPage.clickLinkWithText("Зарегистрироваться");
        registerPage.setName(name);
        registerPage.setEmail(email);
        registerPage.setPassword(password);

        registerPage.clickRegister();

        assertTrue(loginPage.waitForLoginPage());
        assertTrue(loginPage.isLoginPageOpen());

    }
}