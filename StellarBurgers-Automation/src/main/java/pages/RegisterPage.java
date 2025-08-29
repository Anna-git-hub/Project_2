package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
    private WebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement nameField;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    @FindBy(xpath = "//p[text()='Некорректный пароль']")
    private WebElement passwordError;

    @FindBy(linkText = "Войти")
    private WebElement loginLink;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setName(String name) {
        nameField.sendKeys(name);
    }

    public void setEmail(String email) {
        emailField.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickRegister() {
        registerButton.click();
    }

    public boolean isPasswordErrorVisible() {
        return passwordError.isDisplayed();
    }

    public void clickLogin() {
        loginLink.click();
    }
}