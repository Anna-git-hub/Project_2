import clients.ApiClient;
import extensions.BrowserWithApiUserExtension;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;


@Epic("Регистрация и авторизация")
@Feature("Авторизация пользователя")
public class UserTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private ProfileAccountPage profileAccountPage;
    private ConstructorPage constructorPage;

    private BasePage basePage;
    protected ApiClient apiClient;
    protected String email;
    protected String password;
    protected WebDriverWait wait;

    @RegisterExtension
    public BrowserWithApiUserExtension browserWithApiUserExtension = new BrowserWithApiUserExtension();

    private <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    @BeforeEach
    public void setUp() {
        driver = browserWithApiUserExtension.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        this.email = browserWithApiUserExtension.getEmail();
        this.password = browserWithApiUserExtension.getPass();
        this.apiClient = new ApiClient();

        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        profileAccountPage = new ProfileAccountPage(driver);
        constructorPage = new ConstructorPage(driver);
        profilePage = new ProfilePage(driver);
        basePage = new BasePage(driver);

        loginPage.open();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();

    }

    @Test
    @Story("Выход из аккаунта")
    @DisplayName("Пользователь может выйти из аккаунта")
    @Severity(SeverityLevel.NORMAL)
    public void testLogoutSuccessfully(){
        basePage.clickLinkByText("Личный Кабинет");
        assertTrue(profileAccountPage.waitForProfileAccountPage());
        assertTrue(profilePage.isProfilePageOpen());

        profileAccountPage.clickBtnByText("Выход");

        assertTrue(loginPage.waitForLoginPage());
        assertTrue(loginPage.isLoginPageOpen());
    }

    @Test
    @Story("Переход в личный кабинет")
    @DisplayName("Переход в личный кабинет по ссылке")
    @Severity(SeverityLevel.NORMAL)
    public void testRedirectToProfileWithUrl() {
        profileAccountPage.open();

        assertTrue(profileAccountPage.waitForProfileAccountPage());
        assertTrue(profileAccountPage.isProfileAccountPageOpen());
        assertTrue(profileAccountPage.isProfileBtnIsDisplayed());
    }

    @Test
    @Story("Переход в личный кабинет")
    @DisplayName("Переход в личный кабинет по кнопке")
    @Severity(SeverityLevel.NORMAL)
    public void testRedirectToProfileWithBtn(){
        basePage.clickLinkByText("Личный Кабинет");
        profileAccountPage.clickProfileBtn();

        assertTrue(profileAccountPage.waitForProfileAccountPage());
        assertTrue(profileAccountPage.isProfileAccountPageOpen());
        assertTrue(profileAccountPage.isProfileBtnIsDisplayed());
    }

    @Test
    @Story("Конструктор")
    @DisplayName("Переход в раздел “Конструктор” по кнопке")
    @Severity(SeverityLevel.NORMAL)
    public void testRedirectToConstructorWithBtn(){
        basePage.clickLinkByText("Конструктор");
        assertTrue(constructorPage.isBurgerIngredientsSectionIsDisplayed());
        assertTrue(constructorPage.isBurgerConstructorSectionIsDisplayed());
    }

    @Test
    @Story("Конструктор")
    @DisplayName("Переход по вкладкам в разделе 'Соберите бургер'")
    @Severity(SeverityLevel.NORMAL)
    public void testSectionBurgerIngredients(){
        basePage.clickLinkByText("Конструктор");

        assertTrue(constructorPage.tabInBurgerIngredientSectionIsDisplayed("Начинки"));

        constructorPage.clickTabInBurgerIngredientSection("Начинки");
        assertTrue(constructorPage.waitTabInBurgerIngredientSectionActive("Начинки",5));

        constructorPage.clickTabInBurgerIngredientSection("Соусы");
        assertTrue(constructorPage.waitTabInBurgerIngredientSectionActive("Соусы",5));

        constructorPage.clickTabInBurgerIngredientSection("Булки");
        assertTrue(constructorPage.waitTabInBurgerIngredientSectionActive("Булки",5));
    }

    @Test
    @Story("Конструктор")
    @DisplayName("Работа в разделе “Конструктор” — добавление случайных ингредиентов")
    @Severity(SeverityLevel.NORMAL)
    public void testWorkInConstructorPage(){
        basePage.clickLinkByText("Конструктор");
        List<String> buns = Arrays.asList("Флюоресцентная булка R2-D3", "Краторная булка N-200i");
        List<String> sauces = Arrays.asList("Соус Spicy-X", "Соус традиционный галактический", "Соус с шипами Антарианского плоскоходца");
        List<String> fillings = Arrays.asList(
                "Мясо бессмертных моллюсков Protostomia",
                "Говяжий метеорит (отбивная)",
                "Хрустящие минералы галактики",
                "Филе Люминесцентного тетракота",
                "Биокотлета из марсианской Магнолии"
        );

        String randomBun = getRandomElement(buns);
        String randomSauce = getRandomElement(sauces);
        String randomFilling = getRandomElement(fillings);

        constructorPage.clickTabInBurgerIngredientSection("Начинки");
        assertTrue(constructorPage.waitTabInBurgerIngredientSectionActive("Начинки", 5));
        constructorPage.dragAndDropIngredient(randomFilling);

        constructorPage.clickTabInBurgerIngredientSection("Соусы");
        assertTrue(constructorPage.waitTabInBurgerIngredientSectionActive("Соусы", 5));
        constructorPage.dragAndDropIngredient(randomSauce);

        constructorPage.clickTabInBurgerIngredientSection("Булки");
        assertTrue(constructorPage.waitTabInBurgerIngredientSectionActive("Булки", 5));
        constructorPage.dragAndDropIngredient(randomBun);

        assertTrue(constructorPage.isIngredientInConstructor(randomBun), "Булка должна быть в конструкторе");
        assertTrue(constructorPage.isIngredientInConstructor(randomSauce), "Соус должен быть в конструкторе");
        assertTrue(constructorPage.isIngredientInConstructor(randomFilling), "Начинка должна быть в конструкторе");
    }
}