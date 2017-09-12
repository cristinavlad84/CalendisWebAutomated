package ro.evozon.tools.utils;

import ch.lambdaj.function.convert.Converter;
import net.thucydides.core.matchers.BeanMatcher;
import ro.evozon.tools.ConfigUtils;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

import static ch.lambdaj.Lambda.convert;

/**
 * Class designed to make it easier reading from and reasoning about data in
 * HTML tables.
 */
public class ElementsList {

	private final WebElement tableElement;
	private List<String> headings;
	public static String headingLocator;
	public static String rowContainerLocator;
	public static String rowLocator;
	public static String rowForHeadingLocator;

	public ElementsList(final WebElement tableElement) {
		this.tableElement = tableElement;
		this.headings = null;
	}

	public ElementsList(final WebElement tableElement, List<String> headings) {
		this.tableElement = tableElement;
		this.headings = headings;
	}

	// public ElementsList( String headingLocator, String rowContainerLocator,
	// String rowLocator, String rowForHeadingLocator ) {
	// // TODO Auto-generated constructor stub
	// this.headingLocator = headingLocator;
	// this.rowContainerLocator =rowContainerLocator;
	// this.rowLocator=rowLocator;
	// this.rowForHeadingLocator=rowForHeadingLocator;
	// }

	public static ElementsList inTable(final WebElement table) {
		return new ElementsList(table);
	}

	public List<Map<String, WebElement>> getRows() {

		List<Map<String, WebElement>> results = new ArrayList<Map<String, WebElement>>();

		List<String> headings = getHeadings();
		List<WebElement> rows = getRowElementsFor(headings);
		int column=0;
		for (WebElement row : rows) {
			List<WebElement> cells = cellsIn(row);
			if(headings.size()==1){
				results.add(rowDataFrom(0,cells, headings));
			}else{
//			 if (enoughCellsFor(headings).in(cells)) {
				 System.out.println("enough" );
			results.add(rowDataFrom(column,cells, headings));}
			column++;
			// }
		}
		return results;
	}

	public WebElement findFirstRowWhere(final BeanMatcher... matchers) {
		List<WebElement> rows = getRowElementsWhere(matchers);
		if (rows.isEmpty()) {
			throw new AssertionError("Expecting a table with at least one row where: " + Arrays.deepToString(matchers));
		}
		return rows.get(0);
	}

	public boolean containsRowElementsWhere(BeanMatcher... matchers) {
		List<WebElement> rows = getRowElementsWhere(matchers);
		return (!rows.isEmpty());
	}

	public void shouldHaveRowElementsWhere(BeanMatcher... matchers) {
		List<WebElement> rows = getRowElementsWhere(matchers);
		if (rows.isEmpty()) {
			throw new AssertionError("Expecting a table with at least one row where: " + Arrays.deepToString(matchers));
		}
	}

	public void shouldNotHaveRowElementsWhere(BeanMatcher... matchers) {
		List<WebElement> rows = getRowElementsWhere(matchers);
		if (!rows.isEmpty()) {
			throw new AssertionError("Expecting a table with no rows where: " + Arrays.deepToString(matchers));
		}
	}

	public static ElementsListBuilder withColumns(String... headings) {
		return new ElementsListBuilder(Arrays.asList(headings));
	}

	public static class ElementsListBuilder {
		private final List<String> headings;

		public ElementsListBuilder(List<String> headings) {
			this.headings = headings;
		}

		public List<Map<String, WebElement>> readRowsFrom(WebElement table) {
			return new ElementsList(table, headings).getRows();
		}

		public ElementsList inTable(WebElement table) {
			return new ElementsList(table, headings);
		}
	}

	private static class EnoughCellsCheck {
		private final int minimumNumberOfCells;

		private EnoughCellsCheck(List<String> headings) {
			this.minimumNumberOfCells = headings.size();
		}

		public boolean in(List<WebElement> cells) {
			return (cells.size() >= minimumNumberOfCells);
		}
	}

	private EnoughCellsCheck enoughCellsFor(List<String> headings) {
		return new EnoughCellsCheck(headings);
	}

	public List<String> getHeadings() {
		List<String> thHeadings = new ArrayList<String>();
		if (headings == null) {
			thHeadings = convert(headingElements(), toTextValues());
			if (thHeadings.isEmpty()) {
				headings = convert(firstRowElements(), toTextValues());
			} else {
				headings = thHeadings;
			}
		}
		for (String s : headings) {
			System.out.println("found heading " + s);
		}
		return headings;
	}

	public List<WebElement> headingElements() {
		return tableElement.findElements(By.xpath(ElementsList.headingLocator));
	}

	public List<WebElement> firstRowElements() {
		List<WebElement> mList = tableElement.findElement(By.xpath(ElementsList.rowContainerLocator))
				.findElements(By.xpath(ElementsList.rowLocator));

		return mList;
	}

	public List<WebElement> getRowElementsFor(List<String> headings) {

		List<WebElement> rowCandidates = tableElement.findElements(By.xpath(ElementsList.rowForHeadingLocator));
		// .findElements(By.xpath(ElementsList.rowForHeadingLocator +
		// headings.size() + "]"));
		System.out.println("row candidates list size =" + rowCandidates.size());
		System.out.println("row for heading locator" + ElementsList.rowForHeadingLocator);
		System.out.println("headings size  " + headings.size());
		for (String s : headings) {
			System.out.println("heading is " + s);

		}
		for (WebElement el : rowCandidates) {
			System.out.println(" row candidates" + el.getAttribute("class"));
		}
		//rowCandidates = stripHeaderRowIfPresent(rowCandidates, headings);

		
		return rowCandidates;
	}

