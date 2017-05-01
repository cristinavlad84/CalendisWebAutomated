package ro.evozon.steps.serenity.business;

import ro.evozon.pages.business.LoggedInBusinessPage;
import ro.evozon.pages.business.SettingsPage;
import net.thucydides.core.annotations.Step;
import static org.assertj.core.api.Assertions.*;
import ro.evozon.AbstractSteps;

public class AddItemToBusinessSteps extends AbstractSteps {

	LoggedInBusinessPage loggedInBusinessPage;
	SettingsPage settingsPage;

	@Step
	public void fill_in_staff_name(String staffName) {
		settingsPage.fill_in_staff_name(staffName);
	}

	@Step
	public void fill_in_staff_email(String staffEmail) {
		settingsPage.fill_in_staff_email(staffEmail);
	}

	@Step
	public void fill_in_staff_phone(String staffPhone) {
		settingsPage.fill_in_staff_phone(staffPhone);
	}

	@Step
	public void click_on_add_new_staff_button() {
		settingsPage.click_on_add_new_staff();
	}

	@Step
	public void is_staff_name_displayed_in_personal_section(String staffName) {
		softly.assertThat(
				settingsPage
						.is_staff_name_displayed_in_personal_section(staffName))
				.as("staff Name").isTrue();
	}

	@Step
	public void is_staff_email_displayed_in_personal_section(String staffEmail) {

	}

	public void is_staff_phone_displayed_in_personal_section(String staffPhone) {

	}

	@Step
	public void click_on_modify_staff_link(String staffName) {
		settingsPage.click_on_modify_link(staffName);
	}

	@Step
	public void click_on_delete_staff_link(String staffName) {
		settingsPage.click_on_delete_staff_link(staffName);
	}

	@Step
	public void confirm_staff_deletion() {
		settingsPage.confirm_staff_deletion_in_modal();
	}

	@Step
	public void select_staff_type_to_add(String staffType) {
		settingsPage.select_staff_type_to_be_added(staffType);
	}

	@Step
	public String select_domain_to_add_service() {
		return settingsPage.select_domain_for_service();
	}

	@Step
	public void check_default_location() {
		settingsPage.check_in_default_location();
	}

	@Step
	public void click_on_set_staff_schedule() {
		settingsPage.click_on_set_staff_schedule();
	}

	@Step
	public void select_day_of_week_for_staff_schedule() {
		settingsPage.select_day_of_week_for_staff();
	}

	@Step
	public void click_on_save_staff_schedule() {
		settingsPage.click_on_save_staff_schedule();
	}

	@Step
	public void click_on_save_receptionist() {
		settingsPage.click_on_save_receptionist();
	}

	@Step
	public void click_on_save_staff_edits() {
		settingsPage.click_on_save_staff_edit();
	}

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
	public void click_on_add_domain() {
		settingsPage.click_on_add_new_domain();
	}

	@Step
	public void click_on_add_location() {
		settingsPage.click_on_add_new_location();
	}

