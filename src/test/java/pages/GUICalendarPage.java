package pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BaseTest;

public class GUICalendarPage {

    private By dateField = By.id("datepicker");
    private By monthYearLocator = By.className("ui-datepicker-title");
    private By nextButton = By.className("ui-datepicker-next");
    private By prevButton = By.className("ui-datepicker-prev");

    public GUICalendarPage() {}

    public void openCalendar() {
        BaseTest.getDriver().findElement(dateField).click();
    }

    public void selectDate(String targetDay, String targetMonth, String targetYear) {
        openCalendar();

        while (true) {
            WebElement monthYearElement = BaseTest.getWait(10).until(
                    ExpectedConditions.visibilityOfElementLocated(monthYearLocator));
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
                BaseTest.getWait(10).until(ExpectedConditions.elementToBeClickable(prevButton)).click();
            } else {
                BaseTest.getWait(10).until(ExpectedConditions.elementToBeClickable(nextButton)).click();
            }
        }

        String day = "//td[not(contains(@class,'ui-datepicker-other-month'))]/a[text()='" 
                + Integer.parseInt(targetDay) + "']";
        BaseTest.getWait(10).until(ExpectedConditions.elementToBeClickable(By.xpath(day))).click();
    }

    public String getSelectedDate() {
        return BaseTest.getDriver().findElement(dateField).getAttribute("value");
    }

    private int getMonthIndex(String month) {
        List<String> months = Arrays.asList("january", "february", "march", "april", "may", "june", "july", "august",
                "september", "october", "november", "december");
        return months.indexOf(month.toLowerCase());
    }
}
