import clients.ApiClient;
import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import extensions.BrowserWithApiUserExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import pages.ProfilePage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Регистрация и авторизация")
@Feature("Авторизация пользователя")
public class LoginTest{

    private WebDriver driver;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    protected ApiClient apiClient;
    protected String email;
    protected String password;
    protected WebDriverWait wait;

    @RegisterExtension
    public BrowserWithApiUserExtension browserWithApiUserExtension = new BrowserWithApiUserExtension();


    @BeforeEach
    public void setUp() {
        this.email = browserWithApiUserExtension.getEmail();
        this.password = browserWithApiUserExtension.getPass();
        this.apiClient = new ApiClient();
    }

    @Test
    @Story("Вход через личный кабинет")
    @DisplayName("Позитивный сценарий: вход с валидными данными")
    @Description("Пользователь вводит email и пароль, переходит в конструктор")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Документация", url = "https://stellarburgers.nomoreparties.site/docs")
    public void testLoginViaLoginButton(){
        driver = browserWithApiUserExtension.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        loginPage.open();

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();

        assertTrue(profilePage.isProfilePageOpen());
    }
}