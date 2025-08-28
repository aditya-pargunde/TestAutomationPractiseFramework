package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class ReadXLSData {

    @DataProvider(name = "GUIElementTest")
    public Object[][] getData(Method m) throws EncryptedDocumentException, IOException {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/testdata/GUICalendarTestData.xlsx");
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fis);

        Sheet guiSheet = workbook.getSheet("GUIElementTest");
        int totalRowsGUI = guiSheet.getPhysicalNumberOfRows();
        int totalColumnsGUI = guiSheet.getRow(0).getPhysicalNumberOfCells();

        Sheet dateSheet = workbook.getSheet("DatePickerTest");
        int totalRowsDate = dateSheet.getPhysicalNumberOfRows();
        int totalColumnsDate = dateSheet.getRow(0).getPhysicalNumberOfCells();

        int totalRows = Math.min(totalRowsGUI, totalRowsDate);
        int totalColumns = totalColumnsGUI + totalColumnsDate;

        Object[][] testData = new Object[totalRows - 1][totalColumns];
        DataFormatter format = new DataFormatter();

        for (int i = 1; i < totalRows; i++) {
            for (int j = 0; j < totalColumnsGUI; j++) {
                testData[i - 1][j] = format.formatCellValue(guiSheet.getRow(i).getCell(j));
            }
            for (int k = 0; k < totalColumnsDate; k++) {
                testData[i - 1][totalColumnsGUI + k] = format.formatCellValue(dateSheet.getRow(i).getCell(k));
            }
        }

        workbook.close();
        fis.close();
        return testData;
    }
}
