package testcase;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ReusableMethods {

	WebDriver driver;

	public ReusableMethods(WebDriver driver) {
		this.driver = driver;
		// THIS IS THE CRUCIAL LINE
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "name")
	WebElement nameField;
	@FindBy(id = "email")
	WebElement emailField;
	@FindBy(id = "phone")
	WebElement phoneField;
	@FindBy(id = "textarea")
	WebElement addressField;
	@FindBy (xpath = "//input[@id='male']") WebElement genderField;
	
	@FindBy(xpath = "//input[@class='form-check-input']")
	List<WebElement> daysCheckboxes;
	

	public void personalDetails(String name, String email, String phone, String address, String gender) {
		nameField.sendKeys(name);
		emailField.sendKeys(email);
		phoneField.sendKeys(phone);
		addressField.sendKeys(address);
		genderField.sendKeys(gender);
		if (gender.equalsIgnoreCase("male")) {
	        genderField.click();
	    }
	
	}
	
	
}