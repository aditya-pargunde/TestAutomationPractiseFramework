package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static Properties config = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/testdata.properties");
            config.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    // ✅ Helper methods
    public static WebDriverWait getWait(long seconds) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
    }

    public static JavascriptExecutor getJS() {
        return (JavascriptExecutor) getDriver();
    }

    public static Actions getActions() {
        return new Actions(getDriver());
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = config.getProperty("browser", "chrome").toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(config.getProperty("headless", "false"));
        WebDriver webDriver;

        switch (browser) {
        case "firefox":
            FirefoxOptions ffOptions = new FirefoxOptions();
            if (isHeadless) ffOptions.addArguments("--headless");
            webDriver = new FirefoxDriver(ffOptions);
            break;

        case "edge":
            EdgeOptions edgeOptions = new EdgeOptions();
            if (isHeadless) edgeOptions.addArguments("--headless=new");
            webDriver = new EdgeDriver(edgeOptions);
            break;
            
        case "safari":
            // Safari doesn’t support headless mode
            webDriver = new SafariDriver();
            break;

        case "chrome":
        default:
            ChromeOptions chromeOptions = new ChromeOptions();
            if (isHeadless) chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--start-maximized");
            webDriver = new ChromeDriver(chromeOptions);
            break;
        }

        driver.set(webDriver);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();

        String url = config.getProperty("baseURL");
        getDriver().get(url);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
