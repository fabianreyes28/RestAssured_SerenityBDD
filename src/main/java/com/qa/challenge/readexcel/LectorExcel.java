package com.qa.challenge.readexcel;


import com.qa.challenge.exceptions.PropertiesDoesNotLoadException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Java 17 + Apache POI 5.2.3 compatible.
 * Reads Excel into List<Map<header, valueAsString>>
 */
public final class LectorExcel {

    public List<Map<String, String>> getData(String excelFilePath, String sheetName) {
        try (Workbook workbook = getWorkBook(excelFilePath)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new PropertiesDoesNotLoadException(
                        new IllegalArgumentException("Sheet not found: " + sheetName));
            }
            return readSheet(sheet, workbook);
        } catch (InvalidFormatException | IOException e) {
            throw new PropertiesDoesNotLoadException(e);
        }
    }

    public List<Map<String, String>> getData(String excelFilePath, int sheetNumber) {
        try (Workbook workbook = getWorkBook(excelFilePath)) {
            if (sheetNumber < 0 || sheetNumber >= workbook.getNumberOfSheets()) {
                throw new PropertiesDoesNotLoadException(
                        new IndexOutOfBoundsException("Invalid sheet index: " + sheetNumber));
            }
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            return readSheet(sheet, workbook);
        } catch (InvalidFormatException | IOException e) {
            throw new PropertiesDoesNotLoadException(e);
        }
    }

    private Workbook getWorkBook(String excelFilePath) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(new File(excelFilePath));
    }

    private List<Map<String, String>> readSheet(Sheet sheet, Workbook workbook) {
        List<Map<String, String>> excelRows = new ArrayList<>();

        int headerRowNumber = findHeaderRowNumber(sheet);
        if (headerRowNumber < 0) {
            return excelRows; // no header found
        }

        Row headerRow = sheet.getRow(headerRowNumber);
        if (headerRow == null) {
            return excelRows;
        }

        int totalColumns = headerRow.getLastCellNum(); // may be -1 if empty
        if (totalColumns <= 0) {
            return excelRows;
        }

        // Build stable headers list in column order
        List<String> headers = new ArrayList<>(totalColumns);
        for (int c = 0; c < totalColumns; c++) {
            Cell headerCell = headerRow.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String header = getCellAsString(headerCell, workbook, true).trim();
            headers.add(header);
        }

        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        DataFormatter formatter = new DataFormatter(true);

        // Data starts after header
        int firstDataRow = headerRowNumber + 1;
        int lastRow = sheet.getLastRowNum();

        for (int r = firstDataRow; r <= lastRow; r++) {
            Row row = sheet.getRow(r);
            LinkedHashMap<String, String> rowMap = new LinkedHashMap<>();

            for (int c = 0; c < headers.size(); c++) {
                String header = headers.get(c);
                if (header == null || header.isBlank()) {
                    // skip blank headers to avoid empty keys
                    continue;
                }

                Cell cell = (row == null)
                        ? null
                        : row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                String value = getCellAsString(cell, formatter, evaluator);
                rowMap.put(header, value);
            }

            excelRows.add(rowMap);
        }

        return excelRows;
    }

    /**
     * Finds the first row that contains at least one non-blank cell.
     */
    private int findHeaderRowNumber(Sheet sheet) {
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();

        for (int r = firstRow; r <= lastRow; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;

            short lastCell = row.getLastCellNum();
            if (lastCell <= 0) continue;

            for (int c = 0; c < lastCell; c++) {
                Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                CellType type = cell.getCellType();

                // Correct logic: header row if ANY cell is not blank/none
                if (type != CellType.BLANK && type != CellType._NONE) {
                    return r;
                }
            }
        }
        return -1;
    }

    /**
     * Header conversion helper (creates evaluator/formatter internally).
     */
    private String getCellAsString(Cell cell, Workbook workbook, boolean isHeader) {
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        DataFormatter formatter = new DataFormatter(true);
        return getCellAsString(cell, formatter, evaluator);
    }

    /**
     * Converts a cell into String using POI 5.2.x APIs.
     */
    private String getCellAsString(Cell cell, DataFormatter formatter, FormulaEvaluator evaluator) {
        if (cell == null) return "";

        CellType type = cell.getCellType();

        // Evaluate formulas
        if (type == CellType.FORMULA) {
            type = evaluator.evaluateFormulaCell(cell);
        }

        switch (type) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // If you want date formatting: use DateUtil.isCellDateFormatted(cell)
                // and return formatter.formatCellValue(cell)
                return NumberToTextConverter.toText(cell.getNumericCellValue());
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case BLANK:
            case _NONE:
                return "";
            case ERROR:
                return Byte.toString(cell.getErrorCellValue());
            default:
                return formatter.formatCellValue(cell, evaluator);
        }
    }
}
