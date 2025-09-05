package pages;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import base.BaseTest;

public class mouseActionsAtRightTabs {
    private Alert alert;

    public mouseActionsAtRightTabs() {}

    // Tabs
    public void wikiSearch() {
        BaseTest.getDriver().findElement(By.className("wikipedia-search-input")).sendKeys("Search");
        BaseTest.getDriver().findElement(By.className("wikipedia-search-button")).click();
        BaseTest.getWait(5).until(ExpectedConditions.visibilityOfElementLocated(By.className("wikipedia-search-results")));
    }

    // simple alert
    public void simpleAlert() {
        BaseTest.getDriver().findElement(By.id("alertBtn")).click();
        alert = BaseTest.getDriver().switchTo().alert();
        System.out.println("Simple Alert OK Clicked - " + alert.getText());
        alert.accept();
    }

    // confirmation alert
    public void ConfirmationAlert() {
        WebElement confirmAlertButton = BaseTest.getDriver().findElement(By.id("confirmBtn"));
        confirmAlertButton.click();
        alert = BaseTest.getDriver().switchTo().alert();
        System.out.println("Confirmation Alert Text - " + alert.getText());
        alert.dismiss();

        WebElement confirmAlertCancelMessage = BaseTest.getDriver().findElement(By.xpath("//p[@id='demo']"));
        BaseTest.getWait(5).until(ExpectedConditions.visibilityOf(confirmAlertCancelMessage));
        Assert.assertEquals(confirmAlertCancelMessage.getText(), "You pressed Cancel!");
        System.out.println("Confirmation Alert Cancelled - " + confirmAlertCancelMessage.getText());

        confirmAlertButton.click();
        alert = BaseTest.getDriver().switchTo().alert();
        alert.accept();
        WebElement confirmAlertSuccessMessage = BaseTest.getDriver().findElement(By.xpath("//p[@id='demo']"));
        BaseTest.getWait(5).until(ExpectedConditions.visibilityOf(confirmAlertSuccessMessage));
        Assert.assertEquals(confirmAlertSuccessMessage.getText(), "You pressed OK!");
        System.out.println("Confirmation Alert Submitted - " + confirmAlertSuccessMessage.getText());
    }

    // prompt alert
    public void promptAlert(String name) {
        WebElement promptAlertButton = BaseTest.getDriver().findElement(By.id("promptBtn"));
        promptAlertButton.click();
        alert = BaseTest.getDriver().switchTo().alert();
        alert.sendKeys(name);
        System.out.println("Prompt Alert - " + alert.getText());
        alert.dismiss();

        WebElement promptCancelledMessage = BaseTest.getDriver().findElement(By.id("demo"));
        BaseTest.getWait(5).until(ExpectedConditions.visibilityOf(promptCancelledMessage));
        Assert.assertEquals(promptCancelledMessage.getText(), "User cancelled the prompt.");
        System.out.println("Prompt Alert Cancelled - " + promptCancelledMessage.getText());

        promptAlertButton.click();
        alert = BaseTest.getDriver().switchTo().alert();
        alert.sendKeys(name);
        alert.accept();
        WebElement promptAcceptedMessage = BaseTest.getDriver().findElement(By.id("demo"));
        Assert.assertEquals(promptAcceptedMessage.getText(), "Hello " + name + "! How are you today?");
        System.out.println("Prompt Alert Submitted - " + promptAcceptedMessage.getText());
    }

    public void copyText() {
        WebElement CopyTextButton = BaseTest.getDriver().findElement(By.xpath("//*[@id=\"HTML10\"]/div[1]/button"));
        BaseTest.getJS().executeScript("arguments[0].scrollIntoView(true);", CopyTextButton);
        WebElement Field1 = BaseTest.getDriver().findElement(By.id("field1"));
        WebElement Field2 = BaseTest.getDriver().findElement(By.id("field2"));
        BaseTest.getActions().doubleClick(CopyTextButton).perform();
        BaseTest.getWait(5).until(ExpectedConditions.attributeToBe(Field2, "value", "Hello World!"));
        Assert.assertEquals(Field1.getAttribute("value"), Field2.getAttribute("value"));
    }

    public void dragAndDrop() {
        WebElement source = BaseTest.getDriver().findElement(By.id("draggable"));
        WebElement target = BaseTest.getDriver().findElement(By.id("droppable"));
        BaseTest.getActions().dragAndDrop(source, target).perform();
    }

    public void priceRangeSlider() {
        WebElement leftSlider = BaseTest.getDriver().findElement(By.xpath("//span[contains(@class,'ui-slider-handle')][1]"));
        BaseTest.getActions().clickAndHold(leftSlider).moveByOffset(50, 0).release().perform();

        WebElement rightSlider = BaseTest.getDriver().findElement(By.xpath("//span[contains(@class,'ui-slider-handle')][2]"));
        BaseTest.getActions().clickAndHold(rightSlider).moveByOffset(-30, 0).release().perform();
    }

    public void brokenLinks() {
        WebElement brokenLinks = BaseTest.getDriver().findElement(By.id("broken-links"));
        BaseTest.getJS().executeScript("arguments[0].scrollIntoView(true);", brokenLinks);

        List<WebElement> allBrokenLinksList = BaseTest.getDriver().findElements(By.xpath("//*[@id='broken-links']/a"));
        System.out.println("Total links: " + allBrokenLinksList.size());

        for (WebElement link : allBrokenLinksList) {
            String url = link.getAttribute("href");
            verifyLink(url);
        }
    }

    public static void verifyLink(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode >= 400) {
                System.out.println(url + " is a broken link.");
            } else {
                System.out.println(url + " is a valid link.");
            }
        } catch (Exception e) {
            System.out.println(url + " could not be verified: " + e.getMessage());
        }
    }
}
