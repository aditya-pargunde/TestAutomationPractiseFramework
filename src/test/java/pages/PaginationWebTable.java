package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PaginationWebTable {

	private WebDriver driver;

	public PaginationWebTable(WebDriver driver) {
		this.driver = driver;
	}

	public void selectProductCheckbox(String productName) {
		boolean found = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		while (true) {
			// Locate all rows except header
			List<WebElement> tableRows = driver
					.findElements(By.xpath("//table[@id='productTable']/tbody/tr[position()>0]"));

			// check for porduct column
			for (WebElement row : tableRows) {
				String currentProduct = row.findElement(By.xpath("./td[2]")).getText();

				// check the product in row
				if (currentProduct.equalsIgnoreCase(productName)) {
					WebElement productCheckbox = row.findElement(By.xpath("./td[4]/input[@type='checkbox']"));

					// if found click checkbox
					if (!productCheckbox.isSelected()) {
						wait.until(ExpectedConditions.elementToBeClickable(productCheckbox)).click();
					}
					Assert.assertTrue(productCheckbox.isSelected(), " " + productName + " checkbox is NOT selected");
					System.out.println("📦 Selected product: " + productName);
					found = true;
					break;
				}
			}

			if (found)
				break;

			try {
				WebElement currentPage = driver.findElement(By.xpath("//ul[@id='pagination']/li/a[@class='active']"));
				WebElement nextPage = currentPage.findElement(By.xpath("./parent::li/following-sibling::li/a"));
				nextPage.click();

				// Wait until table reloads (first row changes)
				wait.until(ExpectedConditions.stalenessOf(tableRows.get(0)));
			} catch (Exception e) {
				System.out.println(" " + productName + " not found in table after checking all pages.");
				break;
			}
		}
	}

	public void clickFooterHome() {
		driver.findElement(By.xpath("//div[@id='PageList1']//a[text()='Home']")).click();

		// Wait for full page load (document.readyState = complete)
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
				.equals("complete"));
	}
}