	public List<WebElement> getRowElements() {

		return getRowElementsFor(getHeadings());
	}

	private List<WebElement> stripHeaderRowIfPresent(List<WebElement> rowCandidates, List<String> headings) {
		for (WebElement el : rowCandidates) {
			System.out.println(" row candidates 2" + el.getAttribute("class"));
		}
		if (!rowCandidates.isEmpty()) {
			WebElement firstRow = rowCandidates.get(0);
			System.out.println("first row " + rowCandidates.get(0).getAttribute("class"));
			if (hasMatchingCellValuesIn(firstRow, headings)) {
				System.out.println("removed");
				rowCandidates.remove(0);
			}
		}
		return rowCandidates;
	}

	private boolean hasMatchingCellValuesIn(WebElement firstRow, List<String> headings) {
		List<WebElement> cells = firstRow.findElements(By.xpath(ElementsList.rowLocator));
		System.out.println("has matching cell values list size " + cells.size());
		for (int cellIndex = 0; cellIndex < headings.size(); cellIndex++) {
			if ((cells.size() < cellIndex) || (!cells.get(cellIndex).getText().equals(headings.get(cellIndex)))) {
				return false;
			}
		}
		return true;
	}

	public List<WebElement> getRowElementsWhere(BeanMatcher... matchers) {

		List<WebElement> rowElements = getRowElementsFor(getHeadings());
		List<Integer> matchingRowIndexes = findMatchingIndexesFor(rowElements, matchers);

		List<WebElement> matchingElements = new ArrayList<WebElement>();
		for (Integer index : matchingRowIndexes) {
			matchingElements.add(rowElements.get(index));
		}
		return matchingElements;
	}

	private List<Integer> findMatchingIndexesFor(List<WebElement> rowElements, BeanMatcher[] matchers) {
		List<Integer> indexes = new ArrayList<Integer>();
		List<String> headings = getHeadings();

		int index = 0;
		for (WebElement row : rowElements) {
			Map<String, WebElement> rowData =new HashMap<String, WebElement>();
			List<WebElement> cells = cellsIn(row);
			System.out.println("cells list size   " + cells.size());
			if(headings.size()==1){
			 rowData = rowDataFrom(0,cells, headings);
				
			}
			else
			{
				 rowData = rowDataFrom(index,cells, headings);
			}
			if (matches(rowData, matchers)) {
				System.out.println("matched");
				System.out.println("added at index " + index);
				indexes.add(index);
			}
			index++;
		}

		return indexes;
	}

	private boolean matches(Map<String, WebElement> rowData, BeanMatcher[] matchers) {

		Map<String, String> rowDataContent = new HashMap<String, String>();
		rowData.forEach((k, v) -> rowDataContent.put(k, v.getText()));

		for (BeanMatcher matcher : matchers) {
			System.out.println("the matcher" + matcher + "compared with" + rowDataContent);
			if (!matcher.matches(rowDataContent)) {
				System.out.println("Not matched!!");
				return false;
			}

		}
		return true;
	}

	private Map<String, WebElement> rowDataFrom(int column,List<WebElement> cells, List<String> headings) {
		Map<String, WebElement> rowData = new HashMap<String, WebElement>();
		
		for (WebElement cell : cells) {
			System.out.println("!!!! cell is "+cell.getText());
		}
		
		for (WebElement cell : cells) {

			// String cell = cellValueAt(column, cells);

			if (!StringUtils.isEmpty(headings.get(column))) {
				rowData.put(headings.get(column), cell);
				System.out.println("column"+column+
						"put heading% as key " + headings.get(column) + " and cell% as value  " + cell.getTagName());
			} else {
				rowData.put(Integer.toString(column), cell);
			}
			
		}
		return rowData;
	}

	private List<WebElement> cellsIn(WebElement row) {
		
		List<WebElement> mList = row.findElements(By.xpath(ElementsList.rowLocator));
		//System.out.println("!!cell in is"+mList.get(0).getText());
		// for(WebElement el : mList){
		// System.out.println("cell is "+el.getAttribute("value"));
		// }
		return mList;
	}

	private String cellValueAt(final int column, final List<WebElement> cells) {
		if (cells.get(column).getText().isEmpty()) {
			return cells.get(column).getAttribute("value");
		} else
			return ConfigUtils.replaceLineBreaks(cells.get(column).getText());
	}

	private Converter<WebElement, String> toTextValues() {
		return new Converter<WebElement, String>() {
			public String convert(WebElement from) {
				if (from.getText().isEmpty()) {
					return from.getAttribute("value");
				} else {
					return ConfigUtils.replaceLineBreaks(from.getText());
				}
			}
		};
	}

	public static List<Map<String, WebElement>> rowsFrom(final WebElement table) {
		return new ElementsList(table).getRows();
	}

	public static List<WebElement> filterRows(final WebElement table, final BeanMatcher... matchers) {

		return new ElementsList(table).getRowElementsWhere(matchers);
	}

	public List<WebElement> filterRows(final BeanMatcher... matchers) {
		return new ElementsList(tableElement).getRowElementsWhere(matchers);
	}

}
