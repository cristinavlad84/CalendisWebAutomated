package ro.evozon.features.client.login;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.Tools;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.client.ForgotPasswordClientAccountSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = {
		"In order to login into client account with password recovery flow",
		"As end user ",
		"I want to be able to recover password via email and login into account" })
@RunWith(SerenityRunner.class)
public class ForgotPasswordForClientAccountStory extends BaseTest {

	private String clientFirstName, clientEmail, clientPassword,
			clientNewPassword;

	@Before
	public void readFromFile() {
		clientNewPassword = FieldGenerators.generateRandomString(8,
				Mode.ALPHANUMERIC);
		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileName();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			clientFirstName = props.getProperty("firstName", clientFirstName);
			clientEmail = props.getProperty("emailAddress", clientEmail);
			clientPassword = props.getProperty("password", clientPassword);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Steps
	ForgotPasswordClientAccountSteps forgotPasswordStep;
	@Steps
	public NewClientAccountSteps loginStep;

	@Issue("#CLD-008")
	@Test
	public void forgot_password_for_client_account() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.clicks_on_intra_in_cont_link();
		loginStep.click_on_forgot_password_link();
		forgotPasswordStep.fill_in_email_forgot_password_form(clientEmail);
		forgotPasswordStep.click_on_send_button_forgot_password();
		forgotPasswordStep
				.should_see_success_message_sent_email_forgot_password(Constants.SUCCESS_MESSAGE_SENT_EMAIL_FORGOT_PASSWORD);
		// user should be logged in

		
		Tools emailExtractor = new Tools();
		Tools.RetryOnExceptionStrategy retry = new Tools.RetryOnExceptionStrategy();
		String link = "";
		while (retry.shouldRetry()) {
			try {
				link = emailExtractor.getLinkFromEmails(
						Constants.GMAIL_CLIENT_BASE_ACCOUNT_SUFFIX,
						Constants.GMAIL_CLIENT_BASE_PSW,
						Constants.RESET_PASSWORD_EMAIL_SUBJECT,
						Constants.LINK_FORGOT_PASSWORD, clientEmail);
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					System.out.println("in catch.....");
					retry.errorOccured();
				} catch (RuntimeException e1) {
					throw new RuntimeException(
							"Exception while searching email:", e);
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}

			}
		}
		System.out.println("link to navigate to " + link);
//		loginStep.closeBrowser();
//		loginStep.deleteAllCookies();
		loginStep.navigateTo(link);
		forgotPasswordStep.fill_in_new_password(clientNewPassword);
		forgotPasswordStep.repeat_password(clientNewPassword);
		forgotPasswordStep.click_on_reset_button();
		forgotPasswordStep
				.should_see_success_message_after_password_reset(Constants.SUCCESS_MESSAGE_AFTER_RESET_PASSWORD);
		// login with new password
		loginStep.closeBrowser();
		loginStep.deleteAllCookies();
		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.clicks_on_intra_in_cont_link();
		loginStep.fill_in_client_email_address(clientEmail);
		loginStep.fill_in_client_password(clientNewPassword);
		loginStep.click_on_login_button();

		// user should be logged in

		loginStep.user_dropdown_as_logged_in_should_be_visible();
		loginStep.user_should_see_username_in_dropdown(clientFirstName);

	}

}