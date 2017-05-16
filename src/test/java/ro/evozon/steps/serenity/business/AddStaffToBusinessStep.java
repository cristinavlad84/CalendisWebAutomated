package ro.evozon.steps.serenity.business;

import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.SettingsPage;
import ro.evozon.pages.business.StaffPage;

public class AddStaffToBusinessStep extends AbstractSteps {
	StaffPage staffPage;
	SettingsPage settingsPage;

	@Step
	public void fill_in_staff_name(String staffName) {
		staffPage.fill_in_staff_name(staffName);
	}

	@Step
	public void fill_in_staff_email(String staffEmail) {
		staffPage.fill_in_staff_email(staffEmail);
	}

	@Step
	public void fill_in_staff_phone(String staffPhone) {
		staffPage.fill_in_staff_phone(staffPhone);
	}

	@Step
	public void click_on_add_new_staff_button() {
		staffPage.click_on_add_new_staff();
	}

	@Step
	public void is_staff_name_displayed_in_personal_section(String staffName) {
		softly.assertThat(staffPage.is_staff_name_displayed_in_personal_section(staffName)).as("staff Name").isTrue();
	}

	@Step
	public void click_on_modify_staff_link(String staffName) {

		staffPage.click_on_modify_link(staffName);

	}

	@Step
	public void select_service_domain_location_for_specialist(String locationName, String domainName,
			String serviceName) {
		
		staffPage.check_service(locationName, domainName, serviceName);
	}

	@Step
	public void click_on_delete_staff_link(String staffName) {
		staffPage.click_on_delete_staff_link(staffName);
	}

	@Step
	public void confirm_staff_deletion() {
		settingsPage.confirm_item_deletion_in_modal();
	}

	@Step
	public void select_staff_type_to_add(String staffType) {
		staffPage.select_staff_type_to_be_added(staffType);
	}

	@Step
	public void check_default_location_for_staff() {
		staffPage.check_in_default_location_for_staff();
	}

	@Step
	public void click_on_set_staff_schedule() {
		staffPage.click_on_set_staff_schedule();
	}

	@Step
	public void select_day_of_week_for_staff_schedule() {
		staffPage.select_day_of_week_for_staff();
	}

	@Step
	public void click_on_save_staff_schedule() {
		staffPage.click_on_save_staff_schedule();
	}

	@Step
	public void click_on_save_receptionist() {
		staffPage.click_on_save_receptionist();
	}

	@Step
	public void click_on_save_staff_edits() {
		staffPage.click_on_save_staff_edit();
	}

	@Step
	public void is_staff_email_displayed_in_personal_section(String staffName, String staffEmail) {
		staffPage.is_staff_email_displayed_in_personal_section(staffName, staffEmail);
	}

	public void is_staff_phone_displayed_in_personal_section(String staffName, String staffPhone) {
		staffPage.is_staff_phone_displayed_in_personal_section(staffName, staffPhone);
	}

}
