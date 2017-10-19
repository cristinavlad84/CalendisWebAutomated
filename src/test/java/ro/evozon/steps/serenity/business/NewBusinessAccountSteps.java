package ro.evozon.steps.serenity.business;

import ro.evozon.pages.business.BusinessHomePage;

import ro.evozon.pages.business.NewBusinessAccountPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;

import ro.evozon.AbstractSteps;
import ro.evozon.tools.Constants;

public class NewBusinessAccountSteps extends AbstractSteps {

	BusinessHomePage businessHomePage;

	NewBusinessAccountPage newBusinessAccountPage;

	@Step
	public void click_on_inregistreaza_te() {
		businessHomePage.waitUntilButtonAppears();
		businessHomePage.click_on_inregistreaza_te();
	}

	@Step
	public void waitForPageToLoad() {
		newBusinessAccountPage.waitUntilDropdownLoads();
	}

	@Step
	public void selectBusinessCategory() {
		newBusinessAccountPage.select_random_business_category();
	}
	@Step
	public void selectBusinessCategory(String category) {
		newBusinessAccountPage.select_business_category(category);
		}
	@StepGroup
	public void fill_in_business_details(String businessName,
			String businessEmail, String businessPhone) {
		newBusinessAccountPage.fill_in_business_name(businessName);
		newBusinessAccountPage.fill_in_business_email(businessEmail);
		newBusinessAccountPage.fill_in_business_phone(businessPhone);

	}

	@Step
	public void fill_in_password(String password) {

		newBusinessAccountPage.fill_in_password(password);

	}

	@Step
	public void fill_in_repeat_password(String paswd) {
		newBusinessAccountPage.fill_in_repeat_password(paswd);
	}

	@Step
	public void chek_terms_and_condition_box() {
		newBusinessAccountPage.checkTermsAndConditionBox();
	}

	@Step
	public void click_on_ok_button() {
		newBusinessAccountPage.click_on_ok_button();
	}

	@Step
	public void click_on_register_button() {
		newBusinessAccountPage.click_on_register();

	}

	@Step
	public void success_message_should_be_visible() {
		newBusinessAccountPage.success_message_should_be_visible();
	}

	@Step
	public void user_should_see_success_message() {
		
		softly.assertThat(newBusinessAccountPage.get_standard_text_from_success_message().contains(Constants.SUCEESS_MESSAGE_NEW_BUSINESS_REGISTER));
	}
}