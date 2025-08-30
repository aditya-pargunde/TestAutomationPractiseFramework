package pages;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class mouseActionsAtRightTabs {
    private WebDriver driver;
    private Alert alert;
    private JavascriptExecutor js;
    private Actions a;
    private WebDriverWait wait;

    public mouseActionsAtRightTabs(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.a = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	// Tabs
	public void wikiSearch() {
		driver.findElement(By.className("wikipedia-search-input")).sendKeys("Search");
		driver.findElement(By.className("wikipedia-search-button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("wikipedia-search-results")));

	}

	// Alerts & Popups

	// simple laert
	public void simpleAlert() {
		driver.findElement(By.id("alertBtn")).click();
		alert = driver.switchTo().alert();
		System.out.println("simple Alert - " + alert.getText());
		alert.accept();
	}

	// confirmation alert
	public void ConfirmationAlert() {
		driver.findElement(By.id("confirmBtn")).click();

		alert = driver.switchTo().alert();
		System.out.println("simple Alert - " + alert.getText());
		alert.accept();
	}

	// confirmation alert
	public void promptAlert() {
		WebElement promptAlertButton = driver.findElement(By.id("promptBtn"));
		promptAlertButton.click();
		alert = driver.switchTo().alert();
		String userName = "Baburao Ganpatrao Aapte";
		alert.sendKeys(userName);
		System.out.println("simple Alert - " + alert.getText());

		// cancelling the alert
		alert.dismiss();
		String promptAlertMessage = driver.findElement(By.id("demo")).getText();
		Assert.assertEquals(promptAlertMessage, "User cancelled the prompt.");

		// confirming the alert
		promptAlertButton.click();
		alert = driver.switchTo().alert();
		alert.sendKeys(userName);
		alert.accept();
		String promptAcceptedMessage = driver.findElement(By.id("demo")).getText();
		Assert.assertEquals(promptAcceptedMessage, "Hello " + userName + "! How are you today?");
	}
	
	public void copyText()
	{
		WebElement CopyTextButton = driver.findElement(By.xpath("//*[@id=\"HTML10\"]/div[1]/button"));
		js.executeScript("arguments[0].scrollIntoView(true);", CopyTextButton);
		WebElement Field1= driver.findElement(By.id("field1"));
		WebElement Field2= driver.findElement(By.id("field2"));
		a.doubleClick(CopyTextButton).perform();
		Assert.assertEquals(Field1.getAttribute("value"), Field2.getAttribute("value"));
	}
	
	public void dragAndDrop() {
		WebElement source = driver.findElement(By.id("draggable"));
		WebElement target = driver.findElement(By.id("droppable"));
		a.dragAndDrop(source, target).perform();
	}
	
	public void priceRangeSlider() {
		WebElement leftSlider = driver.findElement(By.xpath("//span[contains(@class,'ui-slider-handle')][1]"));  
		a = new Actions(driver);

		// Move slider 50 pixels to the right
		a.clickAndHold(leftSlider).moveByOffset(50, 0).release().perform();
		
		WebElement rightSlider = driver.findElement(By.xpath("//span[contains(@class,'ui-slider-handle')][2]"));  
		a = new Actions(driver);

		// Move slider 30 pixels to the left
		a.clickAndHold(rightSlider).moveByOffset(-30, 0).release().perform();


	}

	public void brokenLinks() {
		
		//scroll to broken links section
	    WebElement brokenLinks = driver.findElement(By.id("broken-links"));
	    js.executeScript("arguments[0].scrollIntoView(true);", brokenLinks);

	    // get list of all broken links
	    List<WebElement> allBrokenLinksList = driver.findElements(By.xpath("//*[@id='broken-links']/a"));

	    System.out.println("Total links: " + allBrokenLinksList.size());

	    for (WebElement link : allBrokenLinksList) {
	        String url = link.getAttribute("href");
	        verifyLink(url);
	    }
	}
	
	public void verifyLink(String linkUrl) {
	    try {
	        URL url = new URL(linkUrl);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        httpConn.setConnectTimeout(5000);
	        httpConn.connect();

	        int responseCode = httpConn.getResponseCode();

	        if (responseCode >= 400) {
	            System.out.println(linkUrl + " ❌ BROKEN (HTTP code: " + responseCode + ")");
	        } else {
	            System.out.println(linkUrl + " ✅ VALID (HTTP code: " + responseCode + ")");
	        }
	    } catch (Exception e) {
	        System.out.println(linkUrl + " ⚠️ Error: " + e.getMessage());
	    }
	}
}
