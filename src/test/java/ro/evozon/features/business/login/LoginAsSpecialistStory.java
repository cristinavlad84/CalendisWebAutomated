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
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = {
		"In order to login to business account as specialist",
		"As business user ",
		"I want to be able to add new specialist and then login into specialist account" })
@RunWith(SerenityRunner.class)
public class LoginAsSpecialistStory extends BaseTest {

	private String specialistEmail, specialistPassword;

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileNameForStaffAccount();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);

			specialistEmail = props.getProperty("specialistEmail",
					specialistEmail);
			specialistPassword = props.getProperty("specialistPassword",
					specialistPassword);

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
	public void login_into_specialist_account() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(specialistEmail,
				specialistPassword);

		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();

	}

}