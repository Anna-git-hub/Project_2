package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConstructorPage {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    private WebDriver driver;

    @FindBy(xpath = "//span[text()='Булки']")
    private WebElement bunsTab;

    @FindBy(xpath = "//span[text()='Соусы']")
    private WebElement saucesTab;

    @FindBy(xpath = "//span[text()='Начинки']")
    private WebElement fillingsTab;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab') and .//span[text()='Булки']]/parent::div")
    private WebElement bunsTabContainer;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab') and .//span[text()='Соусы']]/parent::div")
    private WebElement saucesTabContainer;

    @FindBy(xpath = "//div[contains(@class, 'tab_tab') and .//span[text()='Начинки']]/parent::div")
    private WebElement fillingsTabContainer;

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ConstructorPage open() {
        driver.get(BASE_URL);
        return this;
    }

    public void clickBuns() {
        bunsTab.click();
    }

    public void clickSauces() {
        saucesTab.click();
    }

    public void clickFillings() {
        fillingsTab.click();
    }

    public boolean isBunsActive() {
        return bunsTabContainer.getAttribute("class").contains("tab_tab_type_current");
    }

    public boolean isSaucesActive() {
        return saucesTabContainer.getAttribute("class").contains("tab_tab_type_current");
    }

    public boolean isFillingsActive() {
        return fillingsTabContainer.getAttribute("class").contains("tab_tab_type_current");
    }
}