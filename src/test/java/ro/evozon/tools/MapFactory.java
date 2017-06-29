package ro.evozon.tools;

import ch.lambdaj.function.convert.Converter;
import net.thucydides.core.matchers.BeanMatcher;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

import static ch.lambdaj.Lambda.convert;

/**
 * Class designed to make it easier reading from and reasoning about data in
 * HTML tables.
 */
public class MapFactory {

	private final WebElement tableElement;
	private List<String> headings;
	public static String headingLocator;
	public static String rowContainerLocator;
	public static String rowLocator;
	public static String rowForHeadingLocator;

	public MapFactory(final WebElement tableElement) {
		this.tableElement = tableElement;
		this.headings = null;
	}

	public MapFactory(final WebElement tableElement, List<String> headings) {
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

	public static MapFactory inTable(final WebElement table) {
		return new MapFactory(table);
	}

	public List<Map<Object, String>> getRows() {

		List<Map<Object, String>> results = new ArrayList<Map<Object, String>>();

		List<String> headings = getHeadings();
		List<WebElement> rows = getRowElementsFor(headings);

		for (WebElement row : rows) {
			List<WebElement> cells = cellsIn(row);
			if (enoughCellsFor(headings).in(cells)) {
				results.add(rowDataFrom(cells, headings));
			}
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

		public List<Map<Object, String>> readRowsFrom(WebElement table) {
			return new MapFactory(table, headings).getRows();
		}

		public MapFactory inTable(WebElement table) {
			return new MapFactory(table, headings);
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
		return tableElement.findElements(By.cssSelector(MapFactory.headingLocator));
	}

	public List<WebElement> firstRowElements() {
		List<WebElement> mList = tableElement.findElement(By.xpath(MapFactory.rowContainerLocator))
				.findElements(By.xpath(MapFactory.rowLocator));

		return mList;
	}

	public List<WebElement> getRowElementsFor(List<String> headings) {

		List<WebElement> rowCandidates = tableElement
				.findElements(By.xpath(MapFactory.rowForHeadingLocator + headings.size() + "]"));
		System.out.println("row candidates list size =" + rowCandidates.size());
		System.out.println("row for heading locator" + MapFactory.rowForHeadingLocator + headings.size() + "]");
		System.out.println("headings size  " + headings.size());
		for (String s : headings) {
			System.out.println("heading is " + s);

		}
		rowCandidates = stripHeaderRowIfPresent(rowCandidates, headings);

		for (WebElement el : rowCandidates) {
			System.out.println(" row " + el.getText());
		}
		return rowCandidates;
	}

	public List<WebElement> getRowElements() {

		return getRowElementsFor(getHeadings());
	}

	private List<WebElement> stripHeaderRowIfPresent(List<WebElement> rowCandidates, List<String> headings) {
		if (!rowCandidates.isEmpty()) {
			WebElement firstRow = rowCandidates.get(0);

			if (hasMatchingCellValuesIn(firstRow, headings)) {
				System.out.println("removed");
				rowCandidates.remove(0);
			}
		}
		return rowCandidates;
	}

	private boolean hasMatchingCellValuesIn(WebElement firstRow, List<String> headings) {
		List<WebElement> cells = firstRow.findElements(By.xpath(MapFactory.rowLocator));

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

			List<WebElement> cells = cellsIn(row);
			System.out.println("cells  " + cells.size());
			System.out.println("cell  " + cells.get(0).getText());
			Map<Object, String> rowData = rowDataFrom(cells, headings);
			if (matches(rowData, matchers)) {
				System.out.println("matched");
				System.out.println("added at index " + index);
				indexes.add(index);
			}
			index++;
		}

		return indexes;
	}

	private boolean matches(Map<Object, String> rowData, BeanMatcher[] matchers) {
		rowData.entrySet().forEach(entry -> System.out.println("entry in map" + entry.getValue()));
		for (BeanMatcher matcher : matchers) {
			System.out.println("the matcher" + matcher + "compared with" + rowData);
			if (!matcher.matches(rowData)) {
				System.out.println("Not matched!!1");
				return false;
			}

		}
		return true;
	}

	private Map<Object, String> rowDataFrom(List<WebElement> cells, List<String> headings) {
		Map<Object, String> rowData = new HashMap<Object, String>();

		int column = 0;
		for (String heading : headings) {
			String cell = cellValueAt(column++, cells);
			if (!StringUtils.isEmpty(heading)) {
				rowData.put(heading, cell);
				System.out.println("put heading" + heading + "cell " + cell);
			}
			rowData.put(column, cell);
		}
		return rowData;
	}

	private List<WebElement> cellsIn(WebElement row) {
		return row.findElements(By.xpath(MapFactory.rowLocator));
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

	public static List<Map<Object, String>> rowsFrom(final WebElement table) {
		return new MapFactory(table).getRows();
	}

	public static List<WebElement> filterRows(final WebElement table, final BeanMatcher... matchers) {

		return new MapFactory(table).getRowElementsWhere(matchers);
	}

	public List<WebElement> filterRows(final BeanMatcher... matchers) {
		return new MapFactory(tableElement).getRowElementsWhere(matchers);
	}

}
