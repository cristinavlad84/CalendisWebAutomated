package ro.evozon.features.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Step;
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
import ro.evozon.features.business.CreateNewBusinessAccountStory;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NewBusinessAccountSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = {
		"In order to login to client platform with existing business account",
		"As end user ",
		"I want to be able to register and activate account via email link" })
@RunWith(SerenityRunner.class)
public class CreatesNewClientWithExistingBusinessAccountStory extends BaseTest {
	private CreateNewBusinessAccountStory businessAccount = new CreateNewBusinessAccountStory();

	private final String clientLastName, clientFirstName, clientEmail,
			clientPhoneNo, clientPassword;

	public CreatesNewClientWithExistingBusinessAccountStory() {
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
					+ ConfigUtils.getOutputFileNameForExistingBusinessAccount();
			Properties props = new Properties();
			FileWriter writer = new FileWriter(fileName);

			props.setProperty("businessName", businessAccount.businessName);

			props.setProperty("businessEmail", businessAccount.businessEmail);
			props.setProperty("businessPhoneNo",
					businessAccount.businessPhoneNo);
			props.setProperty("businessPassword",
					businessAccount.businessPassword);

			props.setProperty("clientFirstName", clientFirstName);
			props.setProperty("clientLastName", clientLastName);
			props.setProperty("clientemailAddress", clientEmail);
			props.setProperty("clientPhoneNo", clientPhoneNo);
			props.setProperty("clientPassword", clientPassword);
			props.store(writer, "existing business user details");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Before
	public void deleteFile() {
		String csv = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileNameForExistingBusinessAccount();
		File file = new File(csv);
		boolean status = file.delete();
		if (status)
			System.out.println("File deleted successfully!!");
		else {
			System.out.println("File does not exists");

		}

	}

	@Steps
	public NewClientAccountSteps newClientAccountStep;
	@Steps
	public NewBusinessAccountSteps newBusinessAccountStep;
	@Steps
	public LoginBusinessAccountSteps loginStep;

	@Issue("#WIKI-1")
	@Test
	public void creating_new_account_as_client_with_existing_business_account()
			throws Exception {
		// create new business account

		newBusinessAccountStep.navigate(ConfigUtils.getBusinessPlatformUrl()
				.toString());
		newBusinessAccountStep.click_on_inregistreaza_te();
		newBusinessAccountStep.waitForPageToLoad();
		newBusinessAccountStep.selectBusinessCategory();
		newBusinessAccountStep.fill_in_business_details(
				businessAccount.businessName, businessAccount.businessEmail,
				businessAccount.businessPhoneNo);

		newBusinessAccountStep.click_on_register_button();
		newBusinessAccountStep.success_message_should_be_visible();
		newBusinessAccountStep
				.user_should_see_business_email_in_success_message(businessAccount.businessEmail);

		Tools emailExtractor = new Tools();
		String link = "";

		Tools.RetryOnExceptionStrategy retry = new Tools.RetryOnExceptionStrategy();
		while (retry.shouldRetry()) {
			try {
				link = emailExtractor
						.getActivationLinkFromEmailForNewlyCreatedAccount(
								Constants.GMAIL_BUSINESS_BASE_ACCOUNT_SUFFIX,
								Constants.GMAIL_BUSINESS_BASE_PSW,
								Constants.NEW_BUSINESS_ACCOUNT_SUCCESS_MESSAGE_SUBJECT,
								Constants.LINK__BUSINESS_ACTIVATE,
								businessAccount.businessEmail);
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
		String link2 = emailExtractor.editBusinessActivationLink(link,
				ConfigUtils.getBusinessEnvironment());

		newBusinessAccountStep.navigateTo(link2);
		newBusinessAccountStep
				.fill_in_password(businessAccount.businessPassword);
		newBusinessAccountStep
				.fill_in_repeat_password(businessAccount.businessPassword);
		newBusinessAccountStep.chek_terms_and_condition_box();
		newBusinessAccountStep.click_on_ok_button();

		// create new client account with existing business
		newClientAccountStep.getDriver().close();
		System.out.println("client url " + ConfigUtils.getBaseUrl());
		newClientAccountStep.navigateTo(ConfigUtils.getBaseUrl());

		newClientAccountStep.clicks_on_intra_in_cont_link();

		newClientAccountStep.click_on_creeaza_un_cont_nou();
		newClientAccountStep.fill_in_client_details(clientLastName,
				clientFirstName, clientEmail, clientPhoneNo);

		newClientAccountStep.click_on_create_account_button();

		//
		// newClientAccountStep
		// .should_see_success_message(Constants.NEW_ACCOUNT_SUCCESS_MESSAGE_WEB);
		// Tools emailExtractor2 = new Tools();
		// String link3 = "";
		// try {
		// link = emailExtractor
		// .getActivationLinkFromEmailForNewlyCreatedAccount(
		// Constants.GMAIL_CLIENT_BASE_ACCOUNT_SUFFIX,
		// Constants.GMAIL_CLIENT_BASE_PSW,
		// Constants.NEW_CLIENT_ACCOUNT_SUCCESS_MESSAGE_SUBJECT,
		// Constants.LINK__CLIENT_ACTIVATE, clientEmail);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// newClientAccountStep.navigateTo(link3);
		// newClientAccountStep.fill_in_password(clientPassword);
		// newClientAccountStep.fill_in_repeat_password(clientPassword);
		// newClientAccountStep.clik_on_save_button();
		// newClientAccountStep.user_dropdown_as_logged_in_should_be_visible();
		// newClientAccountStep
		// .user_should_see_username_in_dropdown(clientFirstName);

	}

}