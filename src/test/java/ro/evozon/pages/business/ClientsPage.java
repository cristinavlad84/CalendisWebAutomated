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

	public void click_on_add_client() {

		clickOn(find(By.id("add_client")));
	}

	

	public void fill_in_clients_last_name(String lastName) {
		enter(lastName).into(find(By.id("client-lastname")));
	}
	public void fill_in_clients_first_name(String firstName) {
		enter(firstName).into(find(By.id("client-firstname")));
	}
	public void click_on_save_client() {
		clickOn(find(By.cssSelector("button[class='action_button client_side_btn_ml']")));
	}

	public Optional<Map<Object, String>> search_for_client_in_table(String lastName, String firstName) {
		WebElement table = find(By.cssSelector(
				"table[class='table responsive tick-client calendis-business-table']"));
		System.out.println("table found " + table.getTagName());
		List<Map<Object, String>> tableRows = HtmlTable.rowsFrom(table);
		tableRows.forEach(System.out::println);
		// tableRows.forEach(p -> System.out.println(p.get("NUME GRUP")));
		// tableRows.forEach(p -> System.out.println(p.get("DISCOUNT GRUP")));
		Optional<Map<Object, String>> result = tableRows.stream().filter(
				u -> (u.get("Nume").contains(lastName)) && (u.get("Prenume").contains(firstName)))
				.findFirst();
		// .forEach(u -> System.out.println(u.get("NUME GRUP")));
		System.out.println(" found nume " + result.get().get("Nume"));
		System.out.println("found prenume " + result.get().get("Prenume"));
		return result;
	}
	


	
}