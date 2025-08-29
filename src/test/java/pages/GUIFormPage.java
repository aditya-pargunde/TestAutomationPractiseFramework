package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class GUIFormPage {

	WebDriver driver;
	Properties locators;

	@FindBy(id = "name")
	private WebElement nameInput;

	@FindBy(id = "email")
	private WebElement emailInput;

	@FindBy(id = "phone")
	private WebElement phoneInput;

	@FindBy(id = "textarea")
	private WebElement addressInput;

	@FindBy(id = "colors")
	private WebElement colourInput;

	@FindBy(id = "animals")
	private WebElement animalInput;

	@FindBy(id = "male")
	private WebElement maleRadio;

	@FindBy(id = "female")
	private WebElement femaleRadio;

	@FindBy(xpath = "//input[@type='checkbox']")
	private List<WebElement> dayCheckbox;

	@FindBy(id = "country")
	private WebElement countryDropdown;

	public GUIFormPage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		locators = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/configFile/locators.properties");
		locators.load(fis);
		fis.close();
	}

	public void enterName(String name) throws InterruptedException {
		nameInput.sendKeys(name);

	}

	public void enterEmail(String email) throws InterruptedException {
		emailInput.sendKeys(email);

	}

	public void enterPhone(String phone) throws InterruptedException {
		phoneInput.sendKeys(phone);

	}

	public void enterAddress(String address) throws InterruptedException {
		addressInput.sendKeys(address);

	}

	public void selectGender(String gender) throws InterruptedException {
		if (gender.equalsIgnoreCase("male"))
			maleRadio.click();
		else if (gender.equalsIgnoreCase("female"))
			femaleRadio.click();

	}

	public void selectDay(String day) throws InterruptedException {
		for (WebElement cb : dayCheckbox) {
			if (cb.getAttribute("value").equalsIgnoreCase(day)) {
				if (!cb.isSelected()) {
					cb.click();
				}
				break;
			}

		}

	}

	public void selectCountry(String country) throws InterruptedException {
		Select select = new Select(countryDropdown);
		select.selectByVisibleText(country);
		Thread.sleep(1000);
	}

	public void enterColour(String colour) throws InterruptedException {
		Select select = new Select(colourInput);
		select.selectByVisibleText(colour);
		Thread.sleep(1000);
	}

	public void enterAnimal(String animal) throws InterruptedException {
		Select select = new Select(animalInput);
		select.selectByVisibleText(animal);
		Thread.sleep(1000);
	}

}
