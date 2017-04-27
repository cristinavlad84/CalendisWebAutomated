package ro.evozon.pages.business;

import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import ro.evozon.AbstractPage;
import ro.evozon.tools.ConfigUtils;

public class SettingsPage extends AbstractPage {

	public void logout_link_should_be_displayed() {
		find(By.id("nav-disconect")).shouldBeVisible();
	}

	public void select_location_from_left_menu() {
		clickOn(find(By.id("settings_locations_tab")));
	}

	public void select_domain_from_left_menu() {
		clickOn(find(By.id("settings_domains_tab")));
	}

	public void click_on_add_new_location() {

		try {
			WebElement el = find(By.id("new-location"));
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(true);", el);
			jse.executeScript("arguments[0].click();", el);
		} catch (Exception e) {

		}

	}

	public void click_on_add_new_domain() {

		try {
			WebElement el = find(By.id("add-new-domain"));
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(true);", el);
			jse.executeScript("arguments[0].click();", el);
		} catch (Exception e) {

		}

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

	public void fill_in_domain_name(String name) {
		enter(name).into(find(By.id("settings-input-domain")));
	}

	public String select_random_region() {
		return select_random_option_in_dropdown(find(By
				.cssSelector("select[class='pick-me new-sel-settings settings-select-region']")));
	}

	public void click_on_set_location_schedule() {
		clickOn(find(By
				.cssSelector("button[class='validation_button client_side_btn_l navigate-location']")));
	}

	public void select_day_of_week_location_schedule() {
		select_day_of_week_schedule("div[class='check-schedule']",
				"label[for^='checkbox']");
	}

	public void click_on_save_location() {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(true);",
					find(By.id("new-staff")));
			jse.executeScript(
					"arguments[0].click();",
					find(By.cssSelector("button[class='validation_button client_side_btn_m save-location']")));
		} catch (Exception e) {

		}

