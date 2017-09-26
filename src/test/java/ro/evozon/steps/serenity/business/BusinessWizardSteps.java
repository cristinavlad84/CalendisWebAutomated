package ro.evozon.steps.serenity.business;

import ro.evozon.pages.business.BusinessHomePage;
import ro.evozon.pages.business.BusinessWizardPage;
import ro.evozon.tools.models.DaysOfWeek;
import ro.evozon.tools.utils.Time24HoursValidator;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;
import static org.assertj.core.api.Assertions.*;

import org.apache.xalan.xsltc.compiler.Constants;

import ro.evozon.AbstractSteps;

public class BusinessWizardSteps extends AbstractSteps {

	BusinessHomePage businessHomePage;

	BusinessWizardPage businessWizardPage;

	@Step
	public void click_on_inregistreaza_te() {
		businessHomePage.waitUntilButtonAppears();
		businessHomePage.click_on_inregistreaza_te();
	}

	@Step
	public void wizard_tex_should_be_dispayed(String expectedMessage) {
		// assertThat(page.getPublicationDate(), is(selectedPublicationName));
		softly.assertThat(businessWizardPage.get_text_from_welcome_wizard()).isEqualTo(expectedMessage);
	}

	@Step
	public void fill_in_business_address(String businessAddress) {
		businessWizardPage.fill_in_business_adress(businessAddress);
	}

	@Step
	public String select_random_county() {
		return businessWizardPage.select_random_business_county();
	}

	@Step
	public void select_specific_county(String county) {
		businessWizardPage.select_specific_business_county(county);
	}

	@Step
	public String select_random_city() {
		return businessWizardPage.select_random_city();
	}

	@Step
	public void select_specific_city(String city) {
		businessWizardPage.select_specific_city(city);
	}

	@Step
	public void fill_in_business_location_name(String busLocationName) {
		businessWizardPage.fill_in_business_location_name(busLocationName);
	}

	@Step
	public void fill_in_business_phone(String businessPhone) {
		businessWizardPage.fill_in_business_phone_no(businessPhone);
	}

	@Step
	public void click_on_set_business_schedule() {
		businessWizardPage.click_on_go_to_schedule();
	}

	@Step
	public void schedule_popup_should_appear() {
		businessWizardPage.schedule_popup_should_appear();
	}

	@StepGroup
	public void fill_in_schedule_form_for_business() {
		check_schedule_day_of_week_business();
		click_on_save_schedule_business();

	}

	@StepGroup
	public void fill_in_schedule_form_for_business( String rangeHours, int position) {
		check_schedule_day_of_week_business( rangeHours, position);

	}

	@StepGroup
	public void fill_in_schedule_form_for_staff() {
		check_schedule_day_of_week_staff();
		click_on_save_staff_schedule();

	}

	@Step
	public void fill_in_schedule_form_for_staff(String rangeHours, int position) {
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
																			// format

		businessWizardPage.select_day_of_week_staff(position);
		businessWizardPage.select_startHour(startHour, position);
		businessWizardPage.select_endHour(endHour, position);
	}

	@Step
	public void check_schedule_day_of_week_business() {
		businessWizardPage.select_day_of_week_business();
	}

	@Step
	public void check_schedule_day_of_week_business( String rangeHours, int position) {
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
																			// format

		businessWizardPage.select_day_of_week_business(position);
		businessWizardPage.select_startHour(startHour, position);
		businessWizardPage.select_endHour(endHour, position);

	}

	@Step
	public void check_schedule_day_of_week_staff() {
		businessWizardPage.select_day_of_week_staff();
	}

	@Step
	public void click_on_save_schedule_business() {
		businessWizardPage.click_on_save_location();
	}

	@Step
	public void waitForWizardPageToLoad() {
		businessWizardPage.waitUntilElementsAppear();
	}

	@Step
	public void fill_in_new_domain(String domain) {
		businessWizardPage.fill_in_domain(domain);
	}

	@StepGroup
	public void fill_in_domain_form(String domain) {
		fill_in_new_domain(domain);
		click_on_add_button();
		click_on_submit_domain_form();
	}

	@Step
	public void click_on_add_button() {
		businessWizardPage.click_on_add_domain();
	}

	@Step
	public void click_on_submit_domain_form() {
		businessWizardPage.submit_domain_form();
	}

	@Step
	public void fill_in_service_name(String serviceName) {
		businessWizardPage.fill_in_service_name(serviceName);
	}

	@Step
	public String select_service_duration() {
		return businessWizardPage.select_service_duration();
	}

	@Step
	public void select_service_max_persons() {
		businessWizardPage.select_service_max_persons();
	}

	@Step
	public void fill_in_service_max_persons(String personsNo) {
		businessWizardPage.fill_in_max_persons_per_service(personsNo);
	}

	@Step
	public void fill_in_service_price(String price) {
		businessWizardPage.fill_in_service_price(price);
	}

	@Step
	public void click_on_save_service_form() {
		businessWizardPage.save_service_popup_content();
	}

	@Step
	public void fill_in_service_duration_per_service(String duration) {
		businessWizardPage.fill_in_duration_per_service(duration);
	}

	@Step
	public void fill_in_staff_name(String staffName) {
		businessWizardPage.fill_in_staff_name(staffName);
	}

	@Step
	public void fill_in_staff_email(String staffEmail) {
		businessWizardPage.fill_in_staff_email(staffEmail);
	}

	@Step
	public void fill_in_staff_phone(String staffPhone) {
		businessWizardPage.fill_in_staff_phone(staffPhone);
	}

	@Step
	public void click_on_set_staff_schedule() {
		businessWizardPage.click_on_set_staff_schedule_button();
	}

	@Step
	public void click_on_save_staff_schedule() {
		businessWizardPage.click_on_save_staff_schedule_button();
	}

	@Step
	public void expectedMessageShouldBeDispayedInWizardOverlay(String expectedMessage) {
		softly.assertThat(businessWizardPage.getTextFromWizardOverlay()).isEqualTo(expectedMessage);
	}

	@Step
	public void dismiss_wizard_modal() {
		businessWizardPage.dismiss_wizard_modal();
	}

}