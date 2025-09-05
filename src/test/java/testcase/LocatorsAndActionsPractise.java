package testcase;

import java.io.IOException;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.GUIBookTablePage;
import pages.GUICalendarPage;
import pages.GUIFormPage;
import pages.PaginationWebTable;
import pages.mouseActionsAtRightTabs;
import utilities.ReadXLSData;
import utilities.ExtentTestNGListener;

@Listeners(ExtentTestNGListener.class)
public class LocatorsAndActionsPractise extends BaseTest {

    @Test(dataProviderClass = ReadXLSData.class, dataProvider = "FormCalendarData", threadPoolSize = 4)
    public void fillFormAndSelectDate(String name, String email, String phone, String address, String gender,
                                      String country, String colour, String animal, String dayOfWeek,
                                      String bookName, String productName, String calDay,
                                      String calMonth, String calYear)
                                      throws IOException, InterruptedException {
        System.out.println("Thread: " + Thread.currentThread().getId() + " | Browser started for user: " + name);

        GUIFormPage formPage = new GUIFormPage();
        GUICalendarPage calendarPage = new GUICalendarPage();
        GUIBookTablePage bookTablePage = new GUIBookTablePage();
        PaginationWebTable paginationTable = new PaginationWebTable();
        mouseActionsAtRightTabs mouseActions = new mouseActionsAtRightTabs();

        // --- Fill Form ---
        formPage.enterName(name);
        formPage.enterEmail(email);
        formPage.enterPhone(phone);
        formPage.enterAddress(address);
        formPage.selectGender(gender);
        formPage.selectCountry(country);
        formPage.enterColour(colour);
        formPage.enterAnimal(animal);
        formPage.selectDay(dayOfWeek);

        // --- Calendar ---
        calendarPage.selectDate(calDay, calMonth, calYear);
        System.out.println("✅ Test Run successful for -> " 
            + "Name: " + name + " | Email: " + email + " | Phone: " + phone
            + " | Address: " + address + " | Gender: " + gender + " | Country: " + country 
            + " | Colour: " + colour + " | Animal: " + animal + " | Day: " + dayOfWeek 
            + " | Calendar Date: " + calDay + "-" + calMonth + "-" + calYear 
            + " | Book: " + bookName + " | Product: " + productName);

        // --- Book Table ---
        bookTablePage.printBookDetails(bookName);

        // --- Pagination Table ---
        paginationTable.selectProductCheckbox(productName);
        paginationTable.clickFooterHome();

        // --- Mouse Actions ---
        mouseActions.wikiSearch();
        mouseActions.simpleAlert();
        mouseActions.ConfirmationAlert();
        mouseActions.promptAlert(name);
        mouseActions.copyText();
        mouseActions.dragAndDrop();
        mouseActions.priceRangeSlider();
        mouseActions.brokenLinks();
    }
}
