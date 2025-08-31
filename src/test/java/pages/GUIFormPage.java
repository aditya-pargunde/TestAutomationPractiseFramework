package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class GUIFormPage {

	private Properties locators;
	private WebDriver driver;

	// Constructor that accepts WebDriver
	public GUIFormPage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		locators = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/configFile/locators.properties");
		locators.load(fis);
		fis.close();
	}

	public void enterName(String name) {
		driver.findElement(By.id(locators.getProperty("nameField"))).sendKeys(name);
	}

	public void enterEmail(String email) {
		driver.findElement(By.id(locators.getProperty("emailField"))).sendKeys(email);
	}

	public void enterPhone(String phone) {
		driver.findElement(By.id(locators.getProperty("phoneField"))).sendKeys(phone);
	}

	public void enterAddress(String address) {
		driver.findElement(By.id(locators.getProperty("addressField"))).sendKeys(address);
	}

	public void selectGender(String gender) {
		driver.findElement(By.xpath("//input[@value='" + gender.toLowerCase() + "']")).click();
	}

	public void selectDay(String dayOfWeek) {
		driver.findElement(By.xpath(locators.getProperty("dayField"))).sendKeys(dayOfWeek);
	}

	public void selectCountry(String country) {
		driver.findElement(By.id(locators.getProperty("countryField"))).sendKeys(country);
	}

	public void enterColour(String colour) {
		driver.findElement(By.id(locators.getProperty("colourField"))).sendKeys(colour);
	}

	public void enterAnimal(String animal) {
		driver.findElement(By.id(locators.getProperty("animalField"))).sendKeys(animal);
	}
}
