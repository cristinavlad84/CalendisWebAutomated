package ro.evozon.pages.business;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.WebElementFacade;
import ro.evozon.AbstractPage;
import ro.evozon.tools.ConfigUtils;

public class PermissionPage extends AbstractPage {

	public void check_permission(String locator, String specialistName) {
		List<WebElementFacade> specialistNamesList = findAll(
				By.cssSelector("div[class^=" + "'" + locator + "'" + "] > ul > li > ul > li  a"));
		for (WebElementFacade el : specialistNamesList) {
			WebElementFacade optToFound = el.find(By.cssSelector("span[class='permission-name']"));
			String comparator = ConfigUtils.removeAccents(optToFound.getText().trim()).toLowerCase();
			String toCompare = ConfigUtils.removeAccents(specialistName.trim().toLowerCase());
			System.out.println("comparing " + comparator + " with " + toCompare);

			if (comparator.contentEquals(toCompare)) {

				if (!el.getAttribute("class").contains("jstree-clicked")) {
					System.out.println("Attribute flag " + el.getAttribute("class"));
					scroll_in_view_then_click_on_element(el);
					System.out.println("found");
					break;
				} else {
					// do nothing, exit
					break;
				}
			}
		}

	}

	public void check_permission_appointment_creation(String specialistName) {
		check_permission("staff-permissions type2", specialistName);
	}

	public void check_permission_appointment_edit_future(String specialistName) {
		check_permission("staff-permissions type4", specialistName);

	}

	public void check_permission_appointment_edit_past(String specialistName) {
		check_permission("staff-permissions type21", specialistName);
	}

	public void check_permission_calendar_view_for_others(String specialistName) {
		check_permission("staff-permissions type13", specialistName);
	}

	public void check_permission_appointment_creation_for_others(String specialistName) {
		check_permission("staff-permissions type15", specialistName);
	}

	public void check_permission_appointment_edit_future_for_others(String specialistName) {
		check_permission("staff-permissions type17", specialistName);

	}

	public void check_permission_appointment_edit_past_for_others(String specialistName) {
		check_permission("staff-permissions type23", specialistName);
	}

	public void check_permission_schedule(String specialistName) {
		check_permission("staff-permissions type6", specialistName);
	}

	public void check_permission_exceptions(String specialistName) {
		check_permission("staff-permissions type8", specialistName);
	}

	public void check_permission_clients_contacts(String specialistName) {
		check_permission("staff-permissions type10", specialistName);
	}

	public void check_permission_clients_database_view(String specialistName) {
		check_permission("staff-permissions type12", specialistName);
	}

	public void check_permission_clients_info_edit(String specialistName) {
		check_permission("staff-permissions type19", specialistName);
	}
}
