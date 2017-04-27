package ro.evozon.features.business.registration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NewBusinessAccountSteps;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to create new business", "As business user ",
		"I want to be able to create new business through wizard" })
@RunWith(SerenityRunner.class)
public class BusinessRegistrationWizardStory extends BaseTest {

	private static String businessEmail;
	private static String businessPhoneNo;
	private static String businessPassword;
	private final static String businessAddress;
	private final static String businessMainLocation;
	private final static String businessMainDomain;
	private final static String businessFirstService;
	private final static String businessServicePrice;

	private final static String staffName;
	private final static String staffPhone;
	private final static String staffEmail;
	private final static String staffPassword;

	static {

		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileName();
		File file = new File(fileName);
		if (file != null) {
			Properties prop = new Properties();

			try {
				prop.load(new FileInputStream(fileName));
				businessEmail = prop.getProperty("businessEmail");
				businessPassword = prop.getProperty("businessPassword");
				businessPhoneNo = prop.getProperty("businessPhoneNo");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		businessAddress = FieldGenerators.generateRandomString(6, Mode.ALPHA)
				.concat(FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		businessMainLocation = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
		businessMainDomain = FieldGenerators
				.generateRandomString(6, Mode.ALPHA);
		businessFirstService = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
		businessServicePrice = new DecimalFormat("#.00").format(FieldGenerators
				.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
						Constants.MAX_SERVICE_PRICE));

		staffName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		staffEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA)
				.toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(
						Constants.STAFF_FAKE_DOMAIN);
		staffPhone = PhonePrefixGenerators.generatePhoneNumber();
		staffPassword = FieldGenerators.generateRandomString(8,
				Mode.ALPHANUMERIC);
	}

	@After
	public void writeToPropertiesFile() {

		try {
			String fileName = Constants.OUTPUT_PATH
					+ ConfigUtils.getOutputFileNameForNewBusiness();
			Properties props = new Properties();
			FileWriter writer = new FileWriter(fileName);
			props.setProperty("businessAddress", businessAddress);
			props.setProperty("businessMainLocation", businessMainLocation);
			props.setProperty("businessMainDomain", businessMainDomain);
			props.setProperty("businessFirstService", businessFirstService);
			props.setProperty("businessServicePrice", businessServicePrice);
			props.setProperty("staffName", staffName);
			props.setProperty("staffEmail", staffEmail);
			props.setProperty("staffPhone", staffPhone);
			props.setProperty("staffPassword", staffPassword);
			props.store(writer, "new business details");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Before
	public void deleteFile() {
		// get business credentials from properties file

		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileNameForNewBusiness();
		File file = new File(fileName);
		boolean status = file.delete();
		if (status)
			System.out.println("File deleted successfully!!");
		else {
			System.out.println("File does not exists");

		}

	}

	@Steps
	public LoginBusinessAccountSteps loginStep;

	@Steps
	NewBusinessAccountSteps newBusinessAccountSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;
	@Steps
	public StaffSteps staffSteps;

	@Issue("#CLD-028; CLD-026")
	@Test
	public void new_business_registration_wizard() throws Exception {

		// login with business account
		loginStep.navigateTo(ConfigUtils.getBaseUrl());

		loginStep.login_into_business_account(businessEmail, businessPassword);
		// domain form
		businessWizardSteps.waitForWizardPageToLoad();

		businessWizardSteps
				.wizard_tex_should_be_dispayed(Constants.WIZARD_SUCCESS_MESSAGE_BUSINESS);
		businessWizardSteps.fill_in_location_wizard(businessAddress,
				businessMainLocation, businessPhoneNo);
		// schedule form

		businessWizardSteps.fill_in_schedule_form_for_business();

		// domain form
		businessWizardSteps.fill_in_domain_form(businessMainDomain);
		// service form
		businessWizardSteps.fill_in_service_form(businessFirstService,
				businessServicePrice);
		// staff form
		businessWizardSteps.fill_is_staff_form(staffName, staffEmail,
				staffPhone);
		// staff schedule

		businessWizardSteps.click_on_set_staff_schedule();

		businessWizardSteps.fill_in_schedule_form_for_staff();

		// assert overlay is displayed
		businessWizardSteps
				.expectedMessageShouldBeDispayedInWizardOverlay(Constants.SUCESS_MESSAGE_BUSINESS_WIZARD_COMPLETION);
		businessWizardSteps.dismiss_wizard_modal();
		// verify that staff receives email with invitation to join calendis
		Tools emailExtractor = new Tools();
		String link = "";

		Tools.RetryOnExceptionStrategy retry = new Tools.RetryOnExceptionStrategy();
		while (retry.shouldRetry()) {
			try {
				link = emailExtractor
						.getLinkFromEmails(
								Constants.STAFF_GMAIL_BASE_ACCOUNT,
								Constants.STAFF_PASSWORD_GMAIL_BASE_ACCOUNT,
								Constants.STAFF_INVITATION_TO_JOIN_CALENDIS_MESSAGE_SUBJECT,
								Constants.LINK__STAFF_INVITATED, staffEmail);
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
		// activate staff account
		loginStep.navigateTo(link2);
		staffSteps.fill_in_staff_password(staffPassword);
		staffSteps.repeat_staff_password(staffPassword);
		staffSteps.click_on_set_staff_password_button();
		// assert that tooltip overlay is displayed
		staffSteps.intro_overlay_should_be_displayed();
		// close intro overlay --> otherwise will pops up at login
		staffSteps.close_intro_overlay();
		// login as staff
		loginStep.assertAll();

	}

}
