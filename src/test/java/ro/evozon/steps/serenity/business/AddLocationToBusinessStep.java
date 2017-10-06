package ro.evozon.steps.serenity.business;

import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.BusinessWizardPage;
import ro.evozon.pages.business.DomainPage;
import ro.evozon.pages.business.LocationPage;
import ro.evozon.pages.business.SettingsPage;
import ro.evozon.tools.utils.Time24HoursValidator;

public class AddLocationToBusinessStep extends AbstractSteps {
	LocationPage locationPage;
	SettingsPage settingsPage;
	BusinessWizardPage businessWizardPage;

	@Step
	public void click_on_add_location() {
		locationPage.click_on_add_new_location();
	}

	@Step
	public void fill_in_location_address(String address) {
		locationPage.fill_in_location_street_address(address);
	}

	@Step
	public void fill_in_location_name(String name) {
		locationPage.fill_in_location_name(name);
	}

	@Step
	public void fill_in_location_phone(String phone) {
		locationPage.fill_in_location_phone(phone);

	}

	@Step
	public String select_random_location_region() {
		return locationPage.select_random_location_region();
	}

	@Step
	public void select_location_region(String region) {
		locationPage.select_location_region(region);
	}

	@Step
	public String select_random_location_city() {
		return locationPage.select_random_location_city();
	}

	@Step
	public void select_location_city(String locationCity) {
		locationPage.select_location_city(locationCity);
	}

	@Step
	public void click_on_set_location_schedule() {
		locationPage.click_on_set_location_schedule();
	}

	@Step
	public void check_schedule_day_of_week_location(String rangeHours, int position) {
		Time24HoursValidator time24HoursValidator = new Time24HoursValidator();
		String[] hours = rangeHours.split("-");
		String startHour = time24HoursValidator.getHourFromString(hours[0]);// validate
																			// hour
																			// is
																			// correct
																			// format
		String endHour = time24HoursValidator.getHourFromString(hours[1]); // validate
																			// hour
																			// is
																			// correct
		System.out.println("endHour = " + endHour); // format

		locationPage.waitForWithRefresh();
		locationPage.select_day_of_week_location_schedule(position);
		businessWizardPage.select_startHour(startHour, position);
		businessWizardPage.select_endHour(endHour, position);

	}

	@Step
	public void click_on_set_location_schdule_editing() {
		locationPage.click_on_set_location_schedule_editing();
	}

	@Step
	public void select_days_of_week_for_location() {
		locationPage.select_day_of_week_location_schedule();
	}

	@Step
	public void click_on_save_location_button() {
		locationPage.click_on_save_location();
	}

	@Step
	public void verify_location_address_appears_in_location_section(String locationAddress) {

		softly.assertThat(locationPage.is_location_street_address_displayed(locationAddress)).as("location street")
				.isTrue();
	}

	@Step
	public void verify_location_address_is_not_displayed_in_location_section(String locationAddress) {

		softly.assertThat(locationPage.is_location_street_address_displayed(locationAddress)).as("location street")
				.isFalse();
	}

	@Step
	public void verify_location_details_appears_in_location_section(String locationStreetAddress, String locationRegion,
			String locationCity, String locationPhone, String locationName) {

		softly.assertThat(
				locationPage.is_location_detail_displayed_in_location_section(locationStreetAddress, locationRegion))
				.as("locationRegion").isTrue();
		softly.assertThat(
				locationPage.is_location_detail_displayed_in_location_section(locationStreetAddress, locationCity))
				.as("locationCity").isTrue();
		softly.assertThat(
				locationPage.is_location_detail_displayed_in_location_section(locationStreetAddress, locationPhone))
				.as("locationPhone").isTrue();
		softly.assertThat(
				locationPage.is_location_detail_displayed_in_location_section(locationStreetAddress, locationName))
				.as("locationName").isTrue();
	}

	@Step
	public void click_on_modify_location_link(String locationAddress) {
		locationPage.click_on_modify_location_link(locationAddress);
	}

	@Step
	public void click_on_delete_location_link(String locationAddress) {
		locationPage.click_on_delete_location_link(locationAddress);
	}
}
