package ro.evozon.steps.serenity.business;

import ro.evozon.pages.business.LoggedInBusinessPage;
import ro.evozon.pages.business.SettingsPage;
import net.thucydides.core.annotations.Step;
import static org.assertj.core.api.Assertions.*;
import ro.evozon.AbstractSteps;

public class AddLocationSteps extends AbstractSteps {

	LoggedInBusinessPage loggedInBusinessPage;
	SettingsPage settingsPage;

	@Step
	public void click_on_location_left_menu() {
		settingsPage.select_location_from_left_menu();
	}

	@Step
	public void click_on_add_location() {
		settingsPage.click_on_add_new_location();
	}

	@Step
	public void fill_in_location_address(String address) {
		settingsPage.fill_in_location_street_address(address);
	}

	@Step
	public void fill_in_location_name(String name) {
		settingsPage.fill_in_location_name(name);
	}

	@Step
	public void fill_in_location_phone(String phone) {
		settingsPage.fill_in_location_phone(phone);

	}

	@Step
	public String select_random_region() {
		return settingsPage.select_random_region();
	}

	@Step
	public String select_random_city() {
		return settingsPage.select_random_city();
	}

	@Step
	public void click_on_set_location_schedule() {
		settingsPage.click_on_set_location_schedule();
	}

	@Step
	public void click_on_set_location_schdule_editing() {
		settingsPage.click_on_set_location_schedule_editing();
	}

	@Step
	public void select_days_of_week_for_location() {
		settingsPage.select_day_of_week_location_schedule();
	}

	@Step
	public void click_on_save_location_button() {
		settingsPage.click_on_save_location();
	}

	@Step
	public void verify_location_address_appears_in_location_section(
			String locationAddress) {
		softly.assertThat(
				settingsPage
						.is_location_street_address_displayed(locationAddress))
				.isTrue();
	}

	@Step
	public void verify_location_address_is_not_displayed_in_location_section(
			String locationAddress) {
		softly.assertThat(
				settingsPage
						.is_location_street_address_displayed(locationAddress))
				.isFalse();
	}

	@Step
	public void verify_location_details_appears_in_location_section(
			String locationStreetAddress, String locationRegion,
			String locationCity, String locationPhone, String locationName) {
		softly.assertThat(
				settingsPage
						.is_location_region_city_phone_displayed_in_location_section(
								locationStreetAddress, locationRegion,
								locationCity, locationPhone, locationName))
				.isTrue();
	}

	@Step
	public void click_on_modify_location_link(String locationAddress) {
		settingsPage.click_on_modify_location_link(locationAddress);
	}

	@Step
	public void click_on_delete_location_link(String locationAddress) {
		settingsPage.click_on_delete_location_link(locationAddress);
	}

	@Step
	public void confirm_location_deletion_in_modal() {
		settingsPage.confirm_location_deletion_in_modal();
	}

}