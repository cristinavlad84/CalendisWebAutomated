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
		"In order to login to business account as colaborator",
		"As business user ",
		"I want to be able to add new colaborator and then login into colaborator account" })
@RunWith(SerenityRunner.class)
public class LoginAsColaboratorStory extends BaseTest {

	private String colaboratorEmail, colaboratorPassword;

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileNameForColaborator();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);

			colaboratorEmail = props.getProperty("colaboratorEmail",
					colaboratorEmail);
			colaboratorPassword = props.getProperty("colaboratorPassword",
					colaboratorPassword);

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
	public void login_into_colaborator_account() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(colaboratorEmail,
				colaboratorPassword);

		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();

	}

}