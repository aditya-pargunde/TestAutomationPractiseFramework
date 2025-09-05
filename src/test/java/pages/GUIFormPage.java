package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

import base.BaseTest;

public class GUIFormPage {

    private Properties locators;

    public GUIFormPage() throws IOException {
        locators = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/configFile/locators.properties");
        locators.load(fis);
        fis.close();
    }

    public void enterName(String name) {
        BaseTest.getDriver().findElement(By.id(locators.getProperty("nameField"))).sendKeys(name);
    }

    public void enterEmail(String email) {
        BaseTest.getDriver().findElement(By.id(locators.getProperty("emailField"))).sendKeys(email);
    }

    public void enterPhone(String phone) {
        BaseTest.getDriver().findElement(By.id(locators.getProperty("phoneField"))).sendKeys(phone);
    }

    public void enterAddress(String address) {
        BaseTest.getDriver().findElement(By.id(locators.getProperty("addressField"))).sendKeys(address);
    }

    public void selectGender(String gender) {
        BaseTest.getDriver().findElement(By.xpath("//input[@value='" + gender.toLowerCase() + "']")).click();
    }

    public void selectDay(String dayOfWeek) {
        BaseTest.getDriver().findElement(By.xpath(locators.getProperty("dayField"))).sendKeys(dayOfWeek);
    }

    public void selectCountry(String country) {
        BaseTest.getDriver().findElement(By.id(locators.getProperty("countryField"))).sendKeys(country);
    }

    public void enterColour(String colour) {
        BaseTest.getDriver().findElement(By.id(locators.getProperty("colourField"))).sendKeys(colour);
    }

    public void enterAnimal(String animal) {
        BaseTest.getDriver().findElement(By.id(locators.getProperty("animalField"))).sendKeys(animal);
    }
}
