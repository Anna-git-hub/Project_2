package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    private WebDriver driver;

    @FindBy(css = "p[class^='AppHeader']")
    private List<WebElement> headerLinks;

    @FindBy(css = "button")
    private List<WebElement> buttons;

    @FindBy(xpath = "//button[text()='Выход']")
    private WebElement logoutButton;

    @FindBy(xpath = "//p[contains(text(),'Личный Кабинет')]")
    private WebElement profileBtn;

    @FindBy(xpath = "//input[@name='Name']")
    private WebElement nameField;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickLinkByText(String text) {
        headerLinks.stream().filter(l -> l.getText().equals(text)).findFirst()
                .ifPresent(WebElement::click);
    }

    public void clickBtnByText(String text) {
        buttons.stream().filter(b -> b.getText().equals(text)).findFirst()
                .ifPresent(WebElement::click);
    }

    public void isDisplayedBtnWithText(String text) {
        buttons.stream().filter(b -> b.getText().equals(text)).findFirst()
                .ifPresent(WebElement::isDisplayed);
    }

    public void clickLogout() {
        logoutButton.click();
    }

    public String getUserName() {
        return nameField.getAttribute("value");
    }

    public ProfilePage open() {
        driver.get(BASE_URL);
        return this;
    }
}