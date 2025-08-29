package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/login";

    private final WebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@name='Пароль']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement loginButton;

    @FindBy(linkText = "Зарегистрироваться")
    private WebElement registerLink;

    @FindBy(linkText = "Восстановить пароль")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//h1[contains(@class, 'text_type_main-large')]")
    private WebElement title;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage open() {
        driver.get(BASE_URL);
        return this;
    }

    public String titleText() {
        return title.getText();
    }

    public void setEmail(String email) {
        emailField.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void clickRegister() {
        registerLink.click();
    }

    public void clickForgotPassword() {
        forgotPasswordLink.click();
    }
}