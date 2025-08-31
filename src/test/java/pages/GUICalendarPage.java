package pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GUICalendarPage {

	private WebDriver driver;
	private WebDriverWait wait;

	private By dateField = By.id("datepicker");
	private By monthYearLocator = By.className("ui-datepicker-title");
	private By nextButton = By.className("ui-datepicker-next");
	private By prevButton = By.className("ui-datepicker-prev");

	// Constructor
	public GUICalendarPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void openCalendar() {
		driver.findElement(dateField).click();
	}

	public void selectDate(String targetDay, String targetMonth, String targetYear) {
		openCalendar();

		while (true) {
			WebElement monthYearElement = wait.until(ExpectedConditions.visibilityOfElementLocated(monthYearLocator));
			String displayedText = monthYearElement.getText();
			String[] parts = displayedText.split(" ");
			String displayedMonth = parts[0];
			String displayedYear = parts[1];

			if (displayedMonth.equalsIgnoreCase(targetMonth) && displayedYear.equals(targetYear)) {
				break;
			}

			int displayedYearInt = Integer.parseInt(displayedYear);
			int targetYearInt = Integer.parseInt(targetYear);

			int displayedMonthIndex = getMonthIndex(displayedMonth);
			int targetMonthIndex = getMonthIndex(targetMonth);

			if (displayedYearInt > targetYearInt
					|| (displayedYearInt == targetYearInt && displayedMonthIndex > targetMonthIndex)) {
				wait.until(ExpectedConditions.elementToBeClickable(prevButton)).click();
			} else {
				wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
			}
		}

		String day = "//td[not(contains(@class,'ui-datepicker-other-month'))]/a[text()='" + Integer.parseInt(targetDay)
				+ "']";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(day))).click();
	}

	public String getSelectedDate() {
		return driver.findElement(dateField).getAttribute("value");
	}

	private int getMonthIndex(String month) {
		List<String> months = Arrays.asList("january", "february", "march", "april", "may", "june", "july", "august",
				"september", "october", "november", "december");
		return months.indexOf(month.toLowerCase());
	}
}
