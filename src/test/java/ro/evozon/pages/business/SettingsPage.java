package ro.evozon.pages.business;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.serenitybdd.core.annotations.findby.By;
import ro.evozon.AbstractPage;
import net.serenitybdd.core.pages.WebElementFacade;

public class SettingsPage extends AbstractPage {

	public void logout_link_should_be_displayed() {
		find(By.id("nav-disconect")).shouldBeVisible();
	}

	public void select_location_from_left_menu() {
		scroll_in_view_then_click_on_element(find(By.id("settings_locations_tab")));
	}

	public void select_service_from_left_menu() {
		scroll_in_view_then_click_on_element(find(By.id("settings_services_tab")));
	}
	public void select_groups_from_left_menu(){
		scroll_in_view_then_click_on_element(find(By.id("settings_group_tab")));
	}
	public void click_on_price_list_tab() {
		scroll_in_view_then_click_on_element(find(By.id("price-lists")));
	}
	public void click_on_services_packet_tab() {
		scroll_in_view_then_click_on_element(find(By.id("bundled-services")));
	}

	public void select_domain_from_left_menu() {
		scroll_in_view_then_click_on_element(find(By.id("settings_domains_tab")));
	}

	public void select_voucher_codes_from_left_menu() {
		scroll_in_view_then_click_on_element(find(By.id("add-new-partner-voucher")));
	}

	public void confirm_item_deletion_in_modal() {
		click_on_element(find(By.cssSelector("button#confirm-delete-item")));
		
	}

	

	public void wait_for_save_edits_popup_disappear() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#myAlert")));
	}
}