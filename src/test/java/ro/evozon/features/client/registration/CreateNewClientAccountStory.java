package ro.evozon.features.client.registration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import ro.evozon.tools.enums.PhonePrefixGenerators;
import ro.evozon.tools.Tools;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.steps.serenity.client.SetPasswordClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to login to client platform", "As end user ",
		"I want to be able to register and activate account via email link" })
@RunWith(SerenityRunner.class)
public class CreateNewClientAccountStory extends BaseTest {

	private final String clientLastName, clientFirstName, clientEmail,
			clientPhoneNo, clientPassword;

	public CreateNewClientAccountStory() {
		this.clientFirstName = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
		this.clientLastName = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);

		this.clientEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA)
				.toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(
						Constants.CLIENT_FAKE_MAIL_DOMAIN);
		this.clientPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		this.clientPassword = FieldGenerators.generateRandomString(8,
				Mode.ALPHANUMERIC);
	}

	@After
	public void writeToPropertiesFile() {

		try {
			String fileName = Constants.OUTPUT_PATH
					+ ConfigUtils.getOutputFileName();
			Properties props = new Properties();
			FileWriter writer = new FileWriter(fileName);
			props.setProperty("firstName", clientFirstName);
			props.setProperty("lastName", clientLastName);
			props.setProperty("emailAddress", clientEmail);
			props.setProperty("PhoneNo", clientPhoneNo);
			props.setProperty("password", clientPassword);
			props.store(writer, "user details");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Before
	public void deleteFile() {
		String csv = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileName();
		File file = new File(csv);
		boolean status = file.delete();
		if (status)
			System.out.println("File deleted successfully!!");
		else {
			System.out.println("File does not exists");

		}

	}

	@Steps
	public NewClientAccountSteps endUser;
	@Steps
	public SetPasswordClientAccountSteps setPasswordStep;

	@Issue("#CLD-001")
	@Test
	public void creating_new_account_as_client() throws Exception {

		endUser.navigateTo(ConfigUtils.getBaseUrl());
		endUser.clicks_on_intra_in_cont_link();
		endUser.click_on_creeaza_un_cont_nou();
		endUser.fill_in_client_details(clientLastName, clientFirstName,
				clientEmail, clientPhoneNo);

		endUser.click_on_create_account_button();
		endUser.should_see_success_message_account_created(Constants.NEW_ACCOUNT_SUCCESS_MESSAGE_WEB);
		Tools emailExtractor = new Tools();
		Tools.RetryOnExceptionStrategy retry = new Tools.RetryOnExceptionStrategy();
		String link = "";
		while (retry.shouldRetry()) {
			try {
				link = emailExtractor.getLinkFromEmails(
						Constants.CLIENT_GMAIL_BASE_ACCOUNT_SUFFIX,
						Constants.GMAIL_CLIENT_BASE_PSW,
						Constants.NEW_CLIENT_ACCOUNT_SUCCESS_MESSAGE_SUBJECT,
						Constants.LINK__CLIENT_ACTIVATE, clientEmail);
				break;
			} catch (Exception e) {
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
		endUser.navigateTo(link);
		setPasswordStep.fill_in_password(clientPassword);
		setPasswordStep.fill_in_repeat_password(clientPassword);
		setPasswordStep.clik_on_save_button();
		setPasswordStep.user_dropdown_as_logged_in_should_be_visible();
		setPasswordStep.user_should_see_username_in_dropdown(clientFirstName);
		setPasswordStep.assertAll();
	}
}