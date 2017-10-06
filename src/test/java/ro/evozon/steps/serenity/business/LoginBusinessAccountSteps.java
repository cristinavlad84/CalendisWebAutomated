package ro.evozon.steps.serenity.business;

import ro.evozon.pages.business.BusinessHomePage;

import ro.evozon.pages.business.LoggedInBusinessPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;
import ro.evozon.AbstractSteps;

public class LoginBusinessAccountSteps extends AbstractSteps {

	BusinessHomePage businessHomePage;
	LoggedInBusinessPage loggedInBusinessPage;

	@Step
	public void click_on_log_in_link() {
		businessHomePage.click_on_login_in_link();

	}

	@StepGroup
	public void login_into_business_account(String email, String password) {
		click_on_log_in_link();
		fill_in_business_emai_address(email);
		fill_in_business_password(password);
		click_on_login_submit_button();

	}
	@Step
	public void dismiss_any_popup_if_appears(){
		businessHomePage.dismiss_popup();
	}
	@Step
	public void fill_in_business_emai_address(String email) {

		businessHomePage.fill_in_business_email(email);

	}

	@Step
	public void fill_in_business_password(String password) {

		businessHomePage.fill_in_business_password(password);

	}

	@Step
	public void click_on_login_submit_button() {
		businessHomePage.click_on_login_submit_button();
	}

	@Step
	public void logout_link_should_be_displayed() {
		loggedInBusinessPage.logout_link_should_be_displayed();
	}

	@Step
	public void click_on_settings() {
		loggedInBusinessPage.click_on_settings();
	}

}