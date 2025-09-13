package framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {

    /**
     * Reads an Excel file and returns a map of rowNumber -> rowData
     * Each rowData is a map with column headers as keys
     *
     * @param filePath  Path to the Excel file
     * @param sheetName Name of the sheet to read
     * @return Map<String, Map<String, String>>
     * @throws IOException if file not found or can't be read
     */
    public static Map<String, Map<String, String>> readExcel(String filePath, String sheetName) throws IOException {
        Map<String, Map<String, String>> data = new LinkedHashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " not found in " + filePath);
            }

            Iterator<Row> rowIterator = sheet.iterator();

            // First row = headers
            List<String> headers = new ArrayList<>();
            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue());
                }
            }

            // Remaining rows = data
            int rowNumber = 1; // start numbering at 1
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, String> rowData = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowData.put(headers.get(i), getCellValueAsString(cell));
                }
                data.put(String.valueOf(rowNumber), rowData);
                rowNumber++;
            }
        }
        return data;
    }

    private static String getCellValueAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            case BLANK -> "";
            default -> "";
        };
    }
}
