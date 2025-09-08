// pages/RegisterPage.java
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
    private WebDriver driver;

    @FindBy(xpath = "//label[text()='Имя']/following-sibling::input")
    private WebElement nameField;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    @FindBy(xpath = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    @FindBy(xpath = "//p[text()='Некорректный пароль']")
    private WebElement passwordError;

    @FindBy(linkText = "Войти")
    private WebElement loginLink;

    @FindBy(xpath = "//h2[text()='Регистрация']")
    private WebElement pageTitle;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void setEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickRegister() {
        registerButton.click();
    }

    public void clickLoginLink() {
        loginLink.click();
    }

    public boolean isPasswordErrorVisible() {
        return passwordError.isDisplayed();
    }

    public boolean isPageTitleDisplayed() {
        return pageTitle.isDisplayed();
    }

    public String getNameValue() {
        return nameField.getAttribute("value");
    }

    public String getEmailValue() {
        return emailField.getAttribute("value");
    }

    public String getPasswordValue() {
        return passwordField.getAttribute("value");
    }

    public WebDriver getDriver() {
        return driver;
    }
}