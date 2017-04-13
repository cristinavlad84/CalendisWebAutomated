package ro.evozon.features.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.Tools;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to login to client account", "As end user ",
		"I want to be able to complete email and password and login into account" })
@RunWith(SerenityRunner.class)
public class LoginIntoClientAccountStory extends BaseTest {

	private String clientFirstName, clientEmail, clientPassword;

	@Before
	public void readFromFile() {
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
	public NewClientAccountSteps loginStep;

	@Issue("#WIKI-1")
	@Test
	public void creating_new_account_as_client_should_display_the_success_email_message_and_activate_link_should_logg_in_client()
			throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.clicks_on_intra_in_cont_link();
		loginStep.fill_in_client_email_address(clientEmail);
		loginStep.fill_in_client_password(clientPassword);
		loginStep.click_on_login_button();

		// user should be logged in

		loginStep.user_dropdown_as_logged_in_should_be_visible();
		loginStep.user_should_see_username_in_dropdown(clientFirstName);

	}

}