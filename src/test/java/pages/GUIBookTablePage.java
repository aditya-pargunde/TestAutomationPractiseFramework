package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.BaseTest;

public class GUIBookTablePage {

    public GUIBookTablePage() {}

    public void printBookDetails(String bookName) {
        List<WebElement> rows = BaseTest.getDriver()
                .findElements(By.xpath("//table[@name='BookTable']/tbody/tr[position()>1]"));

        for (WebElement row : rows) {
            WebElement bookCell = row.findElement(By.xpath("./td[1]"));
            String currentBook = bookCell.getText();

            if (currentBook.equalsIgnoreCase(bookName)) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                System.out.println("📚BookName: " + cells.get(0).getText() + ", Author: " + cells.get(1).getText()
                        + ", Subject: " + cells.get(2).getText() + ", Price: " + cells.get(3).getText());
                break;
            }
        }
    }
}
