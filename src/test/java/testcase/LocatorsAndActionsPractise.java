package testcase;

import java.io.IOException;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.GUICalendarPage;
import pages.GUIFormPage;
import utilities.ReadXLSData;

public class LocatorsAndActionsPractise extends BaseTest {

	@Test(dataProvider = "FormData", dataProviderClass = ReadXLSData.class)
	public void testForm(String name, String email, String phone, String address, String gender, String country,
			String colour, String animal, String day) throws IOException, InterruptedException {

		GUIFormPage formPage = new GUIFormPage(driver);

		formPage.enterName(name);
		formPage.enterEmail(email);
		formPage.enterPhone(phone);
		formPage.enterAddress(address);
		formPage.selectGender(gender);
		formPage.selectDay(day); // <-- new column usage
		formPage.selectCountry(country);
		formPage.enterColour(colour);
		formPage.enterAnimal(animal);

		System.out.println("✅ Test Run successful for -> " + "Name: " + name + " | Email: " + email + " | Phone: "
				+ phone + " | Address: " + address + " | Gender: " + gender + " | Day: " + day + " | Country: "
				+ country + " | Colour: " + colour + " | Animal: " + animal);
	}

	@Test(dataProvider = "CalendarData", dataProviderClass = ReadXLSData.class, dependsOnMethods = "testForm")
	public void testCalendar(String day, String month, String year) throws IOException, InterruptedException {

		GUICalendarPage calendarPage = new GUICalendarPage(driver);

		calendarPage.openCalendar();
		calendarPage.selectDate(day, month, year);

		System.out.println("✅ Calendar selected successfully: " + day + "-" + month + "-" + year);
	}
}
