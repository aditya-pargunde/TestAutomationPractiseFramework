package testcase;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.ReadXLSData;

public class LocatorsAndActionsPractise extends BaseTest {

	@Test(dataProviderClass = ReadXLSData.class, dataProvider = "GUIElementTest")
	public void guiElementsTest(String name, String email, String phone, String address, String gender, String country,
			String colour, String animal, String day, String month, String year) throws InterruptedException {

		// this will enter peronal details from excel sheet
		driver.findElement(By.id(locator.getProperty("nameField"))).sendKeys(name);
		driver.findElement(By.id(locator.getProperty("emailField"))).sendKeys(email);
		driver.findElement(By.id(locator.getProperty("phoneField"))).sendKeys(phone);
		driver.findElement(By.id(locator.getProperty("addressField"))).sendKeys(address);
		driver.findElement(By.id(String.format(locator.getProperty("genderField"), gender.toLowerCase()))).click();
		driver.findElement(By.id(locator.getProperty("countryField"))).sendKeys(country);
		driver.findElement(By.id(locator.getProperty("colurField"))).sendKeys(colour);
		driver.findElement(By.id(locator.getProperty("animalField"))).sendKeys(animal);

		// this will select dates from sheet and enter
		selectDateFromCalendar(driver, day, month, year);

		Thread.sleep(2000);
		// System.out.println("✅ Test Run successful for: " + name + " | Date: " + day +
		// "-" + month + "-" + year);
		System.out.println("✅ Test Run successful for -> " + "Name: " + name + " | Email: " + email + " | Phone: "
				+ phone + " | Address: " + address + " | Gender: " + gender + " | Country: " + country + " | Colour: "
				+ colour + " | Animal: " + animal + " | Date: " + day + "-" + month + "-" + year);
	}
}