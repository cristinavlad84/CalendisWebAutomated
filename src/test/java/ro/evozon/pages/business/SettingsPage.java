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
		clickOn(find(By.id("settings_locations_tab")));
	}

	public void select_service_from_left_menu() {
		clickOn(find(By.id("settings_services_tab")));
	}

	public void click_on_price_list_tab() {
		scroll_in_view_then_click_on_element(find(By.id("price-lists")));
	}

	public void select_domain_from_left_menu() {
		click_on_element(find(By.id("settings_domains_tab")));
	}

	public void confirm_item_deletion_in_modal() {
		clickOn(find(By.id("confirm-delete-item")));
		waitForPageToLoad();
	}

	public void waitforAllert() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='main']/div/div/div[@id='myAlert']")));

	}

	public void wait_for_save_edits_popup_disappear() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#myAlert")));
	}
}