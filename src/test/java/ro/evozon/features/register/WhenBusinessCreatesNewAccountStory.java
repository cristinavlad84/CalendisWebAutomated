package ro.evozon.features.register;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

import org.jruby.ast.EnsureNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.tools.GMailClient;
import ro.evozon.tools.HTMLLInkExtractor;
import ro.evozon.tools.HTMLLInkExtractor.HtmlLink;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.Tools;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NewBusinessAccountSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.steps.serenity.client.EndUserSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to login to business platform",
		"As business user ",
		"I want to be able to register and activate account via email link" })
@RunWith(SerenityRunner.class)
public class WhenBusinessCreatesNewAccountStory extends BaseTest {

	private final static String businessName;
	private final static String businessEmail;
	private final static String businessPhoneNo;
	private final static String businessPassword;

	static {
		businessName = FieldGenerators.generateRandomString(6, Mode.ALPHA);

		businessEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA)
				.toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(
						Constants.BUSINESS_FAKE_MAIL_DOMAIN);
		businessPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		businessPassword = FieldGenerators.generateRandomString(8,
				Mode.ALPHANUMERIC);

	}

	@After
	public void writeToPropertiesFile() {

		try {
			String fileName = Constants.OUTPUT_PATH_BUSINESS_ACCOUNT
					+ ConfigUtils.getOutputFileNameForBusinessAccount();
			Properties props = new Properties();
			FileWriter writer = new FileWriter(fileName);
			props.setProperty("businessName", businessName);

			props.setProperty("businessEmail", businessEmail);
			props.setProperty("businessPhoneNo", businessPhoneNo);
			props.setProperty("businessPassword", businessPassword);
			props.store(writer, "business user details");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Before
	public void deleteFile() {
		String csv = Constants.OUTPUT_PATH_BUSINESS_ACCOUNT
				+ ConfigUtils.getOutputFileNameForBusinessAccount();
		File file = new File(csv);
		boolean status = file.delete();
		if (status)
			System.out.println("File deleted successfully!!");
		else {
			System.out.println("File does not exists");

		}

	}

	@Steps
	public NewBusinessAccountSteps endUser;
	public LoginBusinessAccountSteps loginStep;

	@Issue("#WIKI-1")
	@Test
	public void creating_new_account_as_client_should_display_the_success_email_message_and_activate_link_should_logg_in_client()
			throws Exception {

		endUser.navigateTo(ConfigUtils.getBaseUrl());
		endUser.click_on_inregistreaza_te();
		endUser.waitForPageToLoad();
		endUser.selectBusinessCategory();
		endUser.fill_in_business_details(businessName, businessEmail,
				businessPhoneNo);

		endUser.click_on_register_button();
		endUser.success_message_should_be_visible();
		endUser.user_should_see_business_email_in_success_message(businessEmail);

		Tools emailExtractor = new Tools();
		String link = "";

		Tools.CrunchifyRetryOnExceptionStrategy retry = new Tools.CrunchifyRetryOnExceptionStrategy();
		while (retry.shouldRetry()) {
			try {
				link = emailExtractor
						.getActivationLinkFromEmailForNewlyCreatedAccount(
								Constants.GMAIL_BUSINESS_BASE_ACCOUNT_SUFFIX,
								Constants.GMAIL_BUSINESS_BASE_PSW,
								Constants.NEW_BUSINESS_ACCOUNT_SUCCESS_MESSAGE_SUBJECT,
								Constants.LINK__BUSINESS_ACTIVATE,
								businessEmail);
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
				"business2");

		endUser.navigateTo(link2);
		endUser.fill_in_password(businessPassword);
		endUser.fill_in_repeat_password(businessPassword);
		endUser.chek_terms_and_condition_box();
		endUser.click_on_ok_button();
		

	}
}
