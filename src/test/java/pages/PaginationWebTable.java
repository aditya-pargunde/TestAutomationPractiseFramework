package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseTest;

public class PaginationWebTable {

	public PaginationWebTable() {
	}

	public void selectProductCheckbox(String productName) {
		boolean found = false;
		WebDriverWait wait = BaseTest.getWait(5);

		while (true) {
			List<WebElement> tableRows = BaseTest.getDriver()
					.findElements(By.xpath("//table[@id='productTable']/tbody/tr[position()>0]"));

			for (WebElement row : tableRows) {
				String currentProduct = row.findElement(By.xpath("./td[2]")).getText();

				if (currentProduct.equalsIgnoreCase(productName)) {
					WebElement productCheckbox = row.findElement(By.xpath("./td[4]/input[@type='checkbox']"));

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
				WebElement currentPage = BaseTest.getDriver()
						.findElement(By.xpath("//ul[@id='pagination']/li/a[@class='active']"));
				WebElement nextPage = currentPage.findElement(By.xpath("./parent::li/following-sibling::li/a"));
				nextPage.click();
				wait.until(ExpectedConditions.stalenessOf(tableRows.get(0)));
			} catch (Exception e) {
				System.out.println(" " + productName + " not found in table after checking all pages.");
				break;
			}
		}
	}

	public void clickFooterHome() {
		BaseTest.getDriver().findElement(By.xpath("//div[@id='PageList1']//a[text()='Home']")).click();
		WebDriverWait wait = BaseTest.getWait(10);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
				.equals("complete"));
	}
}
