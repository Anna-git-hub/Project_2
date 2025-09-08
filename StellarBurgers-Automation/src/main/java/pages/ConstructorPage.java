package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class ConstructorPage {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    private WebDriver driver;
    private WebDriverWait wait;


    @FindBy(css = "section[class^='BurgerIngredients']")
    private WebElement burgerIngredientsSection;

    @FindBy(css = "section[class^='BurgerConstructor']")
    private WebElement burgerConstructorSection;

    @FindBy(css = "section[class^='BurgerIngredients'] span")
    private List<WebElement> tabsInBurgerIngredients;

    @FindBy(css = "span[class='constructor-element__text']")
    private List<WebElement> constructorTargets;

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

    public boolean isIngredientInConstructor(String ingredientName) {
        try {
            WebElement ingredient = driver.findElement(By.xpath("//p[text()='" + ingredientName + "']"));
            return ingredient.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isBurgerIngredientsSectionIsDisplayed() {
        return burgerIngredientsSection.isDisplayed();
    }

    public boolean isBurgerConstructorSectionIsDisplayed() {
        return burgerConstructorSection.isDisplayed();
    }

    public void clickTabInBurgerIngredientSection(String tab) {
        tabsInBurgerIngredients.stream().filter(t -> t.getText().equals(tab)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Вкладка '"+tab+"' не найдена"))
                .click();
    }

    public boolean tabInBurgerIngredientSectionIsDisplayed(String tab) {
        return tabsInBurgerIngredients.stream().filter(t -> t.getText().equals(tab)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Вкладка '"+tab+"' не найдена"))
                .isDisplayed();
    }

    public boolean waitTabInBurgerIngredientSectionActive(String tab, long timeoutSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(driver -> {
            WebElement tabElement = tabsInBurgerIngredients.stream()
                    .filter(t -> t.getText().equals(tab))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Вкладка '" + tab + "' не найдена"));

            WebElement parentTab = tabElement.findElement(By.xpath(".."));

            return parentTab.getAttribute("class").contains("tab_tab_type_current");
        });
    }

    public void dragAndDropIngredient(String ingredientName) {
        By ingredientLocator = By.cssSelector("img[alt='"+ingredientName+"']");
        WebElement ingredient = wait.until(ExpectedConditions.visibilityOfElementLocated(ingredientLocator));

        WebElement constructorTarget = wait.until(ExpectedConditions.visibilityOf(constructorTargets.get(0)));

        new Actions(driver)
                .clickAndHold(ingredient)
                .pause(500) // небольшая пауза, чтобы "поднять" ингредиент
                .moveToElement(constructorTarget)
                .pause(500)
                .release()
                .perform();
    }
}