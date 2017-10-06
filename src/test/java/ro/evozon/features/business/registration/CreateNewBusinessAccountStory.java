package ro.evozon.features.business.registration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.UserStoryCode;

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
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NewBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.tests.BaseTest;

@UserStoryCode("US01")
@Narrative(text = { "In order to use business platform", "As business user ",
		"I want to be able to register and activate account via email link then login into account and complete registration wizard" })
@RunWith(SerenityRunner.class)
public class CreateNewBusinessAccountStory extends BaseTest {

	public String businessAddress;
	public String businessMainLocation;
	public String businessMainDomain;
	public String businessFirstService;
	public String businessFirstServicePrice;

	public String businessPhoneNo;
	public String firstAddedSpecialistName;
	public String firstAddedSpecialistPhone, maxPersons;
	public String firstAddedSpecialistEmail;
	public String firstAddedSpecialistPassword;
	public String businessName;
	public String businessEmail;
	public String businessPassword;
	public int businessFirstServiceDuration;

	public CreateNewBusinessAccountStory() {
		this.businessName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.businessEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.BUSINESS_FAKE_MAIL_DOMAIN);
		this.businessPassword = FieldGenerators.generateRandomString(8, Mode.ALPHANUMERIC);
		this.businessPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		this.businessAddress = FieldGenerators.generateRandomString(6, Mode.ALPHA)
				.concat(FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		this.businessMainLocation = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.businessMainDomain = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.businessFirstService = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.businessFirstServicePrice = new DecimalFormat("#.00").format(
				FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Constants.MAX_SERVICE_PRICE));

