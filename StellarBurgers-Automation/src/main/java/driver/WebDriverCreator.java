package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.time.Duration.ofSeconds;

public class WebDriverCreator {

    public static WebDriver createWebDriver() {
        String browser = System.getProperty("browser", "chrome");
        WebDriver webDriver = createWebDriver(browser);
        webDriver.manage().timeouts().implicitlyWait(ofSeconds(3));
        return webDriver;
    }

    private static WebDriver createWebDriver(String browser) {
        switch (browser) {
            case "firefox":
                return createFirefoxDriver();
            case "chrome":
                return createChromeDriver();
            default:
                throw new IllegalArgumentException("Неподдерживаемый браузер: " + browser);
        }
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        return new FirefoxDriver();
    }
}