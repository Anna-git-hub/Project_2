package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfileAccountPage {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/account/profile";

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "a[href='/account/profile']")
    private WebElement accountProfile;

    @FindBy(css = "p[class^='AppHeader']")
    private List<WebElement> headerLinks;

    @FindBy(css = "button")
    private WebElement logoutBtn;

    @FindBy(css = "button")
    private List<WebElement> buttons;

    @FindBy(xpath = "//button[text()='Выход']")
    private WebElement logoutButton;

    @FindBy(xpath = "//p[contains(text(),'Личный Кабинет')]")
    private WebElement profileBtn;

    @FindBy(xpath = "//input[@name='Name']")
    private WebElement nameField;

    public ProfileAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean waitForProfileAccountPage() {
        try {
            wait.until(ExpectedConditions.urlToBe(BASE_URL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProfileAccountPageOpen() {
        return driver.getCurrentUrl().contains(BASE_URL);
    }

    public void clickLinkByText(String text) {
        headerLinks.stream().filter(l -> l.getText().equals(text)).findFirst()
                .ifPresent(WebElement::click);
    }

    public void clickProfileBtn() {
        accountProfile.click();
    }

    public boolean isProfileBtnIsDisplayed() {
        return accountProfile.isDisplayed();
    }

    public void clickLogoutBtn() {
        logoutBtn.click();
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

    public ProfileAccountPage open() {
        driver.get(BASE_URL);
        return this;
    }
}