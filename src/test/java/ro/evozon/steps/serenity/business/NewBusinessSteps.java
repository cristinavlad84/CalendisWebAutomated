package ro.evozon.steps.serenity.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import ro.evozon.pages.business.BusinessHomePage;
import ro.evozon.pages.business.BusinessWizardPage;
import ro.evozon.pages.business.NewBusinessAccountPage;
import ro.evozon.pages.client.ClientHomePage;
import ro.evozon.pages.client.LoggedInClientHomePage;
import ro.evozon.pages.client.NewClientAccountPage;
import ro.evozon.pages.client.SetPassswordNewClientAccountPage;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import ro.evozon.AbstractSteps;

public class NewBusinessSteps extends AbstractSteps {

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
		assertThat(businessWizardPage.get_text_from_welcome_wizard(),
				is(expectedMessage));
	}

	@StepGroup
	public void fill_in_location_wizard(String businessAddress,
			String busLocationName, String phoneNo) {
		businessWizardPage.fill_in_business_adress(businessAddress);
		businessWizardPage.select_random_business_county();
		businessWizardPage.select_random_city();
		businessWizardPage.fill_in_business_location_name(busLocationName);
		businessWizardPage.fill_in_business_phone_no(phoneNo);
		businessWizardPage.click_on_go_to_schedule();
		businessWizardPage.schedule_popup_should_appear();

	}

	@StepGroup
	public void fill_in_schedule_form() {
		check_schedule_day_of_week_business();
		click_on_save_schedule();

	}

	@Step
	public List<String> check_schedule_day_of_week_business() {
		return businessWizardPage.select_day_of_week_business();
	}

	@Step
	public List<String> check_schedule_day_of_week_staff() {
		return businessWizardPage.select_day_of_week_staff();
	}

	@Step
	public void click_on_save_schedule() {
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
		click_os_set_staff_schedule();
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
		businessWizardPage.fill_in_staff_email(staffPhone);
	}

	@Step
	public void click_os_set_staff_schedule() {
		businessWizardPage.click_on_set_staff_schedule_button();
	}

	@Step
	public void click_on_save_staff_schedule() {
		businessWizardPage.click_on_save_staff_schedule_button();
	}

}