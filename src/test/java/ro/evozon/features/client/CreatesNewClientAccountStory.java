package ro.evozon.features.client;

import java.io.File;
import java.io.FileWriter;


import java.io.IOException;
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

@Narrative(text = { "In order to login to client platform", "As end user ",
		"I want to be able to register and activate account via email link" })
@RunWith(SerenityRunner.class)
public class CreatesNewClientAccountStory extends BaseTest {

	private final String clientLastName, clientFirstName, clientEmail,
			clientPhoneNo, clientPassword;

	public CreatesNewClientAccountStory() {
		this.clientFirstName = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
		this.clientLastName = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
		this.clientEmail = Constants.GMAIL_CLIENT_BASE_ACCOUNT_SUFFIX
				+ "+"
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(
						Constants.EMAIL_SUFFIX);
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
		String csv = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileName();
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

	@Issue("#WIKI-1")
	@Test
	public void creating_new_account_as_client_should_display_the_success_email_message_and_activate_link_should_logg_in_client()
			throws Exception {
	
		endUser.navigateTo(ConfigUtils.getBaseUrl());
		endUser.clicks_on_intra_in_cont_link();
		endUser.click_on_creeaza_un_cont_nou();
		endUser.fill_in_client_details(clientLastName, clientFirstName,
				clientEmail, clientPhoneNo);

		endUser.click_on_create_account_button();
		endUser.should_see_success_message(Constants.NEW_ACCOUNT_SUCCESS_MESSAGE_WEB);
		Tools emailExtractor = new Tools();
		String link = "";
		try {
			link = emailExtractor
					.getActivationLinkFromEmailForNewlyCreatedAccount(
							Constants.GMAIL_CLIENT_BASE_ACCOUNT_SUFFIX,
							Constants.GMAIL_CLIENT_BASE_PSW,
							Constants.NEW_CLIENT_ACCOUNT_SUCCESS_MESSAGE_SUBJECT,
							Constants.LINK__CLIENT_ACTIVATE, clientEmail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		endUser.navigateTo(link);
		endUser.fill_in_password(clientPassword);
		endUser.fill_in_repeat_password(clientPassword);
		endUser.clik_on_save_button();
		endUser.user_dropdown_as_logged_in_should_be_visible();
		endUser.user_should_see_username_in_dropdown(clientFirstName);
	}

}