	@Step
	public void click_on_add_service() {
		settingsPage.click_on_add_new_service();
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
	public void fill_in_service_name(String name) {
		settingsPage.fill_in_service_name(name);
	}

	@Step
	public void fill_in_service_max_persons(String maxPersons) {
		settingsPage.fill_in_service_max_persons(maxPersons);
	}

	@Step
	public void fill_in_service_duration(String serviceDuration) {
		settingsPage.fill_in_service_duration(serviceDuration);
	}

	@Step
	public String select_random_location_in_domain_form() {
		return settingsPage.select_random_location_domain_form();
	}

	@Step
	public void fill_in_domain_name(String name) {
		settingsPage.fill_in_domain_name(name);
	}

	@Step
	public void fill_in_location_phone(String phone) {
		settingsPage.fill_in_location_phone(phone);

	}

	@Step
	public void fill_in_service_price(String price) {
		settingsPage.fill_in_service_price(price);

	}

	@Step
	public String select_random_service_duration() {
		return settingsPage.select_random_service_duration();
	}

	@Step
	public String select_random_max_persons_per_service() {
		return settingsPage.select_random_max_persons_per_service();
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
	public void click_on_save_service_edit_form() {
		settingsPage.click_on_save_service_edit_form();
	}

	@Step
	public void click_on_save_domain_button() {
		settingsPage.click_on_save_domain();
	}

	@Step
	public void click_on_save_service_button() {
		settingsPage.click_on_save_service();
	}

	@Step
	public void verify_location_address_appears_in_location_section(
			String locationAddress) {

		softly.assertThat(
				settingsPage
						.is_location_street_address_displayed(locationAddress))
				.as("location street").isTrue();
	}

	@Step
	public void verify_service_name_appears_in_service_section(
			String serviceName) {

		softly.assertThat(settingsPage.is_service_name_displayed(serviceName))
				.as("service name").isTrue();
	}

	@Step
	public void verify_service_name_not_displayed_in_service_section(
			String serviceName) {

		softly.assertThat(settingsPage.is_service_name_displayed(serviceName))
				.as("service name").isFalse();
	}

	@Step
	public void verify_domain_name_appears_in_domain_section(String domainName) {

		softly.assertThat(settingsPage.is_domain_name_displayed(domainName))
				.as("domain Name").isTrue();
	}

	@Step
	public void verify_domain_not_displayed_in_domain_section(String domainName) {

		softly.assertThat(settingsPage.is_domain_name_displayed(domainName))
				.as("domain Name").as("should not be displayed").isFalse();
	}

	@Step
	public void verify_location_address_is_not_displayed_in_location_section(
			String locationAddress) {

		softly.assertThat(
				settingsPage
						.is_location_street_address_displayed(locationAddress))
				.as("location street").isFalse();
	}

	@Step
	public void verify_location_details_appears_in_location_section(
			String locationStreetAddress, String locationRegion,
			String locationCity, String locationPhone, String locationName) {

		softly.assertThat(
				settingsPage.is_location_detail_displayed_in_location_section(
						locationStreetAddress, locationRegion))
				.as("locationRegion").isTrue();
		softly.assertThat(
				settingsPage.is_location_detail_displayed_in_location_section(
						locationStreetAddress, locationCity))
				.as("locationCity").isTrue();
		softly.assertThat(
				settingsPage.is_location_detail_displayed_in_location_section(
						locationStreetAddress, locationPhone))
				.as("locationPhone").isTrue();
		softly.assertThat(
				settingsPage.is_location_detail_displayed_in_location_section(
						locationStreetAddress, locationName))
				.as("locationName").isTrue();
	}

	@Step
	public void verify_service_details_appears_in_service_section(
			String serviceName, String servicePrice, String serviceDuration,
			String serviceMaxPersons) {

		softly.assertThat(
				settingsPage.is_service_detail_displayed_in_service_section(
						serviceName, servicePrice)).as("servicePrice").isTrue();
		softly.assertThat(
				settingsPage.is_service_detail_displayed_in_service_section(
						serviceName, serviceDuration)).as("serviceDuration")
				.isTrue();
		softly.assertThat(
				settingsPage.is_service_detail_displayed_in_service_section(
						serviceName, serviceMaxPersons))
				.as("serviceMaxPersons").isTrue();

	}

	@Step
	public void click_on_modify_location_link(String locationAddress) {
		settingsPage.click_on_modify_location_link(locationAddress);
	}

	@Step
	public void click_on_modify_service_link(String serviceName) {
		settingsPage.click_on_modify_service_link(serviceName);
	}

	@Step
	public void click_on_delete_location_link(String locationAddress) {
		settingsPage.click_on_delete_location_link(locationAddress);
	}

	@Step
	public void click_on_delete_service_link(String serviceName) {
		settingsPage.click_on_delete_service_link(serviceName);
	}

	@Step
	public void click_on_delete_domain(String domainName) {
		settingsPage.click_on_delete_domain_link(domainName);
	}

	@Step
	public void confirm_item_deletion_in_modal() {
		settingsPage.confirm_item_deletion_in_modal();
	}

}