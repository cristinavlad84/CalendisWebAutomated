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
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.steps.serenity.client.SetPasswordClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = {
		"In order to login to client account having also an existing business account",
		"As end user ",
		"I want to be able to complete email and password and login into account" })
@RunWith(SerenityRunner.class)
public class LoginIntoClientWithExistingBusinessAccountStory extends BaseTest {

	private String clientFirstName, businessEmail, businessPassword,
			businessName;

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileNameForExistingBusinessAccount();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			clientFirstName = props.getProperty("clientFirstName",
					clientFirstName);
			businessEmail = props.getProperty("businessEmail", businessEmail);
			businessPassword = props.getProperty("businessPassword",
					businessPassword);
			businessName = props.getProperty("businessName", businessName);

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
	public NewClientAccountSteps loginStep;
	@Steps
	public SetPasswordClientAccountSteps setPasswordStep;

	@Issue("#CLD-007")
	@Test
	public void login_as_client_with_existing_business_account()
			throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.clicks_on_intra_in_cont_link();
		loginStep.fill_in_client_email_address(businessEmail);
		loginStep.fill_in_client_password(businessPassword);
		loginStep.click_on_login_button();
		setPasswordStep.user_should_see_select_account_popup();
		setPasswordStep.user_should_see_client_area_in_select_account_popup();
		setPasswordStep.user_should_see_business_area_in_select_account_popup();
		loginStep
				.user_should_see_message_for_select_account_in_popup(Constants.SELECT_REGISTERED_ACCOUNT_CLIENT);
		loginStep
				.user_should_see_client_name_in_client_account_area_select_account_popup(clientFirstName);
		loginStep
				.user_should_see_business_name_in_busines_account_area_select_account_popup(Constants.BUSINESS_ACCOUNT_MESSAGE_SELECT_REGISTERED_ACCOUNT_CLIENT
						+ businessName);
		loginStep.click_on_login_into_client_account_button();

		// user should be logged in

		setPasswordStep.user_dropdown_as_logged_in_should_be_visible();
		setPasswordStep.user_should_see_username_in_dropdown(clientFirstName);
		setPasswordStep.assertAll();

	}
}