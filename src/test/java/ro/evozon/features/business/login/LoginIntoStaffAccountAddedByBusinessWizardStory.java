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
		"In order to login to account as staff",
		"As end user ",
		"I want to be able after business registration wizard completion with adding specialist, to  login into staff account " })
@RunWith(SerenityRunner.class)
public class LoginIntoStaffAccountAddedByBusinessWizardStory extends BaseTest {

	private String staffEmail, staffPassword;

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileNameForNewBusiness();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);

			staffEmail = props.getProperty("staffEmail", staffEmail);
			staffPassword = props.getProperty("staffPassword", staffPassword);

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
	public void login_into_staff_account() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(staffEmail, staffPassword);

		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();

	}

}