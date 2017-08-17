package ro.evozon.pages.business;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.WebElementFacade;
import ro.evozon.AbstractPage;
import ro.evozon.tools.ConfigUtils;

public class StaffPage extends AbstractPage {

	public void click_on_add_new_staff() {
		WebElementFacade elem = find(By.id("new-staff"));
		scroll_in_view_then_click_on_element(elem);
	}

	public void select_staff_type_to_be_added(String staffType) {
		List<WebElementFacade> myRadioList = findAll(By.cssSelector("label[class='radio-inline radio-role'] > input"));

		WebElementFacade optToSelect = null;
		for (WebElementFacade opt : myRadioList) {
			System.out.println("to print " + opt.getValue().trim());
			if (ConfigUtils.removeAccents(opt.getValue().trim()).contentEquals(staffType)) {

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

	public void check_in_default_location_for_staff() {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement el = find(By.cssSelector(
				"li[class='jstree-node staff-location jstree-open jstree-last'] > a[class='jstree-anchor'] >  i[class='jstree-icon jstree-checkbox']"));
		jse.executeScript("arguments[0].click();", el);
	}

	public WebElementFacade get_location_container(String locationName) {
		WebElementFacade container = null;
		List<WebElementFacade> locationsList = findAll(By.cssSelector(
				"div[class*='col-md-8 specialists-location-services subservices'] > ul[class='jstree-container-ul'] > li"));
		System.out.println("locations list size is " + locationsList.size());
		for (WebElementFacade el : locationsList) {
			WebElementFacade loc = el.find(By.cssSelector("a > span:nth-of-type(1) > span:nth-of-type(2)"));
			if (ConfigUtils.removeAccents(loc.getText().trim()).toLowerCase().contains(locationName.toLowerCase())) {
				System.out
						.println("found locationNAme " + ConfigUtils.removeAccents(loc.getText().trim()).toLowerCase());
				container = el;
				break;
			}
		}
		System.out.println("data id is" + container.getAttribute("data-id"));

		return container;
	}

	public WebElementFacade get_domain_container(String locationName, String domainName) {
		WebElementFacade domainEl = null;
		WebElementFacade container = get_location_container(locationName);
		System.out.println(container.getAttribute("data-id"));
		List<WebElementFacade> domainsList = container.thenFindAll(By.cssSelector("ul > li"));
		System.out.println("domain list size is " + domainsList.size());
		for (WebElementFacade el : domainsList) {
			WebElementFacade dom = el.find(By.cssSelector("a  > div"));
			if (ConfigUtils.removeAccents(dom.getText().toLowerCase()).contains(domainName.toLowerCase())) {
				domainEl = el;
				break;
			}
		}
		return domainEl;
	}

	public WebElementFacade get_service_container(String locationName, String domainName, String serviceName) {
		WebElementFacade serviceContainer = null;
		WebElementFacade domainContainer = get_domain_container(locationName, domainName);
		// expand services
		WebElementFacade expandEl = domainContainer.find(By.cssSelector("i:first-child"));
		scroll_in_view_then_click_on_element(expandEl);
		List<WebElementFacade> servicesList = get_domain_container(locationName, domainName)
				.thenFindAll(By.cssSelector("ul > li"));
		for (WebElementFacade el : servicesList) {
			WebElementFacade service = el.find(By.cssSelector("a  > div > p"));

			if (ConfigUtils.removeAccents(service.getText()).toLowerCase().contains(serviceName.toLowerCase())) {
				System.out.println("service found " + ConfigUtils.removeAccents(service.getText()));
				serviceContainer = el;
				break;
			}
		}
		return serviceContainer;
	}

	public WebElementFacade get_packet_container(String packetName) {
		WebElementFacade packetContainer = null;

		List<WebElementFacade> packetList = findAll(By.cssSelector("div[id='staff-bundles'] > label"));
		for (WebElementFacade el : packetList) {
			List<WebElementFacade> packetL = el.thenFindAll(By.tagName("span"));
			WebElementFacade packet = packetL.get(1);
			if (ConfigUtils.removeAccents(packet.getText()).toLowerCase().contains(packetName.toLowerCase())) {
				System.out.println("packet found " + ConfigUtils.removeAccents(packet.getText()));
				packetContainer = el;
				break;
			}
			else {
				System.out.println("packet not found");
			}
		}
		return packetContainer;
	}

	public void check_location(String locationName) {
		WebElementFacade checkbox = null;
		WebElementFacade container = get_location_container(locationName);
		System.out.println("js tree for check location" + container.find(By.tagName("a")).getAttribute("class"));
		if (!container.find(By.tagName("a")).getAttribute("class").contains("jstree-clicked")) {
			checkbox = container.find(By.cssSelector("a > i"));
			checkbox.click();
		}

	}

	public void check_domain(String locationName, String domainName) {
		WebElementFacade domainEl = get_domain_container(locationName, domainName);
		WebElementFacade checkbox = null;
		if (!domainEl.find(By.tagName("a")).getAttribute("class").contains("jstree-clicked")) {
			checkbox = domainEl.find(By.cssSelector("a > i"));
			checkbox.click();
		}
	}

	public void check_service(String locationName, String domainName, String serviceName) {
		WebElementFacade serviceEl = get_service_container(locationName, domainName, serviceName);
		WebElementFacade checkbox = null;
		if (!serviceEl.find(By.tagName("a")).getAttribute("class").contains("jstree-clicked")) {
			checkbox = serviceEl.find(By.cssSelector("a > i:first-child"));
			click_on_element(checkbox);
		}
	}

	public void check_packet(String packetName) {
		WebElementFacade packetEl = get_packet_container(packetName);
		WebElementFacade checkbox = null;
		List<WebElementFacade> packetL = packetEl.thenFindAll(By.cssSelector("span"));
		checkbox = packetL.get(0);
		scroll_in_view_then_click_on_element(checkbox);
		

	}

	public void click_on_set_staff_schedule() {

		WebElementFacade el = find(
				By.cssSelector("button[class='validation_button client_side_btn_l navigate-staff']"));
		click_on_element(el);
	}

	public void select_day_of_week_for_staff() {
		select_day_of_week_schedule("div[class='schedule-table-wrap form-group clearfix']", "label[for^='checkbox']");
	}

	public void click_on_save_staff_schedule() {

		WebElementFacade el = find(By.cssSelector(
				"div:not(#staff-btn-group-save-receptionist) > button[class='validation_button client_side_btn_m save-staff']"));
		scroll_in_view_then_click_on_element(el);

	}

	public void click_on_save_receptionist() {
		WebElementFacade element = find(
				By.cssSelector("button[class='validation_button client_side_btn_m save-staff']")).waitUntilPresent();
		scroll_in_view_then_click_on_element(element);
	}

	public void click_on_save_staff_edit() {
		WebElementFacade el = find(By.cssSelector(
				"button[class='validation_button client_side_btn_m save-staff-info-update']"));
		scroll_in_view_then_click_on_element(el);
	}

	public boolean is_staff_name_displayed_in_personal_section(String staffName) {
		return is_element_present_in_elements_list(
				"div[id='staff'][class='settings-staff'] div[class='edit-information']",
				"h4[class='service-name loc-address']", staffName);
	}

	public boolean is_staff_email_displayed_in_personal_section(String staffName, String staffEmail) {
		WebElementFacade elementContainer = getStaffElement(staffName);
		return is_item_displayed_through_found_element(elementContainer, staffEmail,
				"span:nth-of-type(2) > p:first-child");
	}

	public boolean is_staff_phone_displayed_in_personal_section(String staffName, String staffPhone) {
		WebElementFacade elementContainer = getStaffElement(staffName);
		return is_item_displayed_through_found_element(elementContainer, staffPhone,
				"span:nth-of-type(2) > p:nth-child(2)");
	}

	public WebElementFacade getStaffElement(String staffName) {
		return get_element_from_elements_list("div[id='staff'][class='settings-staff'] div[class='edit-information']",
				"h4[class='service-name loc-address']:first-child", staffName);
	}

	public void click_on_modify_link(String staffName) {
		WebElementFacade elem = getStaffElement(staffName);
		WebElementFacade staff = elem.find(By.cssSelector(
				"h4[class='service-name loc-address'] > span[class='edit-del-options'] > a[class='edit-info update-staff'] > i:first-child"));
		scroll_in_view_then_click_on_element(staff);

	}

	public void click_on_delete_staff_link(String staffName) {
		WebElementFacade elem = getStaffElement(staffName);
		WebElementFacade staff = elem.find(By.cssSelector(
				"h4[class='service-name loc-address'] > span[class='edit-del-options'] > a[class='edit-info delete-staff'] > i:first-child"));
		scroll_in_view_then_click_on_element(staff);
	}
}
