package ro.evozon.features.business.settings;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import ro.evozon.tools.Tools;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.StaffType;
import ro.evozon.steps.serenity.business.AddSpecialistSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = {
		"In order to login to business account as receptionist",
		"As business user ",
		"I want to be able to add new receptionist and then login into receptionist account" })
@RunWith(SerenityRunner.class)
public class AddColaboratorFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword,
			colaboratorEmail, colaboratorPassword, colaboratorName,
			colaboratorPhoneNo;

	public AddColaboratorFromBusinessAccountStory() {
		super();

		this.colaboratorEmail = FieldGenerators.generateRandomString(3,
				Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(
						Constants.STAFF_FAKE_DOMAIN);
		this.colaboratorPassword = FieldGenerators.generateRandomString(8,
				Mode.ALPHANUMERIC);
		;
		this.colaboratorName = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
		this.colaboratorPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
	}

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileName();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			businessName = props.getProperty("businessName", businessName);
			businessEmail = props.getProperty("businessEmail", businessEmail);
			businessPassword = props.getProperty("businessPassword",
					businessPassword);

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

	@After
	public void writeToPropertiesFile() {

		try {
			String fileName = Constants.OUTPUT_PATH
					+ ConfigUtils.getOutputFileNameForColaborator();
			Properties props = new Properties();
			FileWriter writer = new FileWriter(fileName);
			props.setProperty("colaboratorName", colaboratorName);
			props.setProperty("colaboratorPassword", colaboratorPassword);
			props.setProperty("colaboratorEmail", colaboratorEmail);
			props.setProperty("colaboratorPhoneNo", colaboratorPhoneNo);

			props.store(writer, "colaborator details");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Steps
	public LoginBusinessAccountSteps loginStep;
	@Steps
	public AddSpecialistSteps addSpecialitsSteps;
	@Steps
	public StaffSteps staffSteps;

	@Issue("#CLD-030")
	@Test
	public void add_colaborator_then_set_psw_and_login_into_colaborator_account()
			throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);

		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		addSpecialitsSteps.click_on_settingst_link();
		addSpecialitsSteps.click_on_add_new_staff_button();
		addSpecialitsSteps.fil_in_staff_name(colaboratorName);
		addSpecialitsSteps.fil_in_staff_email(colaboratorEmail);
		addSpecialitsSteps.fil_in_staff_phone(colaboratorPhoneNo);
		addSpecialitsSteps.select_staff_type_to_add(StaffType.COL.toString());
		addSpecialitsSteps.check_default_location();

		addSpecialitsSteps.click_on_set_staff_schedule();
		addSpecialitsSteps.select_day_of_week_for_staff_schedule();

		addSpecialitsSteps.click_on_save_staff_schedule();

		addSpecialitsSteps
				.search_for_staff_in_personal_section(colaboratorName);
		// Thread.sleep(9000);
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
								Constants.LINK__STAFF_INVITATED,
								colaboratorEmail);
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
		staffSteps.fill_in_staff_password(colaboratorPassword);
		staffSteps.repeat_staff_password(colaboratorPassword);
		staffSteps.click_on_set_staff_password_button();
		// assert that tooltip overlay is displayed
		staffSteps.intro_overlay_should_be_displayed();
		// close intro overlay --> otherwise will pops up at login
		staffSteps.close_intro_overlay();
		// login as staff
	}

}