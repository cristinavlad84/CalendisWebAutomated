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

		clickOn(find(
				By.cssSelector("button[class='validation_button client_side_btn_l navigate-staff']"))
				.waitUntilClickable());
	}

	public void select_day_of_week_for_staff() {
		select_day_of_week_schedule(
				"div[class='schedule-table-wrap form-group clearfix']",
				"label[for^='checkbox']");
	}

	public void click_on_save_staff_schedule() {
		clickOn(find(
				By.cssSelector("div:not(#staff-btn-group-save-receptionist) > button[class='validation_button client_side_btn_m save-staff']"))
				.waitUntilPresent());
		waitForPageToLoad(); // -> wait for page to load, otherwise search for
								// staffName will not work
	}

	public void click_on_save_receptionist() {
		clickOn(find(
				By.cssSelector("div#staff-btn-group-save-receptionist > button[class='validation_button client_side_btn_m save-staff']"))
				.waitUntilPresent());
		waitForPageToLoad(); // -> wait for page to load, otherwise search for
								// staffName will not work
	}

	public boolean search_for_staff_name_in_personal_section(String staffName) {
		boolean found = false;
		List<WebElementFacade> staffList = findAll(By
				.cssSelector("div[id='staff'][class='settings-staff'] div[class='edit-information'] > h4[class='service-name loc-address']"));
		System.out.println("no of staff" + staffList.size());
		for (WebElementFacade el : staffList) {
			if (el.getTextValue().trim().contains(staffName)) {

				System.out.println("staff Name is " + el.getTextValue().trim());
				found = true;
				break;
			}

		}
		return found;
	}

}