		this.firstAddedSpecialistName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.firstAddedSpecialistEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.firstAddedSpecialistPhone = PhonePrefixGenerators.generatePhoneNumber();
		this.maxPersons = Integer.toString(FieldGenerators.getRandomIntegerBetween(1, 100));
		this.firstAddedSpecialistPassword = FieldGenerators.generateRandomString(8, Mode.ALPHANUMERIC);
		this.businessFirstServiceDuration = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;

	}

	@After
	public void writeToPropertiesFile() {

		try {
			String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileName();
			Properties props = new Properties();
			FileWriter writer = new FileWriter(fileName);

			props.setProperty("businessName", businessName);

			props.setProperty("businessEmail", businessEmail);
			props.setProperty("businessPhoneNo", businessPhoneNo);
			props.setProperty("businessPassword", businessPassword);
			props.setProperty("businessAddress", businessAddress);
			props.setProperty("businessMainLocation", businessMainLocation);
			props.setProperty("businessMainLocationCounty",
					ConfigUtils.removeAccents(Serenity.sessionVariableCalled("mainLocationCounty").toString()));
			props.setProperty("businessMainLocationCity",
					ConfigUtils.removeAccents(Serenity.sessionVariableCalled("mainLocationCity").toString()));
			props.setProperty("businessMainDomain", businessMainDomain);
			props.setProperty("businessFirstService", businessFirstService);
			props.setProperty("businessFirstServicePrice", businessFirstServicePrice);
			props.setProperty("businessFirstServiceDuration", Integer.toString(businessFirstServiceDuration));
			props.setProperty("firstAddedSpecialistName", firstAddedSpecialistName);
			props.setProperty("firstAddedSpecialistEmail", firstAddedSpecialistEmail);
			props.setProperty("firstAddedSpecialistPhone", firstAddedSpecialistPhone);
			props.setProperty("firstAddedSpecialistPassword", firstAddedSpecialistPassword);
			props.store(writer, "business user details");
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
			System.out.println("File does not exist");

		}

	}

	@Steps
	public NewBusinessAccountSteps endUser;
	@Steps
	public LoginBusinessAccountSteps loginStep;
	@Steps
	NewBusinessAccountSteps newBusinessAccountSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;
	@Steps
	public StaffSteps staffSteps;

	@Issue("#CLD-025; CLD-027; CLD-028; CLD-026")
	@Test
	public void creating_new_account_as_business() throws Exception {

		endUser.navigateTo(ConfigUtils.getBaseUrl());
		endUser.click_on_inregistreaza_te();
		endUser.waitForPageToLoad();
		endUser.selectBusinessCategory();
		endUser.fill_in_business_details(businessName, businessEmail, businessPhoneNo);

		endUser.click_on_register_button();
		endUser.success_message_should_be_visible();
		endUser.user_should_see_business_email_in_success_message(businessEmail);

		Tools emailExtractor = new Tools();
		String link = "";

		Tools.RetryOnExceptionStrategy retry = new Tools.RetryOnExceptionStrategy();
		while (retry.shouldRetry()) {
			try {
				link = emailExtractor.getLinkFromEmails(Constants.BUSINESS_GMAIL_BASE_ACCOUNT_SUFFIX,
						Constants.GMAIL_BUSINESS_BASE_PSW, Constants.NEW_BUSINESS_ACCOUNT_SUCCESS_MESSAGE_SUBJECT,
						Constants.LINK__BUSINESS_ACTIVATE, businessEmail);
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					System.out.println("in catch.....");
					retry.errorOccured();
				} catch (RuntimeException e1) {
					throw new RuntimeException("Exception while searching email:", e);
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}

			}
		}
		String link2 = emailExtractor.editBusinessActivationLink(link, ConfigUtils.getBusinessEnvironment());

		endUser.navigateTo(link2);
		endUser.fill_in_password(businessPassword);
		endUser.fill_in_repeat_password(businessPassword);
		endUser.chek_terms_and_condition_box();
		endUser.click_on_ok_button();
		// close browser
		loginStep.closeBrowser();
		loginStep.deleteAllCookies();
		// login with business account
		loginStep.navigateTo(ConfigUtils.getBaseUrl());

		loginStep.login_into_business_account(businessEmail, businessPassword);
		// domain form
		businessWizardSteps.waitForWizardPageToLoad();

		businessWizardSteps.wizard_tex_should_be_dispayed(Constants.WIZARD_SUCCESS_MESSAGE_BUSINESS);
		businessWizardSteps.fill_in_business_address(businessAddress);
		Serenity.setSessionVariable("mainLocationCounty").to(businessWizardSteps.select_random_county());
		Serenity.setSessionVariable("mainLocationCity").to(businessWizardSteps.select_random_city());
		businessWizardSteps.fill_in_business_location_name(businessMainLocation);
		businessWizardSteps.fill_in_business_phone(businessPhoneNo);
		businessWizardSteps.click_on_set_business_schedule();
		businessWizardSteps.schedule_popup_should_appear();
		// schedule form

		for (int i = 0; i < Constants.NO_OF_WEEK_DAYS; i++) {
			System.out.println("i = " + i);

			businessWizardSteps.fill_in_schedule_form_for_business(Constants.RANGE_HOURS, i);

		}
		businessWizardSteps.click_on_save_schedule_business();
		// domain form
		businessWizardSteps.fill_in_domain_form(businessMainDomain);
		// service form

		businessWizardSteps.fill_in_service_name(businessFirstService);
		businessWizardSteps.fill_in_service_max_persons(maxPersons);
		businessWizardSteps.fill_in_service_duration_per_service(Integer.toString(businessFirstServiceDuration));
		businessWizardSteps.fill_in_service_price(businessFirstServicePrice);
		businessWizardSteps.click_on_save_service_form();

		businessWizardSteps.fill_in_staff_name(firstAddedSpecialistName);
		businessWizardSteps.fill_in_staff_email(firstAddedSpecialistEmail);
		businessWizardSteps.fill_in_staff_phone(firstAddedSpecialistPhone);

		// staff schedule

		businessWizardSteps.click_on_set_staff_schedule();
		//
		for (int i = 0; i < Constants.NO_OF_WEEK_DAYS; i++) {
			System.out.println("i = " + i);

			businessWizardSteps.fill_in_schedule_form_for_staff(Constants.RANGE_HOURS, i);

		}
		businessWizardSteps.click_on_save_staff_schedule();

		// assert overlay is displayed
		businessWizardSteps
				.expectedMessageShouldBeDispayedInWizardOverlay(Constants.SUCESS_MESSAGE_BUSINESS_WIZARD_COMPLETION);
		businessWizardSteps.dismiss_wizard_modal();
		businessWizardSteps.closeBrowser();
		// verify that staff receives email with invitation to join calendis
		Tools emailExtractor2 = new Tools();
		String linktwo = "";

		Tools.RetryOnExceptionStrategy again = new Tools.RetryOnExceptionStrategy();
		while (again.shouldRetry()) {
			try {
				linktwo = emailExtractor2.getLinkFromEmails(Constants.STAFF_GMAIL_BASE_ACCOUNT,
						Constants.STAFF_PASSWORD_GMAIL_BASE_ACCOUNT,
						Constants.STAFF_INVITATION_TO_JOIN_CALENDIS_MESSAGE_SUBJECT, Constants.LINK__STAFF_INVITATED,
						firstAddedSpecialistEmail);
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					System.out.println("in catch.....");
					again.errorOccured();
				} catch (RuntimeException e1) {
					throw new RuntimeException("Exception while searching email:", e);
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}

			}
		}
		String link3 = emailExtractor2.editBusinessActivationLink(linktwo, ConfigUtils.getBusinessEnvironment());
		// activate staff account
		loginStep.navigateTo(link3);
		staffSteps.fill_in_staff_password(firstAddedSpecialistPassword);
		staffSteps.repeat_staff_password(firstAddedSpecialistPassword);
		staffSteps.click_on_set_staff_password_button();
		// assert that tooltip overlay is displayed
		staffSteps.intro_overlay_should_be_displayed();
		// close intro overlay --> otherwise will pops up at login
		staffSteps.close_intro_overlay();
		// login as staff
		loginStep.assertAll();

	}
}
