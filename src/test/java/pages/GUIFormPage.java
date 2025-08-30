package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GUIFormPage {

    WebDriver driver;
    Properties locators;

    @FindBy(id = "name") private WebElement nameInput;
    @FindBy(id = "email") private WebElement emailInput;
    @FindBy(id = "phone") private WebElement phoneInput;
    @FindBy(id = "textarea") private WebElement addressInput;
    @FindBy(id = "colors") private WebElement colourInput;
    @FindBy(id = "animals") private WebElement animalInput;
    @FindBy(id = "male") private WebElement maleRadio;
    @FindBy(id = "female") private WebElement femaleRadio;
    @FindBy(xpath = "//input[@type='checkbox']") private List<WebElement> dayCheckbox;
    @FindBy(id = "country") private WebElement countryDropdown;

    public GUIFormPage(WebDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        locators = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/configFile/locators.properties");
        locators.load(fis);
        fis.close();
    }

    public void enterName(String name) { nameInput.sendKeys(name); }
    public void enterEmail(String email) { emailInput.sendKeys(email); }
    public void enterPhone(String phone) { phoneInput.sendKeys(phone); }
    public void enterAddress(String address) { addressInput.sendKeys(address); }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("male")) maleRadio.click();
        else if (gender.equalsIgnoreCase("female")) femaleRadio.click();
    }

    public void selectDay(String day) {
        for (WebElement cb : dayCheckbox) {
            if (cb.getAttribute("value").equalsIgnoreCase(day)) {
                if (!cb.isSelected()) cb.click();
                break;
            }
        }
    }

    public void selectCountry(String country) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(countryDropdown));
        Select select = new Select(countryDropdown);
        select.selectByVisibleText(country);
    }

    public void enterColour(String colour) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(colourInput));
        Select select = new Select(colourInput);
        select.selectByVisibleText(colour);
    }

    public void enterAnimal(String animal) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(animalInput));
        Select select = new Select(animalInput);
        select.selectByVisibleText(animal);
    }
}
