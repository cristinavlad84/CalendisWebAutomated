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

public class LoginBusinessAccountSteps extends AbstractSteps {

	BusinessHomePage businessHomePage;

	@Step
	public void click_on_enter_into_account_link() {
		businessHomePage.click_on_sign_in_as_business();

	}

	@StepGroup
	public void login_into_business_account(String email,String password){
		click_on_enter_into_account_link();
		fill_in_business_emai_address(email);
		fill_in_business_password(password);
		click_on_login_submit_button();
		
		
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

}