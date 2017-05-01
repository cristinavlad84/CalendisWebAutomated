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

	public void select_service_from_left_menu() {
		clickOn(find(By.id("settings_services_tab")));
	}

	public void select_domain_from_left_menu() {
		clickOn(find(By.id("settings_domains_tab")));
	}

	public void click_on_add_new_location() {

		WebElementFacade el = find(By.id("new-location"));
		scroll_in_view_then_click_on_element(el);
	}

	public void click_on_add_new_service() {
		WebElementFacade el = find(By
				.cssSelector("a[class='add-more new-service']"));
		scroll_in_view_then_click_on_element(el);
	}

	public void click_on_add_new_domain() {
		WebElementFacade addNewDomainElement = find(By.id("add-new-domain"));
		scroll_in_view_then_click_on_element(addNewDomainElement);
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

	public void fill_in_service_name(String name) {
		enter(name).into(find(By.id("service-name")));
	}

	public void fill_in_service_duration(String duration) {
		enter(duration)
				.into(find(By
						.cssSelector("input[class='pick-duration-settings-input pull-left']")));
	}

	public void fill_in_service_max_persons(String maxPers) {
		enter(maxPers)
				.into(find(By
						.cssSelector("input[class='pick-user-settings-input pull-left']")));
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
		List<WebElementFacade> singleLocationList = findAll(By
				.cssSelector("input[class='pick-me new-sel-settings form-control']"));
		List<WebElementFacade> multipleLocationsList = findAll(By
				.cssSelector("select#settings-select-location"));
		if (singleLocationList.size() > 0) {
			locationElem = singleLocationList.get(0);
		} else if (multipleLocationsList.size() > 0) {
			locationElem = multipleLocationsList.get(0);
		}

		return locationElem;
	}

	public void fill_in_domain_name(String name) {
		enter(name).into(find(By.id("settings-input-domain")));
	}

	public void fill_in_service_price(String price) {
		enter(price).into(find(By.id("service-price")));
	}

	public String select_random_service_duration() {
		return select_random_option_in_dropdown(find(
				By.cssSelector("select[class='pick-me pick-duration-settings pull-left']"))
				.waitUntilVisible());
	}

	public String select_random_max_persons_per_service() {
		return select_random_option_in_dropdown(find(
				By.cssSelector("select[class='pick-me pick-user-settings']"))
				.waitUntilVisible());
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
		WebElementFacade elem = find(By
				.cssSelector("button[class='validation_button client_side_btn_m save-location']"));
		scroll_in_view_then_click_on_element(elem);
	}

	public void click_on_save_service_edit_form() {
		WebElementFacade elem = find(By
				.cssSelector("button[class='validation_button client_side_btn_m save-new-service']"));
		scroll_in_view_then_click_on_element(elem);
	}

	public void click_on_save_domain() {

		WebElementFacade elem = find(By
				.cssSelector("button[class='validation_button client_side_btn_m save-new-domain']"));
		scroll_in_view_then_click_on_element(elem);

	}

	public void click_on_save_service() {
		WebElementFacade elem = find(By
				.cssSelector("button[class='validation_button client_side_btn_m save-new-service']"));
		scroll_in_view_then_click_on_element(elem);
	}

	public void click_on_set_location_schedule_editing() {
		WebElementFacade elem = find(By
				.cssSelector("button[class='validation_button client_side_btn_l navigate-location']"));
		scroll_in_view_then_click_on_element(elem);
	}

	public String select_random_city() {
		return select_random_option_in_dropdown(find(By
				.cssSelector("select#settings-select-city")));
	}

	public void click_on_add_new_staff() {
		WebElementFacade elem = find(By.id("new-staff"));
		scroll_in_view_then_click_on_element(elem);
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

	public List<WebElementFacade> verify_single_or_multiple_location() {
		List<WebElementFacade> domainList = null;
		List<WebElementFacade> singleLocationList = findAll(By
				.cssSelector("div[class^='modify-service input-calendis form-services '] > div:nth-of-type(2) > div:nth-of-type(2) > input"));
		List<WebElementFacade> multipleLocationList = findAll(By
				.cssSelector("div[class^='modify-service input-calendis form-services '] > div:nth-of-type(2) > div:nth-of-type(2) > select"));
		if (singleLocationList.size() > 0) {
			domainList = singleLocationList;
		} else if (multipleLocationList.size() > 0) {
			domainList = multipleLocationList;
		}
		return domainList;
	}

	public String select_domain_for_service() {
		String str = "";
		List<WebElementFacade> elemList = verify_single_or_multiple_location();
		if (elemList.get(0).getTagName().contentEquals("input")) {
			// do nothing
		} else if (elemList.get(0).getTagName().contentEquals("select")) {
			str = select_random_option_in_dropdown(elemList.get(0));
		}
		return str;
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

		WebElementFacade el = find(By
				.cssSelector("button[class='validation_button client_side_btn_l navigate-staff']"));
		click_on_element(el);
	}

	public void select_day_of_week_for_staff() {
		select_day_of_week_schedule(
				"div[class='schedule-table-wrap form-group clearfix']",
				"label[for^='checkbox']");
	}

	public void click_on_save_staff_schedule() {

		WebElementFacade el = find(By
				.cssSelector("div:not(#staff-btn-group-save-receptionist) > button[class='validation_button client_side_btn_m save-staff']"));
		scroll_in_view_then_click_on_element(el);

	}

	public void click_on_save_receptionist() {
		WebElementFacade element = find(
				By.cssSelector("button[class='validation_button client_side_btn_m save-staff-info-update']"))
				.waitUntilPresent();
		scroll_in_view_then_click_on_element(element);
	}

	public void click_on_save_staff_edit() {
		WebElementFacade el = find(By
				.cssSelector("div#edit-staff > form:first-child > div[class='modify-schedule input-calendis clearfix'] > div:nth-child(7) > button:nth-child(2)"));
		scroll_in_view_then_click_on_element(el);
	}

	public boolean is_staff_name_displayed_in_personal_section(String staffName) {
		WebElementFacade elementContainer = getStaffElement(staffName);
		return is_item_displayed_through_elements(elementContainer, staffName,
				"h4[class='service-name loc-address']");
	}

	public boolean is_staff_email_displayed_in_personal_section(
			String staffName, String staffEmail) {
		WebElementFacade elementContainer = getStaffElement(staffName);
		return is_item_displayed_through_elements(elementContainer, staffEmail,
				"span:nth-of-type(2) > p:first-child");
	}

	public boolean is_staff_phone_displayed_in_personal_section(
			String staffName, String staffPhone) {
		WebElementFacade elementContainer = getStaffElement(staffName);
		return is_item_displayed_through_elements(elementContainer, staffPhone,
				"span:nth-of-type(2) > p:nth-child(2)");
	}

	public boolean is_location_detail_displayed_in_location_section(
			String locationStreetAddress, String locationDetail) {
		WebElementFacade elementContainer = getLocationWebElement(locationStreetAddress);
		return is_item_displayed_through_elements(elementContainer,
				locationDetail, "span[class='location-phone']");
	}

	public boolean is_service_detail_displayed_in_service_section(
			String serviceName, String serviceDetail) {
		WebElementFacade elementContainer = getServiceWebElement(serviceName);
		return is_item_displayed_through_elements(elementContainer,
				serviceDetail, "span[class='location-phone']");
	}

	public WebElementFacade getStaffElement(String staffName) {
		return get_element_from_elements_list(
				"div[id='staff'][class='settings-staff'] div[class='edit-information']",
				"h4[class='service-name loc-address']:first-child", staffName);
	}

	public WebElementFacade getLocationWebElement(String locationStreetAddress) {
		return get_element_from_elements_list(
				"div[class='location-view-content']",
				"h4[class='loc-address']:first-child> span:nth-of-type(2)",
				locationStreetAddress);
	}

	public WebElementFacade getServiceWebElement(String serviceName) {
		return get_element_from_elements_list(
				"div[class^='saved-services-details'] div[class='edit-information']",
				"h4[class='loc-address service-name']:first-child > span:nth-of-type(1)",
				serviceName);
	}

	public WebElementFacade getDomainWebElement(String locationStreetAddress) {
		return get_element_from_elements_list(
				"div#domains > div[class^='domain'] div[class='location-view']",
				"div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > span:first-child",
				locationStreetAddress);
	}

	public boolean is_location_street_address_displayed(
			String locationStreetAddress) {
		WebElementFacade serviceWebElement = getLocationWebElement(locationStreetAddress);
		return is_item_displayed_through_elements(serviceWebElement,
				locationStreetAddress,
				"h4[class='loc-address']:first-child> span:nth-of-type(2)");
	}

	public boolean is_service_name_displayed(String serviceName) {
		WebElementFacade serviceWebElement = getServiceWebElement(serviceName);
		return is_item_displayed_through_elements(serviceWebElement,
				serviceName,
				"h4[class='loc-address service-name']:first-child> span:nth-of-type(1)");
	}

	public boolean is_domain_name_displayed(String domainName) {
		boolean count = false;
		WebElementFacade domainWebElement = getDomainWebElement(domainName);
		return is_item_displayed_through_elements(
				domainWebElement,
				domainName,
				"div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > span:first-child");
	}

	public void click_on_modify_link(String staffName) {
		WebElementFacade elem = getStaffElement(staffName);
		WebElementFacade staff = elem
				.find(By.cssSelector("h4[class='service-name loc-address'] > span[class='edit-del-options'] > a[class='edit-info update-staff'] > i:first-child"));
		scroll_in_view_then_click_on_element(staff);

	}

	public void click_on_delete_staff_link(String staffName) {
		WebElementFacade elem = getStaffElement(staffName);
		WebElementFacade staff = elem
				.find(By.cssSelector("h4[class='service-name loc-address'] > span[class='edit-del-options'] > a[class='edit-info delete-staff'] > i:first-child"));
		scroll_in_view_then_click_on_element(staff);
	}

	public void confirm_staff_deletion_in_modal() {
		clickOn(find(By.id("confirm-delete-item")));
		waitForPageToLoad();
	}

	public void click_on_modify_service_link(String serviceName) {
		WebElementFacade elem = getServiceWebElement(serviceName);
		WebElementFacade service = elem
				.find(By.cssSelector("div[class='edit-information'] > h4[class='loc-address service-name'] > span:nth-child(2) > a[class='edit-info update-service'] > i:first-child"));
		scroll_in_view_then_click_on_element(service);
	}

	public void click_on_modify_location_link(String locationAdress) {
		WebElementFacade elem = getLocationWebElement(locationAdress);
		WebElementFacade location = elem
				.find(By.cssSelector("div[class='edit-information'] > h4[class='loc-address'] > span:nth-child(3) > a[class='edit-info edit-location'] > i:first-child"));
		scroll_in_view_then_click_on_element(location);
	}

	public void click_on_delete_location_link(String locationAdress) {
		WebElementFacade elem = getLocationWebElement(locationAdress);
		WebElementFacade location = elem
				.find(By.cssSelector("div[class='edit-information'] > h4[class='loc-address'] > span:nth-child(3) > a[class='edit-info delete-location'] > i:first-child"));
		scroll_in_view_then_click_on_element(location);
	}

	public void click_on_delete_service_link(String serviceName) {
		WebElementFacade elem = getServiceWebElement(serviceName);
		WebElementFacade service = elem
				.find(By.cssSelector("div[class='edit-information'] > h4[class='loc-address service-name'] > span:nth-child(2) > a[class='edit-info delete-service'] > i:first-child"));
		scroll_in_view_then_click_on_element(service);
	}

	public void click_on_delete_domain_link(String domainName) {
		WebElementFacade elem = getDomainWebElement(domainName);
		WebElementFacade domain = elem
				.find(By.cssSelector("div[class='edit-information']:first-child> div[class='domain-name']:nth-child(1) > a:nth-of-type(1)"));
		scroll_in_view_then_click_on_element(domain);
	}

	public void confirm_item_deletion_in_modal() {
		clickOn(find(By.id("confirm-delete-item")));
		waitForPageToLoad();
	}

}