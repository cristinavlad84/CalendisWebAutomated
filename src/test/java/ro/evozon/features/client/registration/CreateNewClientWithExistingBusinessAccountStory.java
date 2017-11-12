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
import ro.evozon.features.business.registration.CreateNewBusinessAccountStory;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NewBusinessAccountSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = {
		"In order to login to client platform with existing business account",
		"As end user ",
		"I want to be able to register and activate account via email link" })
@RunWith(SerenityRunner.class)
public class CreateNewClientWithExistingBusinessAccountStory extends BaseTest {
	private CreateNewBusinessAccountStory businessAccount = new CreateNewBusinessAccountStory();

	private final String clientLastName, clientFirstName, clientPhoneNo,
			clientPassword;

	public CreateNewClientWithExistingBusinessAccountStory() {
		this.clientFirstName = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
		this.clientLastName = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
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

	@Issue("#CLD-003")
	@Test
	public void creating_new_account_as_client_with_existing_business_account()
			throws Exception {
		// create new business account
		newBusinessAccountStep.deleteAllCookies();
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
				.user_should_see_success_message();

		Tools emailExtractor = new Tools();
		String link = "";

		Tools.RetryOnExceptionStrategy retry = new Tools.RetryOnExceptionStrategy();
		while (retry.shouldRetry()) {
			try {
				link = emailExtractor.getLinkFromEmails(
						Constants.BUSINESS_GMAIL_BASE_ACCOUNT_SUFFIX,
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
		// close the browser and delete all cookies
		newBusinessAccountStep.closeBrowser();
		newBusinessAccountStep.deleteAllCookies();

		newBusinessAccountStep.navigateTo(link2);
		newBusinessAccountStep
				.fill_in_password(businessAccount.businessPassword);
		newBusinessAccountStep
				.fill_in_repeat_password(businessAccount.businessPassword);
		newBusinessAccountStep.chek_terms_and_condition_box();
		newBusinessAccountStep.click_on_ok_button();

		// create new client account with existing business

		newClientAccountStep.closeBrowser();
		newClientAccountStep.deleteAllCookies();
		newClientAccountStep.navigateTo(ConfigUtils.getBaseUrl());

		newClientAccountStep.clicks_on_intra_in_cont_link();

		newClientAccountStep.click_on_creeaza_un_cont_nou();
		newClientAccountStep.fill_in_client_details(clientLastName,
				clientFirstName, businessAccount.businessEmail, clientPhoneNo);

		newClientAccountStep.click_on_create_account_button();

		//
		newClientAccountStep
				.should_see_warning_message_existing_account(Constants.EXISTING_BUSINESS_ACCOUNT_CREATION);
		newClientAccountStep
				.fill_in_email_for_existing_account(businessAccount.businessEmail);
		newClientAccountStep
				.fill_in_password_for_existing_account(businessAccount.businessPassword);
		newClientAccountStep.click_on_create_personal_account();

		newClientAccountStep
				.should_see_activate_account_modal_with_prefilled_email(businessAccount.businessEmail);

		// fill in first name, last name, phone no,
		// click on activate account
		newClientAccountStep.fill_in_last_name_field(clientLastName);
		newClientAccountStep.fill_in_first_name_field(clientFirstName);
		newClientAccountStep.fill_in_phone_number_field(clientPhoneNo);
		newClientAccountStep.click_on_activate_account_button();
		// should see success message
		// user should see activate account
		newClientAccountStep
				.should_see_success_message_account_activated(Constants.ACTIVATED_ACCOUNT_SUCCESS_MESSAGE);
		newClientAccountStep.assertAll();
	}

}