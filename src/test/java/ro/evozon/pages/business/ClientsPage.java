package ro.evozon.pages.business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import net.thucydides.core.matchers.BeanMatcher;
import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.is;
import net.thucydides.core.pages.components.HtmlTable;
import ro.evozon.AbstractPage;
import ro.evozon.tools.ElementsList;

import static net.thucydides.core.pages.components.HtmlTable.filterRows;
import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;

public class ClientsPage extends AbstractPage {

	public void click_on_add_client() {

		clickOn(find(By.id("add_client")));
	}

	public void fill_in_clients_last_name(String lastName) {
		enter(lastName).into(find(By.id("client-lastname")));
	}

	public void fill_in_clients_email(String email) {
		enter(email).into(find(By.id("client-email")));
	}

	public void fill_in_client_phone(String phone) {
		enter(phone).into(find(By.id("client-phone")));
	}

	public void fill_in_clients_first_name(String firstName) {
		enter(firstName).into(find(By.id("client-firstname")));
	}

	public void click_on_save_client() {
		clickOn(find(By.cssSelector("button[class='action_button client_side_btn_ml']")));
	}

	public List<WebElement> get_row_web_element_with_group(BeanMatcher... matchers) {
		WebElement table = find(By.cssSelector("div[class='sidebar-container']"));
		ElementsList.headingLocator = ".//div[div[@class='sidebar_group_header']][position()=2]/div[@class='sidebar_group_header']";
		ElementsList.rowContainerLocator = ".//div[div[@class='sidebar_group_header']][position()=2]/following-sibling::div[count(preceding-sibling::div/div[@class='sidebar_group_header'])=2]";
		ElementsList.rowLocator = ".//div/span[@class='sidebar_value']";
		ElementsList.rowForHeadingLocator = ".//div[div[@class='sidebar_group_header']][position()=2]/following-sibling::div[count(preceding-sibling::div/div[@class='sidebar_group_header'])=2]";
		List<WebElement> matchingRows = ElementsList.filterRows(table, matchers);
		System.out.println("!!!!!" + matchingRows.get(0).getText());

		return matchingRows;
	}

	public Optional<List<WebElement>> get_row_web_element_containig_client(BeanMatcher... matchers) {

		WebElement table = find(By.cssSelector("table[class='table responsive tick-client calendis-business-table']"));
		List<WebElement> matchingRows = HtmlTable.filterRows(table, matchers);
		Optional<List<WebElement>> optList = Optional.ofNullable(matchingRows);
		 System.out.println("!!!!!size " + matchingRows.size());
		// matchingRows.get(0).getText());

		return optList;
	}

	public void check_group_label(WebElement groupEl) {

		scroll_in_view_then_click_on_element(groupEl.findElement(By.tagName("label")));
	}

	public int getClientsPagesSize() {
		return findAll(By.cssSelector("div[class='page-navigation light-theme simple-pagination'] > ul > li")).size()
				- 2;
	}

	public WebElementFacade getNextPageElement() {
		return find(By.cssSelector("div[class='page-navigation light-theme simple-pagination'] > ul > li:last-child"));
	}

	public void clickOnNextClientPage() {
		click_on_element(find(By.cssSelector(
				"div[class='page-navigation light-theme simple-pagination'] > ul > li:last-child > a > span")));
		waitForPageToLoad();
	}

	public boolean isNextPageDisabled(WebElementFacade nextPageEl) {
		boolean isNextPageDisabled = false;
		List<WebElementFacade> mList = findAll(By
				.cssSelector("div[class='page-navigation light-theme simple-pagination'] > ul >  li:last-child[class='disabled']"));
		
		if (mList.size() >= 1) {
			System.out.println("size "+mList.size()+"no more pages");
			isNextPageDisabled = true;
		}
		return isNextPageDisabled;
	}

	public List<WebElementFacade> get_table_elements_list() {
		return findAll(
				By.cssSelector("table[class='table responsive tick-client calendis-business-table']  > tbody > tr"));
	}

	public void wait_until_table_loaded() {
		// List<WebElementFacade> mList =get_table_elements_list();
		WebElement table = find(By.cssSelector("table[class='table responsive tick-client calendis-business-table'] "));

		System.out.println("!!!! table loading ..." + table.getTagName());
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(table)));
		// element in the page to be

		// waitUntilOptionsPopulated(mList);
	}

	public Optional<Map<String, WebElement>> search_for_group_in_table(String groupName) {
		WebElement table = find(By.cssSelector("div[class='sidebar-container']"));
		System.out.println("list found " + table.getTagName());
		ElementsList.headingLocator = ".//div[div[@class='sidebar_group_header']][position()=2]/div[@class='sidebar_group_header']";
		ElementsList.rowContainerLocator = ".//div[div[@class='sidebar_group_header']][position()=2]/following-sibling::div[count(preceding-sibling::div/div[@class='sidebar_group_header'])=2]";
		ElementsList.rowLocator = ".//div/span[@class='sidebar_value']";
		ElementsList.rowForHeadingLocator = ".//div[div[@class='sidebar_group_header']][position()=2]/following-sibling::div[count(preceding-sibling::div/div[@class='sidebar_group_header'])=2]";

		List<Map<String, WebElement>> tableRows = ElementsList.withColumns("GRUP").readRowsFrom(table);
		System.out.println("size is   " + tableRows.size());
		tableRows.forEach(System.out::println);
		tableRows.forEach(p -> System.out.println("element in map " + p.get("GRUP").getText()));
		Optional<Map<String, WebElement>> result = tableRows.stream()
				.filter(u -> u.get("GRUP").getText().contains(groupName)).findFirst();
		result.get().forEach((s, w) -> System.out.println(w.getText()));
		return result;
	}

	public Optional<List<WebElement>> get_client_in_table(String lastName, String firstName) {
		WebElement table = find(By.cssSelector("table[class='table responsive tick-client calendis-business-table']"));
		System.out.println("table found " + table.getTagName());
		List<WebElement> tableRows = HtmlTable.filterRows(table, the("Nume", containsString(lastName)),
				the("Prenume", containsString(firstName)));
		System.out.println("size of clients list " + tableRows.size());
		// tableRows.forEach(System.out::println);
		Optional<List<WebElement>> optClientList = Optional.ofNullable(tableRows);

		// tableRows.forEach(p -> System.out.println(p.get("NUME GRUP")));
		// tableRows.forEach(p -> System.out.println(p.get("DISCOUNT GRUP")));

		// System.out.println(" found nume " + tableRows.get(0).getText());
		return optClientList;
	}

	public boolean is_client_displayed_in_table(String lastName, String firstName) {
		boolean isPresent = false;
		Optional<List<WebElement>> elementsL = get_client_in_table(lastName, firstName);
		if (elementsL.get().size() > 0) {
			isPresent = true;
		}
		return isPresent;

	}

	public void select_client_from_table(String lastName, String firstName) {
		Optional<List<WebElement>> elementsL = get_client_in_table(lastName, firstName);
		WebElement labelToCheck = elementsL.get().get(0).findElement(By.cssSelector("td:first-child > label"));
		scroll_in_view_then_click_on_element(labelToCheck);
	}

	public void click_on_add_to_group() {
		clickOn(find(By.id("add_to_group")));
	}

	public void select_group_for_client_to_add_to(String priceListName) {
		WebElementFacade dropdown = find(By.cssSelector("select#clients_group"));
		select_option_in_dropdown(dropdown, priceListName);
	}

	public void click_on_save_adding_client_to_group_modal() {
		clickOn(find(By.id("confirmation_modal_save")));
	}
}