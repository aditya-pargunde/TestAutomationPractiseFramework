package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GUICalendarPage {

    private WebDriver driver;

    private By dateField = By.id("datepicker");
    private By monthYearLocator = By.className("ui-datepicker-title");
    private By nextButton = By.className("ui-datepicker-next");
    private By prevButton = By.className("ui-datepicker-prev");

    public GUICalendarPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCalendar() {
        driver.findElement(dateField).click();
    }

    public void selectDate(String targetDay, String targetMonth, String targetYear) throws InterruptedException {
        openCalendar();

        while (true) {
            String displayedText = driver.findElement(monthYearLocator).getText();
            String[] parts = displayedText.split(" ");
            String displayedMonth = parts[0];
            String displayedYear = parts[1];

            if (displayedMonth.equalsIgnoreCase(targetMonth) && displayedYear.equals(targetYear)) break;

            int displayedYearInt = Integer.parseInt(displayedYear);
            int targetYearInt = Integer.parseInt(targetYear);

            int displayedMonthIndex = getMonthIndex(displayedMonth);
            int targetMonthIndex = getMonthIndex(targetMonth);

            if (displayedYearInt > targetYearInt || 
                (displayedYearInt == targetYearInt && displayedMonthIndex > targetMonthIndex)) {
                driver.findElement(prevButton).click();
            } else {
                driver.findElement(nextButton).click();
            }
            Thread.sleep(500);
        }

        driver.findElement(
        	    By.xpath("//td[not(contains(@class,'ui-datepicker-other-month'))]/a[text()='" + Integer.parseInt(targetDay) + "']"))
        	    .click();

    }

    public String getSelectedDate() {
        String date = driver.findElement(dateField).getAttribute("value");
        System.out.println("Selected Date: " + date);
        return date;
    }

    private int getMonthIndex(String month) {
        switch (month.toLowerCase()) {
            case "january": return 1;
            case "february": return 2;
            case "march": return 3;
            case "april": return 4;
            case "may": return 5;
            case "june": return 6;
            case "july": return 7;
            case "august": return 8;
            case "september": return 9;
            case "october": return 10;
            case "november": return 11;
            case "december": return 12;
            default: return -1;
        }
    }

    private String getMonthName(int month) {
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        return months[month-1];
    }
}
