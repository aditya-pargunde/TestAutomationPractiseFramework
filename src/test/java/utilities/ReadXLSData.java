package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class ReadXLSData {

    @DataProvider(name = "FormData")
    public Object[][] getFormData() throws IOException {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/testdata/FormAndCalendarTest.xlsx");
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fis);

        Sheet formSheet = workbook.getSheet("FormData");
        int totalRows = formSheet.getPhysicalNumberOfRows();
        int totalCols = formSheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[totalRows - 1][totalCols];
        DataFormatter format = new DataFormatter();

        for (int i = 1; i < totalRows; i++) {
            Row row = formSheet.getRow(i);
            for (int j = 0; j < totalCols; j++) {
                data[i - 1][j] = format.formatCellValue(row.getCell(j));
            }
        }

        workbook.close();
        fis.close();
        return data;
    }

    @DataProvider(name = "CalendarData")
    public Object[][] getCalendarData() throws IOException {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/testdata/FormAndCalendarTest.xlsx");
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fis);

        Sheet dateSheet = workbook.getSheet("CalendarData");
        int totalRows = dateSheet.getPhysicalNumberOfRows();
        int totalCols = dateSheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[totalRows - 1][totalCols];
        DataFormatter format = new DataFormatter();

        for (int i = 1; i < totalRows; i++) {
            Row row = dateSheet.getRow(i);
            for (int j = 0; j < totalCols; j++) {
                data[i - 1][j] = format.formatCellValue(row.getCell(j));
            }
        }

        workbook.close();
        fis.close();
        return data;
    }
}
