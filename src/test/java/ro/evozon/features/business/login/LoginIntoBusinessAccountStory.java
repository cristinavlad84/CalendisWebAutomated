package ro.evozon.features.business.login;

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
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = {
		"In order to login to business account",
		"As end user ",
		"I want to be able to complete email and password and login into business account" })
@RunWith(SerenityRunner.class)
public class LoginIntoBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword;

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileName();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			businessName = props.getProperty("businessName", businessName);
			businessEmail = props.getProperty("businessEmail", businessEmail);
			businessPassword = props.getProperty("businessPassword",
					businessPassword);

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
	public LoginBusinessAccountSteps loginStep;

	@Issue("#CLD-030")
	@Test
	public void login_into_business_account() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);

		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();

	}

}