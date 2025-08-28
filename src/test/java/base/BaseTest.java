package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BaseTest {

	public static WebDriver driver;
	public static Properties test = new Properties();
	public static Properties locator = new Properties();
	public static FileReader frTest;
	public static FileReader frLocator;

	@BeforeMethod
	public void setUp() throws IOException {
		if (driver == null) {
			frTest = new FileReader(System.getProperty("user.dir") + "/src/test/resources/testdata.properties");
			frLocator = new FileReader(
					System.getProperty("user.dir") + "/src/test/resources/configFiles/locators.properties");

			// load test data
			test.load(frTest);

			// load locators separately
			locator.load(frLocator);
		}

		if (test.getProperty("browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (test.getProperty("browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (test.getProperty("browser").equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}

		driver.get(test.getProperty("baseURL"));
		driver.manage().window().maximize();
	}

	public void selectDateFromCalendar(WebDriver driver, String targetDay, String targetMonth, String targetYear)
			throws InterruptedException {
		// Open the calendar widget
		driver.findElement(By.id(locator.getProperty("dateField"))).click();

		// Locators for the month/year display and navigation arrows
		By monthYearLocator = By.className("ui-datepicker-title");
		By nextButton = By.className("ui-datepicker-next");
		By prevButton = By.className("ui-datepicker-prev");

		while (true) {
			String displayedText = driver.findElement(monthYearLocator).getText();
			String[] parts = displayedText.split(" ");
			String displayedMonth = parts[0];
			String displayedYear = parts[1];

			if (displayedMonth.equalsIgnoreCase(targetMonth) && displayedYear.equals(targetYear)) {
				break;
			}

			int displayedYearInt = Integer.parseInt(displayedYear);
			int targetYearInt = Integer.parseInt(targetYear);

			if (displayedYearInt > targetYearInt || (displayedYearInt == targetYearInt
					&& getMonthIndex(displayedMonth) > getMonthIndex(targetMonth))) {
				driver.findElement(prevButton).click();
			} else {
				driver.findElement(nextButton).click();
			}
			Thread.sleep(500);
		}

		// Select the day
		driver.findElement(
				By.xpath("//td[not(contains(@class,'ui-datepicker-other-month'))]/a[text()='" + targetDay + "']"))
				.click();
	}

	private int getMonthIndex(String month) {
		switch (month.toLowerCase()) {
		case "january":
			return 1;
		case "february":
			return 2;
		case "march":
			return 3;
		case "april":
			return 4;
		case "may":
			return 5;
		case "june":
			return 6;
		case "july":
			return 7;
		case "august":
			return 8;
		case "september":
			return 9;
		case "october":
			return 10;
		case "november":
			return 11;
		case "december":
			return 12;
		default:
			return -1;
		}
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
