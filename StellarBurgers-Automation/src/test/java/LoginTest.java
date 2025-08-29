import clients.ApiClient;
import io.qameta.allure.*;
import io.restassured.response.Response;
import jdk.jfr.Description;
import models.LoginUserRequest;
import models.LogoutUserRequest;
import models.RegisterUserRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.ProfilePage;
import com.github.javafaker.Faker;
import extensions.BrowserExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.time.Duration;

import static generators.UsersGenerator.randomUserRegister;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Регистрация и авторизация")
@Feature("Авторизация пользователя")
public class LoginTest{

    private WebDriver driver;
    private LoginPage loginPage;
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

    @ParameterizedTest(name = "Тест входа в аккаунт в браузере: {0}")
    @ValueSource(strings = {"firefox","chrome"})
    @Story("Вход через личный кабинет")
    @DisplayName("Позитивный сценарий: вход с валидными данными")
    @Description("Пользователь вводит email и пароль, переходит в конструктор")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Документация", url = "https://stellarburgers.nomoreparties.site/docs")
    public void testLoginViaLoginButton(String browser){
        System.setProperty("browser", browser);
        driver = browserExtension.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String email = browserExtension.getEmail();
        String password = browserExtension.getPass();

        loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        assertFalse(driver.getCurrentUrl().contains("/login"));
    }
}