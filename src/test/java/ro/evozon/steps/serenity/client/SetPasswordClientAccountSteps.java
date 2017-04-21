package ro.evozon.steps.serenity.client;

import ro.evozon.pages.client.ClientHomePage;
import ro.evozon.pages.client.LoggedInClientHomePage;
import ro.evozon.pages.client.NewClientAccountPage;
import ro.evozon.pages.client.SetPassswordNewClientAccountPage;
import ro.evozon.tools.ConfigUtils;
import net.thucydides.core.annotations.Step;

import ro.evozon.AbstractSteps;
import static org.assertj.core.api.Assertions.*;

public class SetPasswordClientAccountSteps extends AbstractSteps {

	NewClientAccountPage newAccountModalPage;
	ClientHomePage clientHomePage;
	SetPassswordNewClientAccountPage setPasswordPage;
	LoggedInClientHomePage loggedInClientPage;

	@Step
	public void fill_in_password(String passw) {
		setPasswordPage.fill_in_password(passw);
	}

	@Step
	public void fill_in_repeat_password(String passw) {
		setPasswordPage.fill_in_repeat_password(passw);
	}

	@Step
	public void clik_on_save_button() {
		setPasswordPage.click_on_save_button();
	}

	@Step
	public void user_dropdown_as_logged_in_should_be_visible() {
		setPasswordPage.dropdown_user_should_be_visible();
	}

	@Step
	public void user_should_see_username_in_dropdown(String userFirstName) {

		softly.assertThat(loggedInClientPage.get_user_name_in_dropdown())
				.isEqualTo(userFirstName);
	}

	@Step
	public void click_on_forgot_password_link() {
		newAccountModalPage.click_on_forgot_password_link();
	}

	@Step
	public void user_should_see_select_account_popup() {
		newAccountModalPage.should_see_select_account_option_popup();
	}

	@Step
	public void user_should_see_client_area_in_select_account_popup() {
		newAccountModalPage.should_see_client_account_option_listed_in_popup();
	}

	@Step
	public void user_should_see_business_area_in_select_account_popup() {
		newAccountModalPage
				.should_see_business_account_option_listed_in_popup();
	}

	@Step
	public void user_should_see_message_for_select_account_in_popup(
			String expectedMEssage) {
		softly.assertThat(
				ConfigUtils.removeAccents(newAccountModalPage
						.getTextFromSelectAccountPopup()))
				.as("check text from select account popup ")
				.contains(expectedMEssage);

	}

	@Step
	public void user_should_see_client_name_in_client_account_area_select_account_popup(
			String clientExpectedMessage) {
		softly.assertThat(
				newAccountModalPage.getTextForClientAccountSelectAccountPopup())
				.as("check text from client area")
				.isEqualToIgnoringCase(clientExpectedMessage);

	}

	@Step
	public void user_should_see_business_name_in_busines_account_area_select_account_popup(
			String businessExpectedMessage) {
		softly.assertThat(
				newAccountModalPage
						.getTextForBusinessAccountSelectAccountPopup())
				.as("check text from business area")
				.containsIgnoringCase(
						businessExpectedMessage.replaceAll("\\s", ""));

	}

	@Step
	public void click_on_login_into_client_account_button() {
		newAccountModalPage.click_on_login_into_client_account();
	}

}