package ro.evozon.tools.utils.fileutils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import org.openqa.selenium.WebElement;

import net.thucydides.core.matchers.BeanMatcher;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.utils.ElementsList;
import ro.evozon.tools.utils.ElementsList.ElementsListBuilder;

import org.apache.commons.lang3.StringUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadFromExcelFile {
	public List<String> headings;
	public final Sheet sheet;

	// private static final String FILE_NAME = "src/test/resources/Informatii
	// necesare setare cont_demo_1_1.xlsx";
	public ReadFromExcelFile(final Sheet sheet) {
		this.sheet = sheet;
		this.headings = null;
	}

	public ReadFromExcelFile(final Sheet sheet, List<String> headings) {
		this.sheet = sheet;
		this.headings = headings;
	}

	public static ElementsListBuilder withColumns(String... headings) {
		return new ElementsListBuilder(Arrays.asList(headings));
	}

	public static class ReadFromExcelFileBuilder {
		private final List<String> headings;

		public ReadFromExcelFileBuilder(List<String> headings) {
			this.headings = headings;
		}

		public List<LinkedHashMap<String, Cell>> readRowsFrom(Sheet sheetData) {
			return new ReadFromExcelFile(sheetData, headings).getRows();
		}

		public ElementsList inTable(WebElement table) {
			return new ElementsList(table, headings);
		}
	}

	public static void readXlsxFile(String fileName) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(fileName));
			Workbook workbook = null;
			try {
				workbook = WorkbookFactory.create(new File(fileName));
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			Iterator<Cell> cellIterator = null;
			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				if (!currentRow.getZeroHeight()) {

					cellIterator = currentRow.iterator();

				}

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();
					// getCellTypeEnum shown as deprecated for version 3.15
					// getCellTypeEnum ill be renamed to getCellType starting
					// from version 4.0
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						System.out.print(currentCell.getStringCellValue() + "--");
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						System.out.print(currentCell.getNumericCellValue() + "++");
					}

				}
				System.out.println();

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<LinkedHashMap<String, Cell>> getRows() {

		List<LinkedHashMap<String, Cell>> results = new ArrayList<LinkedHashMap<String, Cell>>();

		List<String> headings = getHeadings();
		List<Row> rows = getRowElementsFor();

		for (Row row : rows) {
			List<Cell> cells = cellsIn(row);

			// if (enoughCellsFor(headings).in(cells)) {
		
			results.add(rowDataFrom(cells, headings));

			// }
		}
		return results;
	}

	public List<String> getHeadings() {
		List<String> columnHeaderList = new ArrayList<String>();

		int colNum = sheet.getRow(0).getLastCellNum();
		System.out.println("colNum" + colNum);
		Row row = sheet.getRow(0);
		System.out.println("row no of cells " + row.getPhysicalNumberOfCells());
		Cell cell = null;

		for (int j = 0; j < colNum; j++) {
			cell = row.getCell(j);
			columnHeaderList.add(cell.getStringCellValue());
		}
		return columnHeaderList;
	}

	public List<Row> getRowElementsWhere(BeanMatcher... matchers) {

		List<Row> rowElements = getRowElementsFor();
		List<Integer> matchingRowIndexes = findMatchingIndexesFor(rowElements, matchers);

		List<Row> matchingElements = new ArrayList<Row>();
		for (Integer index : matchingRowIndexes) {
			matchingElements.add(rowElements.get(index));
		}
		return matchingElements;
	}

	public List<Row> getRowElementsFor() {

		List<Row> rowCandidates = new ArrayList<Row>();
		Iterator<Row> iterator = sheet.iterator();
		// Iterator<Cell> cellIterator = null;
		// while (iterator.hasNext()) {
		//
		// Row currentRow = iterator.next();
		// if (!currentRow.getZeroHeight()) {
		// rowCandidates.add(currentRow);
		//
		// }
		//
		// }
		//
		//
		//
		//
		int rowStart = 1;
		int rowEnd = sheet.getLastRowNum();
		System.out.println("rowEnd =" + rowEnd);
		for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
			Row currentRow = sheet.getRow(rowNum);

			// int lastColumn = currentRow.getLastCellNum();
			if (!currentRow.getZeroHeight()) {
				rowCandidates.add(currentRow);

			}

		}
		System.out.println("no of rows in sheet is " + rowCandidates.size());
		return rowCandidates;
	}

	public List<Integer> findMatchingIndexesFor(List<Row> rowElements, BeanMatcher[] matchers) {
		List<Integer> indexes = new ArrayList<Integer>();
		List<String> headings = getHeadings();

		int index = 0;
		for (Row row : rowElements) {
			Map<String, Cell> rowData = new HashMap<String, Cell>();
			List<Cell> cells = cellsIn(row);

			rowData = rowDataFrom(cells, headings);
			index++;
			if (matches(rowData, matchers)) {
				System.out.println("matched");
				System.out.println("added at index " + index);
				indexes.add(index);
			}
		}

		return indexes;
	}

	public boolean matches(Map<String, Cell> rowData, BeanMatcher[] matchers) {

		Map<String, String> rowDataContent = new HashMap<String, String>();
		rowData.forEach((k, v) -> rowDataContent.put(k, v.getStringCellValue().toString()));

		for (BeanMatcher matcher : matchers) {
			System.out.println("the matcher" + matcher + "compared with" + rowDataContent);
			if (!matcher.matches(rowDataContent)) {
				System.out.println("Not matched!!");
				return false;
			}

		}
		return true;
	}

	public LinkedHashMap<String, Cell> rowDataFrom(List<Cell> cells, List<String> headings) {
		LinkedHashMap<String, Cell> rowData = new LinkedHashMap<String, Cell>();
		String cellValue;
//		for (Cell cell : cells) {
//			System.out.println("!!!! cell is " + cell.getStringCellValue());
//		}
		int column = 0;

		for (Cell cell : cells) {
			
			// String cell = cellValueAt(column, cells);

			if (!StringUtils.isEmpty(headings.get(column))) {
				rowData.put(headings.get(column), cell);
//				System.out.println("column" + column + "put heading% as key " + headings.get(column)
//						+ " and cell% as value  " + cell.getStringCellValue());
				column++;
			} else {
				rowData.put(Integer.toString(column), cell);
				column++;
			}

		}
		return rowData;
	}

	public List<Cell> cellsIn(Row row) {
		int rowNum = sheet.getLastRowNum() + 1;
		int colNum = sheet.getRow(0).getLastCellNum();
		List<Cell> cellsList = new ArrayList<Cell>();
		Cell cell = null;

		for (int j = 0; j < colNum; j++) {
			cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

			cellsList.add(cell);

		}
		return cellsList;

	}

	public boolean containsRowElementsWhere(BeanMatcher... matchers) {
		List<Row> rows = getRowElementsWhere(matchers);
		return (!rows.isEmpty());
	}

	public void shouldHaveRowElementsWhere(BeanMatcher... matchers) {
		List<Row> rows = getRowElementsWhere(matchers);
		if (rows.isEmpty()) {
			throw new AssertionError("Expecting a table with at least one row where: " + Arrays.deepToString(matchers));
		}
	}

	public void shouldNotHaveRowElementsWhere(BeanMatcher... matchers) {
		List<Row> rows = getRowElementsWhere(matchers);
		if (!rows.isEmpty()) {
			throw new AssertionError("Expecting a table with no rows where: " + Arrays.deepToString(matchers));
		}
	}

	public List<Row> filterRows(final BeanMatcher... matchers) {
		return new ReadFromExcelFile(sheet).getRowElementsWhere(matchers);
	}

	public static List<LinkedHashMap<String, Cell>> rowsFrom(final Sheet sheetData) {
		return new ReadFromExcelFile(sheetData).getRows();
	}

	public static List<String> getKeys(LinkedHashMap<String, Cell> myMap) {
		
		return myMap.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList());
	}

	public static void main(String[] args) {

		try {

			String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForXlsxFile();
			FileInputStream excelFile = new FileInputStream(new File(fileName));
			Workbook workbook =null;
			try {
				workbook = WorkbookFactory.create(new File(fileName));
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			Sheet datatypeSheet = workbook.getSheet("Cont adminstrator");
			List<LinkedHashMap<String, Cell>> myDataCollection = rowsFrom(datatypeSheet);
			myDataCollection.forEach(k -> System.out.println(k));

			Sheet datatypeSheet2 = workbook.getSheet("Locatii");
			List<LinkedHashMap<String, Cell>> myDataCollection2 = rowsFrom(datatypeSheet2);
			myDataCollection2.forEach(k -> System.out.println(k));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
