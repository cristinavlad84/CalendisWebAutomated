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
	public void click_on_settingst_link() {
		loggedInBusinessPage.click_on_settings();

	}

	@Step
	public void fil_in_staff_name(String staffName) {
		settingsPage.fill_in_staff_name(staffName);
	}

	@Step
	public void fil_in_staff_email(String staffEmail) {
		settingsPage.fill_in_staff_email(staffEmail);
	}

	@Step
	public void fil_in_staff_phone(String staffPhone) {
		settingsPage.fill_in_staff_phone(staffPhone);
	}

	@Step
	public void click_on_add_new_staff_button() {
		settingsPage.click_on_add_new_staff();
	}

	@Step
	public void search_for_staff_in_personal_section(String staffName) {
		softly.assertThat(
				settingsPage
						.search_for_staff_name_in_personal_section(staffName))
				.isTrue();
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

}