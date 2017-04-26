package ro.evozon.steps.serenity.business;

import ro.evozon.pages.business.BusinessHomePage;
import ro.evozon.pages.business.LoggedInBusinessPage;
import ro.evozon.pages.business.SettingsPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;
import static org.assertj.core.api.Assertions.*;
import ro.evozon.AbstractSteps;

public class AddSpecialistSteps extends AbstractSteps {

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
				.isTrue();
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
	public void confirm_staff_deletion(){
		settingsPage.confirm_staff_deletion_in_modal();
	}
	@Step
	public void select_staff_type_to_add(String staffType) {
		settingsPage.select_staff_type_to_be_added(staffType);
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

}