package StepDefinitions;

import org.openqa.selenium.WebDriver;

import java.util.List;
import base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.GUIFormPage;
import pages.PaginationWebTable;
import pages.GUIBookTablePage;
import pages.GUICalendarPage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pages.mouseActionsAtRightTabs;
import utilities.ReadXLSData;

public class FormSteps extends BaseTest {
	@Given("i opened the automation practise website")
    public void i_opened_the_automation_practise_website()  {
		setUp();
	}

	 @When("I fill the form and calendar with Excel data")
	    public void I_fill_the_form_and_calendar_with_Excel_data() throws IOException {
		 // Read all rows from Excel
		 List<Map<String, String>> allRows = ReadXLSData.getData("GUIElementsFormCalendarTest.xlsx", "FormData", "CalendarData");

        Set<String> executedUsers = new HashSet<>();
     

	    GUIFormPage formPage = new GUIFormPage();
	    GUICalendarPage calendarPage = new GUICalendarPage();
	    GUIBookTablePage bookTablePage = new GUIBookTablePage();
	    PaginationWebTable paginationTable = new PaginationWebTable();

	    for (Map<String, String> row : allRows) {
            // Generate a unique key to avoid duplicates
            String uniqueKey = row.get("name") + "_" + row.get("email");
            if (executedUsers.contains(uniqueKey)) continue;
            executedUsers.add(uniqueKey);

            // --- Fill Form ---
            formPage.enterName(row.get("name"));
            formPage.enterEmail(row.get("email"));
            formPage.enterPhone(row.get("phone"));
            formPage.enterAddress(row.get("address"));
            formPage.selectGender(row.get("gender"));
            formPage.selectCountry(row.get("country"));
            formPage.enterColour(row.get("colour"));
            formPage.enterAnimal(row.get("animal"));
            formPage.selectDay(row.get("dayOfWeek"));

         // --- Calendar ---
            calendarPage.selectDate(row.get("calDay"), row.get("calMonth"), row.get("calYear"));

            // --- Book Table ---
            bookTablePage.printBookDetails(row.get("bookName"));

            // --- Pagination Table ---
            paginationTable.selectProductCheckbox(row.get("productName"));
            paginationTable.clickFooterHome();
	}
	}

	 @And("I perform mouse actions and alerts for all users")
	 public void I_perform_mouse_actions_and_alerts_for_all_users() throws IOException {
	     // Get all Excel rows
	     List<Map<String, String>> allRows = ReadXLSData.getData("GUIElementsFormCalendarTest.xlsx", "FormData", "CalendarData");

	     mouseActionsAtRightTabs mouseActions = new mouseActionsAtRightTabs();

	     for (Map<String, String> row : allRows) {
	         String name = row.get("name");  // Use Excel name for alerts

	         // --- Mouse Actions ---
	         mouseActions.wikiSearch();
	         mouseActions.simpleAlert();
	         mouseActions.ConfirmationAlert();
	         mouseActions.promptAlert(name);
	         mouseActions.copyText();
	         mouseActions.dragAndDrop();
	         mouseActions.priceRangeSlider();
	         mouseActions.brokenLinks();
	     }
	 }
	
	@Then("Browser will close")
	public void Browser_will_close()
	{
		tearDown();
	}

}
