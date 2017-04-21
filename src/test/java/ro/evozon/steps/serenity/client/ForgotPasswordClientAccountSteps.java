package ro.evozon.steps.serenity.client;

import ro.evozon.pages.client.ClientHomePage;
import ro.evozon.pages.client.LoggedInClientHomePage;
import ro.evozon.pages.client.NewClientAccountPage;
import ro.evozon.pages.client.SetPassswordNewClientAccountPage;
import ro.evozon.pages.client.ForgotPasswordPage;
import ro.evozon.tools.ConfigUtils;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;

import ro.evozon.AbstractSteps;
import static org.assertj.core.api.Assertions.*;

public class ForgotPasswordClientAccountSteps extends AbstractSteps {

	ForgotPasswordPage forgotPasswordPage;

	@Step
	public void fill_in_email_forgot_password_form(String email) {
		forgotPasswordPage.fill_in_email_in_lost_password_form(email);
	}

	@Step
	public void click_on_send_button_forgot_password() {
		forgotPasswordPage.click_on_sent_button_in_forgot_password_form();
	}

	@Step
	public void should_see_success_message_sent_email_forgot_password(
			String expectedMessage) {
		assertThat(
				ConfigUtils.removeAccents(
						forgotPasswordPage
								.get_success_message_for_reset_password())
						.trim()).isEqualTo(expectedMessage);
	}

	@Step
	public void fill_in_new_password(String password) {
		forgotPasswordPage.fill_in_new_password(password);
	}

	@Step
	public void repeat_password(String pasw) {
		forgotPasswordPage.repeat_password(pasw);
	}

	@Step
	public void click_on_reset_button() {
		forgotPasswordPage.click_on_reset_password_button();
	}

	@Step
	public void should_see_success_message_after_password_reset(
			String expectedMessage) {
		assertThat(forgotPasswordPage.get_text_from_reset_password_form())
				.isEqualTo(expectedMessage);
	}
}