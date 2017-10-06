package ro.evozon.pages.business;

import java.util.List;

import org.openqa.selenium.By;

import com.sun.jna.platform.win32.WinNT.FILE_NOTIFY_INFORMATION;

import net.serenitybdd.core.pages.WebElementFacade;
import ro.evozon.AbstractPage;

public class LocationPage extends AbstractPage {
	public void click_on_add_new_location() {

		WebElementFacade el = find(By.id("new-location"));
		scroll_in_view_then_click_on_element(el);
	}

	public void fill_in_location_street_address(String addresss) {
		enter(addresss).into(find(By.id("settings-address")));
	}

	public void fill_in_location_phone(String phone) {
		enter(phone).into(find(By.id("settings-phone")));
	}

	public void fill_in_location_name(String name) {
		enter(name).into(find(By.id("settings-location-name")));
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

	public String select_random_location_region() {
		return select_random_option_in_dropdown(
				find(By.cssSelector("select[class='pick-me new-sel-settings settings-select-region']")));
	}

	public void select_location_region(String region) {
		List<WebElementFacade> mList = findAll(
				By.cssSelector("select[class='pick-me new-sel-settings settings-select-region'] > option"));
		select_specific_option_in_list(mList, region);

	}

	public void click_on_set_location_schedule() {
		click_on_element(find(By.cssSelector("button[class='validation_button client_side_btn_l navigate-location']")));
	}

	public void select_day_of_week_location_schedule() {
		select_day_of_week_schedule("div[class='check-schedule']", "label[for^='checkbox']");
	}

	public void select_day_of_week_location_schedule(int position) {
		select_specific_day_of_week("div[class='check-schedule']", "label[for^='checkbox']", position);
	}

	public void click_on_save_location() {
		WebElementFacade elem = find(
				By.cssSelector("button[class='validation_button client_side_btn_m save-location']"));
		scroll_in_view_then_click_on_element(elem);

	}

	public void click_on_set_location_schedule_editing() {
		WebElementFacade elem = find(
				By.cssSelector("button[class='validation_button client_side_btn_l navigate-location']"));
		scroll_in_view_then_click_on_element(elem);
	}

	public String select_random_location_city() {
		return select_random_option_in_dropdown(find(By.cssSelector("select#settings-select-city")));
	}

	public void select_location_city(String locationCity) {
		List<WebElementFacade> mList = findAll(By.cssSelector("select#settings-select-city > option"));
		select_specific_option_in_list(mList, locationCity);
	}

	public boolean is_location_detail_displayed_in_location_section(String locationStreetAddress,
			String locationDetail) {
		WebElementFacade elementContainer = getLocationWebElement(locationStreetAddress);
		return is_item_displayed_through_found_element(elementContainer, locationDetail,
				"span[class='location-phone']");
	}

	public WebElementFacade getLocationWebElement(String locationStreetAddress) {
		return get_element_from_elements_list("div[class='location-view-content']",
				"h4[class='loc-address']:first-child> span:nth-of-type(2)", locationStreetAddress);
	}

	public void click_on_modify_location_link(String locationAdress) {
		WebElementFacade elem = getLocationWebElement(locationAdress);
		WebElementFacade location = elem.find(By.cssSelector(
				"div[class='edit-information'] > h4[class='loc-address'] > span:nth-child(3) > a[class='edit-info edit-location'] > i:first-child"));
		scroll_in_view_then_click_on_element(location);
	}

	public void click_on_delete_location_link(String locationAdress) {
		WebElementFacade elem = getLocationWebElement(locationAdress);
		scroll_in_view_then_click_on_element(elem.find(By.cssSelector(
				"div[class='edit-information'] > h4[class='loc-address'] > span:nth-child(3) > a[class='edit-info delete-location'] > i:first-child")));
	}

	public boolean is_location_street_address_displayed(String locationStreetAddress) {

		return is_element_present_in_elements_list("div[class='location-view-content']",
				"h4[class='loc-address']:first-child> span:nth-of-type(2)", locationStreetAddress);
	}

}
