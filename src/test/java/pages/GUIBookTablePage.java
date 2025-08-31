package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GUIBookTablePage {

	private WebDriver driver;

	public GUIBookTablePage(WebDriver driver) {
		this.driver = driver;
	}

	public void printBookDetails(String bookName) {
		// Locate all rows except header
		List<WebElement> rows = driver.findElements(By.xpath("//table[@name='BookTable']/tbody/tr[position()>1]"));

		for (WebElement row : rows) {
			WebElement bookCell = row.findElement(By.xpath("./td[1]")); // first column is BookName
			String currentBook = bookCell.getText();

			if (currentBook.equalsIgnoreCase(bookName)) {
				List<WebElement> cells = row.findElements(By.tagName("td"));
				System.out.println("📚BookName: " + cells.get(0).getText() + ", Author: " + cells.get(1).getText()
						+ ", Subject: " + cells.get(2).getText() + ", Price: " + cells.get(3).getText());
				break; // stop after finding the book
			}
		}
	}
}
