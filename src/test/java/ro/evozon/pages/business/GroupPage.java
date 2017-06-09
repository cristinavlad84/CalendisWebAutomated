package ro.evozon.pages.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.matchers.BeanMatcher;
import net.thucydides.core.pages.components.HtmlTable;
import ro.evozon.AbstractPage;
import ro.evozon.tools.ElementsList;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;

public class GroupPage extends AbstractPage {
	public void click_on_add_new_group() {
		WebElementFacade el = find(By.id("new-group"));
		scroll_in_view_then_click_on_element(el);
	}

	public void fill_in_group_name(String name) {
		enter(name).into(find(By.cssSelector("div#container-add-group input[name='name']")));
	}

	public String get_group_name() {
		return find(By.cssSelector("div#container-groups input[name='name']")).getAttribute("value");
	}

	public void fill_in_discount_value_for_group(String discountValue) {
		enter(discountValue).into(find(By.id("discount-value")));
	}

	public String get_discount_value_for_group() {
		return find(By.id("discount-value")).getAttribute("value");
	}

	public void click_on_save_group_form() {
		WebElementFacade elem = find(By.cssSelector("button#group_form_save"));
		scroll_in_view_then_click_on_element(elem);
	}

	public void select_price_list_for_group(String priceList) {
		WebElementFacade elemList = find(By.cssSelector("select#price-list"));
		select_option_in_dropdown(elemList, priceList);
	}

	public String get_selected_option_price_dropdown() {
		String txt = "";
		List<WebElementFacade> elemList = findAll(By.cssSelector("select#price-list > option[id='0']"));
		if (elemList.size() > 0) {
			txt = elemList.get(0).getText();
		} else
			throw new AssertionError("Expecting selected option but not found: ");
		return txt;
	}
	// public boolean is_voucher_name_displayed(String voucherName) {
	// return is_element_present_in_elements_list("div#domains >
	// div[class^='domain'] div[class='location-view']",
	// "div[class='edit-information']:first-child>
	// div[class='domain-name']:nth-child(1) > span:first-child",
	// voucherName);
	//
	// }

	public List<WebElementFacade> getGroupNamesElementsList() {
		return find(By.id("container-groups")).thenFindAll(By.cssSelector("div[class='col-xs-12'] > h4:first-child"));
	}

	public List<String> getGroupNames() {
		List<WebElementFacade> listGroupNames = new ArrayList<WebElementFacade>(getGroupNamesElementsList());
		List<String> namesL = listGroupNames.stream().map(p -> p.getText().trim()).collect(Collectors.toList());
		namesL.forEach(p -> System.out.println(p));
		return namesL;
	}

	public Optional<String> getGroupFor(String groupName) {
		return getGroupNames().stream().filter(item -> item.contains(groupName)).findFirst();
	}

	// public Optional<Map<Object, String>> search_for_list_in_table(String
	// listName) {
	// WebElement table = find(By.cssSelector("div#client_groups"));
	// System.out.println("list found " + table.getTagName());
	// List<Map<Object, String>> tableRows =
	// ElementsList.withColumns("Grupuri").readRowsFrom(table);
	// tableRows.forEach(System.out::println);
	// // tableRows.forEach(p -> System.out.println(p.get("NUME GRUP")));
	// // tableRows.forEach(p -> System.out.println(p.get("DISCOUNT GRUP")));
	// Optional<Map<Object, String>> result = tableRows.stream().filter(u ->
	// (u.get("Grupuri").contains(listName)))
	// .findFirst();
	// // .forEach(u -> System.out.println(u.get("NUME GRUP")));
	// System.out.println("Grupuri " + result.get().get("Grupuri"));
	//
	// return result;
	// }

	public List<WebElement> get_element_matching_criteria(BeanMatcher... matchers) {
		WebElement table = find(By.cssSelector("div#client_groups"));	
		ElementsList.headingLocator = ".//div[@class='col-xs-6']/h3";
		ElementsList.rowContainerLocator = ".//div[@class='col-xs-12']";
		ElementsList.rowLocator = ".//h4[position()=1]";
		ElementsList.rowForHeadingLocator = ".//div[@class='col-xs-12'][h4][count(h4)>=";
		List<WebElement> matchingRows = ElementsList.filterRows(table, matchers);
		System.out.println("!!!!!"+matchingRows.get(0).getText());
		return matchingRows;
	}

	public WebElement get_element_containing_group(BeanMatcher... matchers) {
		List<WebElement> matchingRows = get_element_matching_criteria(matchers);
		WebElement targetRow = matchingRows.get(0).findElement(By.cssSelector("h4 > span > a > i"));
		System.out.println("target Row" + targetRow.getTagName());
		return targetRow;
	}

	public void click_on_modify_group(WebElement modifyLink) {
		scroll_in_view_then_click_on_element(modifyLink);
	}
	// public void click_on_modify_service_link(String serviceName) {
	// WebElementFacade elem = getServiceWebElement(serviceName);
	// WebElementFacade service = elem.find(By.cssSelector(
	// "div[class='edit-information'] > h4[class='loc-address service-name'] >
	// span:nth-child(2) > a[class='edit-info update-service'] >
	// i:first-child"));
	// scroll_in_view_then_click_on_element(service);
	// }
	//
	// public void click_on_delete_service_link(String serviceName) {
	// WebElementFacade elem = getServiceWebElement(serviceName);
	// WebElementFacade service = elem.find(By.cssSelector(
	// "div[class='edit-information'] > h4[class='loc-address service-name'] >
	// span:nth-child(2) > a[class='edit-info delete-service'] >
	// i:first-child"));
	// scroll_in_view_then_click_on_element(service);
	// }

}
