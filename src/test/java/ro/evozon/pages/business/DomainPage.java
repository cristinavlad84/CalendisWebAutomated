package ro.evozon.pages.business;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Predicate;

import net.serenitybdd.core.pages.WebElementFacade;
import ro.evozon.AbstractPage;

public class DomainPage extends AbstractPage {
	public void click_on_add_new_domain() {
		WebElementFacade addNewDomainElement = find(By.id("add-new-domain"));
		scroll_in_view_then_click_on_element(addNewDomainElement);
	}

	public void fill_in_domain_name(String name) {
		enter(name).into(find(By.id("settings-input-domain")));
	}

	public void click_on_save_domain() {

		WebElementFacade elem = find(
				By.cssSelector("button[class='validation_button client_side_btn_m save-new-domain']"));
		scroll_in_view_then_click_on_element(elem);

	}

	public WebElementFacade getDomainWebElement(String locationStreetAddress) {
		return get_element_from_elements_list("div#domains > div[class^='domain'] div[class='location-view']",
				"div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > span:first-child",
				locationStreetAddress);
	}

	public WebElementFacade getDomainAssociatedLocationWebElement(String associatedLocation) {
		return get_element_from_elements_list(
				"div#domains > div[class^='domain'] div[class='location-view  col-xs-12']",
				"div[class='edit-information']:first-child> h4[class='loc-address']:nth-child(1) > span:first-child",
				associatedLocation);
	}

	public boolean is_domain_name_displayed(String domainName) {
		return is_element_present_in_elements_list("div#domains > div[class^='domain'] div[class='location-view']",
				"div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > span:first-child",
				domainName);

	}

	public void click_on_delete_domain_link(String domainName) {
		WebElementFacade elem = getDomainWebElement(domainName);
		WebElementFacade domain = elem.find(By.cssSelector(
				"div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > a:nth-of-type(1)"));
		scroll_in_view_then_click_on_element(domain);
	}

	public String select_random_location_domain_form() {
		String str = "";
		WebElementFacade elem = getLocationElementFromDomainForm();
		if (elem.getTagName().contentEquals("input")) {
			// do nothing
		} else if (elem.getTagName().contentEquals("select")) {
			str = select_random_option_in_dropdown(elem);
		}
		return str;

	}

	public void select_location_domain_form(String locationName) {
		WebElementFacade elem = getLocationElementFromDomainForm();
		if (elem.getTagName().contentEquals("select")) {
			System.out.println("option for location "+locationName+"was selected in dropdown");
			select_option_in_dropdown(elem, locationName);
		}

	}

	public WebElementFacade getLocationElementFromDomainForm() {
		WebElementFacade locationElem = null;
		List<WebElementFacade> singleLocationList = findAll(
				By.cssSelector("input[class='pick-me new-sel-settings form-control']"));
		List<WebElementFacade> multipleLocationsList = findAll(By.cssSelector("select#settings-select-location"));
		if (singleLocationList.size() > 0) {
			locationElem = singleLocationList.get(0);
		} else if (multipleLocationsList.size() > 0) {
			locationElem = multipleLocationsList.get(0);
		}

		return locationElem;
	}
}
