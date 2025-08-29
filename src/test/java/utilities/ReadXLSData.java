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

	@DataProvider(name = "FormCalendarData")
	public Object[][] getFormAndCalendarData() throws IOException {
	    File file = new File(System.getProperty("user.dir") + "/src/test/resources/testdata/FormAndCalendarTest.xlsx");
	    FileInputStream fis = new FileInputStream(file);
	    Workbook workbook = WorkbookFactory.create(fis);

	    // --- Form sheet ---
	    Sheet formSheet = workbook.getSheet("FormData");
	    int formRows = formSheet.getPhysicalNumberOfRows();
	    int formCols = formSheet.getRow(0).getPhysicalNumberOfCells();

	    // --- Calendar sheet ---
	    Sheet calSheet = workbook.getSheet("CalendarData");
	    int calRows = calSheet.getPhysicalNumberOfRows();
	    int calCols = calSheet.getRow(0).getPhysicalNumberOfCells();

	    // assume both sheets have same #rows
	    int totalRows = Math.min(formRows, calRows);

	    Object[][] data = new Object[totalRows - 1][formCols + calCols];
	    DataFormatter format = new DataFormatter();

	    for (int i = 1; i < totalRows; i++) {
	        Row formRow = formSheet.getRow(i);
	        Row calRow = calSheet.getRow(i);

	        // form values
	        for (int j = 0; j < formCols; j++) {
	            data[i - 1][j] = format.formatCellValue(formRow.getCell(j));
	        }

	        // calendar values
	        for (int k = 0; k < calCols; k++) {
	            data[i - 1][formCols + k] = format.formatCellValue(calRow.getCell(k));
	        }
	    }

	    workbook.close();
	    fis.close();
	    return data;
	}
}


