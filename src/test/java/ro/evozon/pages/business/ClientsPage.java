package ro.evozon.pages.business;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import net.thucydides.core.matchers.BeanMatcher;
import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.is;
import net.thucydides.core.pages.components.HtmlTable;
import ro.evozon.AbstractPage;
import static net.thucydides.core.pages.components.HtmlTable.filterRows;

public class ClientsPage extends AbstractPage {

	public void click_on_groups_tab() {

		clickOn(find(By.id("group-list")));
	}

	public void click_an_add_new_group() {
		scroll_in_view_then_click_on_element(find(By.cssSelector("div[class='add-group']")));
	}

	public void fill_in_clients_group_name(String groupName) {
		enter(groupName).into(find(By.id("name")));
	}

	public void select_price_list(String listName) {
		List<WebElementFacade> inputBox = findAll(By.cssSelector("input[class='form-control pick-me-client']"));
		List<WebElementFacade> selectList = findAll(By.cssSelector("select#price-list"));
		if (inputBox.size() > 0) {
			// do nothing
		} else if (selectList.size() > 0) {
			select_specific_option_in_list(selectList, listName);
		}

	}

	public void fill_in_clients_group_discount(String discountValue) {
		enter(discountValue).into(find(By.id("discount-value")));
	}

	public void click_on_save_group() {
		clickOn(find(By.id("group-save")));
	}

	public Optional<Map<Object, String>> search_for_list_in_table(String listName, String discountValue) {
		WebElement table = find(By.cssSelector(
				"table[class='table table-hover table-striped table-bordered responsive tick-client-new']"));
		System.out.println("table found " + table.getTagName());
		List<Map<Object, String>> tableRows = HtmlTable.rowsFrom(table);
		tableRows.forEach(System.out::println);
		// tableRows.forEach(p -> System.out.println(p.get("NUME GRUP")));
		// tableRows.forEach(p -> System.out.println(p.get("DISCOUNT GRUP")));
		Optional<Map<Object, String>> result = tableRows.stream().filter(
				u -> (u.get("NUME GRUP").contains(listName)) && (u.get("DISCOUNT GRUP").contains(discountValue)))
				.findFirst();
		// .forEach(u -> System.out.println(u.get("NUME GRUP")));
		System.out.println("nume grup " + result.get().get("NUME GRUP"));
		System.out.println("discount grup" + result.get().get("DISCOUNT GRUP"));
		return result;
	}
	


	
}