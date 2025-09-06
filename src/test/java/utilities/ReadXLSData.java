package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class ReadXLSData {

	@DataProvider(name = "FormCalendarData")
	public Object[][] getFormAndCalendarData() throws IOException {
		File file = new File(
				System.getProperty("user.dir") + "/src/test/resources/testdata/GUIElementsFormCalendarTest.xlsx");
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(fis);

		Sheet formSheet = workbook.getSheet("FormData");
		Sheet calSheet = workbook.getSheet("CalendarData");

		int formRows = formSheet.getPhysicalNumberOfRows();
		int calRows = calSheet.getPhysicalNumberOfRows();

		int totalRows = Math.min(formRows, calRows);

		int formCols = 11; // name,email,phone,address,gender,country,colour,animal,dayOfWeek,bookName,productName

		int calCols = 3; // calDay, calMonth, calYear

		Object[][] data = new Object[totalRows - 1][formCols + calCols];
		DataFormatter format = new DataFormatter();

		for (int i = 1; i < totalRows; i++) {
			Row formRow = formSheet.getRow(i);
			Row calRow = calSheet.getRow(i);

			// --- Form data ---
			for (int j = 0; j < formCols; j++) {
				String val = "";
				if (formRow.getCell(j) != null)
					val = format.formatCellValue(formRow.getCell(j)).trim();
				data[i - 1][j] = val;
			}

			// --- Calendar data ---
			for (int k = 0; k < calCols; k++) {
				String val = "";
				if (calRow.getCell(k) != null)
					val = format.formatCellValue(calRow.getCell(k)).trim();
				data[i - 1][formCols + k] = val;
			}
		}

		workbook.close();
		fis.close();
		return data;
	}

	public static List<Map<String, String>> getData(String fileName, String formSheetName, String calSheetName)
			throws IOException {

		File file = new File(System.getProperty("user.dir") + "/src/test/resources/testdata/" + fileName);
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(fis);

		Sheet formSheet = workbook.getSheet(formSheetName);
		Sheet calSheet = workbook.getSheet(calSheetName);

		int formRows = formSheet.getPhysicalNumberOfRows();
		int calRows = calSheet.getPhysicalNumberOfRows();
		int totalRows = Math.min(formRows, calRows);

		Row formHeader = formSheet.getRow(0);
		Row calHeader = calSheet.getRow(0);

		int formCols = formHeader.getPhysicalNumberOfCells();
		int calCols = calHeader.getPhysicalNumberOfCells();

		List<Map<String, String>> allRows = new ArrayList<>();
		DataFormatter format = new DataFormatter();

		for (int i = 1; i < totalRows; i++) {
			Row formRow = formSheet.getRow(i);
			Row calRow = calSheet.getRow(i);

			Map<String, String> rowData = new HashMap<>();

			// --- Form sheet dynamically ---
			for (int j = 0; j < formCols; j++) {
				String key = format.formatCellValue(formHeader.getCell(j)).trim();
				String val = "";
				if (formRow.getCell(j) != null)
					val = format.formatCellValue(formRow.getCell(j)).trim();
				rowData.put(key, val);
			}

			// --- Calendar sheet dynamically ---
			for (int k = 0; k < calCols; k++) {
				String key = format.formatCellValue(calHeader.getCell(k)).trim();
				String val = "";
				if (calRow.getCell(k) != null)
					val = format.formatCellValue(calRow.getCell(k)).trim();
				rowData.put(key, val);
			}

			allRows.add(rowData);
		}

		workbook.close();
		fis.close();
		return allRows;
	}

}
