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
				By.cssSelector("button[class='validation_button client_side_btn_m save-staff-info-update']"))
						.waitUntilPresent();
		scroll_in_view_then_click_on_element(element);
	}

	public void click_on_save_staff_edit() {
		WebElementFacade el = find(By.cssSelector(
				"div#edit-staff > form:first-child > div[class='modify-schedule input-calendis clearfix'] > div:nth-child(7) > button:nth-child(2)"));
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
