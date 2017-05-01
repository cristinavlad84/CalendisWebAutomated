package ro.evozon.steps.serenity.business;

import ro.evozon.pages.business.BusinessHomePage;
import ro.evozon.pages.business.BusinessWizardPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;
import static org.assertj.core.api.Assertions.*;

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
		softly.assertThat(businessWizardPage.get_text_from_welcome_wizard())
				.isEqualTo(expectedMessage);
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
	public String select_random_city() {
		return businessWizardPage.select_random_city();
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
	public void fill_in_schedule_form_for_staff() {
		check_schedule_day_of_week_staff();
		click_on_save_staff_schedule();

	}

	@Step
	public void check_schedule_day_of_week_business() {
		businessWizardPage.select_day_of_week_business();
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

	@StepGroup
	public void fill_in_service_form(String serviceName, String price) {
		fill_in_service_name(serviceName);
		select_service_duration();
		select_service_max_persons();
		fill_in_service_price(price);
		click_on_save_service_form();

	}

	@Step
	public void fill_in_service_name(String serviceName) {
		businessWizardPage.fill_in_service_name(serviceName);
	}

	@Step
	public void select_service_duration() {
		businessWizardPage.select_service_duration();
	}

	@Step
	public void select_service_max_persons() {
		businessWizardPage.select_service_max_persons();
	}

	@Step
	public void fill_in_service_price(String price) {
		businessWizardPage.fill_in_service_price(price);
	}

	@Step
	public void click_on_save_service_form() {
		businessWizardPage.save_service_popup_content();
	}

	@StepGroup
	public void fill_is_staff_form(String staffName, String staffEmail,
			String staffPhone) {
		fill_in_staff_name(staffName);
		fill_in_staff_email(staffEmail);
		fill_in_staff_phone(staffPhone);
		click_on_set_staff_schedule();
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
	public void expectedMessageShouldBeDispayedInWizardOverlay(
			String expectedMessage) {
		softly.assertThat(businessWizardPage.getTextFromWizardOverlay())
				.isEqualTo(expectedMessage);
	}

	@Step
	public void dismiss_wizard_modal() {
		businessWizardPage.dismiss_wizard_modal();
	}

}