		waitForPageToLoad();// -> wait to save location
	}

	public void click_on_save_domain() {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(true);",
					find(By.id("new-staff")));
			jse.executeScript(
					"arguments[0].click();",
					find(By.cssSelector("button[class='validation_button client_side_btn_m save-new-domain']")));
		} catch (Exception e) {

		}

		waitForPageToLoad();// -> wait to save location
	}

	public void click_on_set_location_schedule_editing() {
		clickOn(find(By
				.cssSelector("button[class='validation_button client_side_btn_l navigate-location']")));

		waitForPageToLoad();// -> wait to save location
	}

	public String select_random_city() {
		return select_random_option_in_dropdown(find(By
				.cssSelector("select#settings-select-city")));
	}

	public void click_on_add_new_staff() {

		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(true);",
					find(By.id("new-staff")));
			jse.executeScript("arguments[0].click();", find(By.id("new-staff")));
		} catch (Exception e) {

		}

	}

	public void select_staff_type_to_be_added(String staffType) {
		List<WebElementFacade> myRadioList = findAll(By
				.cssSelector("label[class='radio-inline radio-role'] > input"));

		WebElementFacade optToSelect = null;
		for (WebElementFacade opt : myRadioList) {
			System.out.println("to print " + opt.getValue().trim());
			if (ConfigUtils.removeAccents(opt.getValue().trim()).contentEquals(
					staffType)) {

				optToSelect = opt;
				break;
			}
		}
		if (!optToSelect.isSelected()) {
			optToSelect.click();
		}
	}

	public void fill_in_staff_name(String staffName) {
		enter(staffName).into(find(By.id("settings-staff-name")));
	}

	public void fill_in_staff_email(String staffEmail) {

		enter(staffEmail).into(find(By.id("settings-staff-email")));
	}

	public void fill_in_staff_phone(String staffPhone) {

		enter(staffPhone).into(find(By.id("settings-staff-phone")));
	}

	public void check_in_default_location() {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement el = find(By
				.cssSelector("li[class='jstree-node staff-location jstree-open jstree-last'] > a[class='jstree-anchor'] >  i[class='jstree-icon jstree-checkbox']"));
		jse.executeScript("arguments[0].click();", el);
	}

	public void click_on_set_staff_schedule() {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement el = find(By
				.cssSelector("button[class='validation_button client_side_btn_l navigate-staff']"));
		jse.executeScript("arguments[0].click();", el);
	}

	public void select_day_of_week_for_staff() {
		select_day_of_week_schedule(
				"div[class='schedule-table-wrap form-group clearfix']",
				"label[for^='checkbox']");
	}

	public void click_on_save_staff_schedule() {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement el = find(By
				.cssSelector("div:not(#staff-btn-group-save-receptionist) > button[class='validation_button client_side_btn_m save-staff']"));
		jse.executeScript("arguments[0].click();", el);

		waitForPageToLoad(); // -> wait for page to load, otherwise search for
								// staffName will not work
	}

	public void click_on_save_receptionist() {
		clickOn(find(
				By.cssSelector("button[class='validation_button client_side_btn_m save-staff-info-update']"))
				.waitUntilPresent());
		waitForPageToLoad(); // -> wait for page to load, otherwise search for
								// staffName will not work
	}

	public void click_on_save_staff_edit() {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElementFacade el = find(By
				.cssSelector("div#edit-staff > form:first-child > div[class='modify-schedule input-calendis clearfix'] > div:nth-child(7) > button:nth-child(2)"));
		el.click();
		waitForPageToLoad(); // -> wait for page to load, otherwise search for
								// staffName will not work
	}

	public boolean is_staff_name_displayed_in_personal_section(String staffName) {
		boolean found = false;
		WebElementFacade el = getStaffElement(staffName).find(
				By.cssSelector("h4[class='service-name loc-address']"));
		if (el.getTextValue().trim().contains(staffName)) {

			System.out.println("staff Name is " + el.getTextValue().trim());
			found = true;
		}
		return found;
	}

	public boolean is_staff_email_displayed_in_personal_section(
			String staffName, String staffEmail) {
		boolean found = false;
		WebElementFacade el = getStaffElement(staffName).find(
				By.cssSelector("span:nth-of-type(2) > p:first-child"));
		if (el.getTextValue().trim().contains(staffEmail)) {

			System.out.println("staff email is " + el.getTextValue().trim());
			found = true;
		}

		return found;
	}

	public boolean is_staff_phone_displayed_in_personal_section(
			String staffName, String staffPhone) {
		boolean found = false;
		WebElementFacade el = getStaffElement(staffName).find(
				By.cssSelector("span:nth-of-type(2) > p:nth-child(2)"));
		if (el.getTextValue().trim().contains(staffPhone)) {

			System.out.println("staff phone is " + el.getTextValue().trim());
			found = true;
		}

		return found;
	}

	public boolean is_location_region_city_phone_displayed_in_location_section(
			String locationStreetAddress, String locationRegion,
			String locationCity, String locationPhone, String locationName) {
		boolean found = false;
		WebElementFacade el = getLocationWebElement(locationStreetAddress)
				.find(By.cssSelector("span:nth-of-type(1)"));
		String str = el.getTextValue().trim().toLowerCase();

		if (str.contains(locationRegion.toLowerCase())
				&& str.contains(locationCity.toLowerCase())
				&& str.contains(locationPhone)
				&& str.contains(locationName.toLowerCase())) {
			found = true;
		}

		return found;
	}

	public WebElementFacade getStaffElement(String staffName) {
		WebElementFacade staffContainer = null;

		List<WebElementFacade> staffList = findAll(By
				.cssSelector("div[id='staff'][class='settings-staff'] div[class='edit-information']"));
		for (WebElementFacade el : staffList) {
			if (el.find(
					By.cssSelector("h4[class='service-name loc-address']:first-child"))
					.getTextValue().trim().toLowerCase()
					.contains(staffName.toLowerCase())) {
				staffContainer = el;
				break;
			}

		}
		return staffContainer;
	}

	public WebElementFacade getLocationWebElement(String locationStreetAddress) {
		WebElementFacade locationContainer = null;
		List<WebElementFacade> locationList = findAll(By
				.cssSelector("div[class='location-view-content']"));
		for (WebElementFacade el : locationList) {
			if (el.find(
					By.cssSelector("h4[class='loc-address']:first-child> span:nth-of-type(2)"))
					.getTextValue().trim().toLowerCase()
					.contains(locationStreetAddress.toLowerCase())) {
				System.out.println("Returned element " + locationStreetAddress);
				locationContainer = el;
				break;
			}

		}
		return locationContainer;
	}

	public WebElementFacade getDomainWebElement(String locationStreetAddress) {
		WebElementFacade locationContainer = null;
		List<WebElementFacade> locationList = findAll(By
				.cssSelector("div#domains > div[class^='domain'] div[class='location-view']"));
		for (WebElementFacade el : locationList) {
			if (el.find(
					By.cssSelector("div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > span:first-child"))
					.getTextValue().trim().toLowerCase()
					.contains(locationStreetAddress.toLowerCase())) {
				System.out.println("Returned element " + locationStreetAddress);
				locationContainer = el;
				break;
			}

		}
		return locationContainer;
	}

	public boolean is_location_street_address_displayed(
			String locationStreetAddress) {
		boolean count = false;
		List<WebElementFacade> locationList = findAll(By
				.cssSelector("div[class='location-view-content']"));
		for (WebElementFacade el : locationList) {

			if (el.find(
					By.cssSelector("h4[class='loc-address']:first-child> span:nth-of-type(2)"))
					.getTextValue().trim().toLowerCase()
					.contains(locationStreetAddress.toLowerCase())) {
				System.out.println("Returned element " + locationStreetAddress);
				count = true;
				break;
			}

		}
		return count;
	}

	public boolean is_domain_name_displayed(String domainName) {
		boolean count = false;
		List<WebElementFacade> domainList = findAll(By
				.cssSelector("div#domains > div[class^='domain'] div[class='location-view']"));
		System.out.println("Size " + domainList.size());
		for (WebElementFacade el : domainList) {

			if (el.find(
					By.cssSelector("div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > span:first-child"))
					.getTextValue().trim().toLowerCase()
					.contains(domainName.toLowerCase())) {
				System.out.println("domain " + domainName
						+ "found in domain section");
				count = true;
				break;
			}

		}
		return count;
	}

	public void click_on_modify_link(String staffName) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElementFacade elem = getStaffElement(staffName);
		WebElementFacade staff = elem
				.find(By.cssSelector("h4[class='service-name loc-address'] > span[class='edit-del-options'] > a[class='edit-info update-staff'] > i:first-child"));
		jse.executeScript("arguments[0].scrollIntoView(true);", staff);
		jse.executeScript("arguments[0].click();", staff);

	}

	public void click_on_delete_staff_link(String staffName) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElementFacade elem = getStaffElement(staffName);
		WebElementFacade staff = elem
				.find(By.cssSelector("h4[class='service-name loc-address'] > span[class='edit-del-options'] > a[class='edit-info delete-staff'] > i:first-child"));
		jse.executeScript("arguments[0].scrollIntoView(true);", staff);
		jse.executeScript("arguments[0].click();", staff);
	}

	public void confirm_staff_deletion_in_modal() {
		clickOn(find(By.id("confirm-delete-item")));
		waitForPageToLoad();
	}

	public void click_on_modify_location_link(String locationAdress) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElementFacade elem = getLocationWebElement(locationAdress);
		WebElementFacade location = elem
				.find(By.cssSelector("div[class='edit-information'] > h4[class='loc-address'] > span:nth-child(3) > a[class='edit-info edit-location'] > i:first-child"));
		jse.executeScript("arguments[0].scrollIntoView(true);", location);
		jse.executeScript("arguments[0].click();", location);
	}

	public void click_on_delete_location_link(String locationAdress) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElementFacade elem = getLocationWebElement(locationAdress);
		WebElementFacade location = elem
				.find(By.cssSelector("div[class='edit-information'] > h4[class='loc-address'] > span:nth-child(3) > a[class='edit-info delete-location'] > i:first-child"));
		jse.executeScript("arguments[0].scrollIntoView(true);", location);
		jse.executeScript("arguments[0].click();", location);
	}

	public void click_on_delete_domain_link(String domainName) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElementFacade elem = getDomainWebElement(domainName);
		WebElementFacade domain = elem
				.find(By.cssSelector("div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > a:nth-of-type(1)"));
		jse.executeScript("arguments[0].scrollIntoView(true);", domain);
		jse.executeScript("arguments[0].click();", domain);
	}

	public void confirm_item_deletion_in_modal() {
		clickOn(find(By.id("confirm-delete-item")));
		waitForPageToLoad();
	}

}