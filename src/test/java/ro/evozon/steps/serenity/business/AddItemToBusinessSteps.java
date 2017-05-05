package ro.evozon.steps.serenity.business;

import ro.evozon.pages.business.LoggedInBusinessPage;
import ro.evozon.pages.business.SettingsPage;
import ro.evozon.tools.models.PriceList;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ro.evozon.AbstractSteps;

public class AddItemToBusinessSteps extends AbstractSteps {

	LoggedInBusinessPage loggedInBusinessPage;
	SettingsPage settingsPage;

	@Step
	public void click_on_location_left_menu() {
		settingsPage.select_location_from_left_menu();
	}

	@Step
	public void click_on_sevice_left_menu() {
		settingsPage.select_service_from_left_menu();
	}

	@Step
	public void click_on_domain_left_menu() {
		settingsPage.select_domain_from_left_menu();
	}

	@Step
	public void prices_lists_should_be_equal(List<String> pricesI, List<String> pricesF) {
		softly.assertThat(pricesI.equals(pricesF));
	}

	@Step
	public void confirm_item_deletion_in_modal() {
		settingsPage.confirm_item_deletion_in_modal();
	}

	@Step
	public void wait_for_saving_alert() {
		settingsPage.waitforAllert();
	}

}