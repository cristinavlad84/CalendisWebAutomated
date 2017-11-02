package ro.evozon.features.business.datadriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
import ro.evozon.tools.utils.DaysOfWeekConverter;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NewBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.tests.BaseTest;

import static ro.evozon.features.business.datadriven.ParseXlsxUtils.parseExcelFile;
import static ro.evozon.features.business.datadriven.ParseXlsxUtils.writeToPropertiesFile;

@UserStoryCode("US01")
@Narrative(text = { "In order to use business platform", "As business user ",
		"I want to be able to register and activate account via email link then login into account and complete registration wizard" })
@RunWith(SerenityRunner.class)
public class CreateNewBusinessAccountWithRealTestDataStory extends BaseTest {

	public String businessAddress;
	public String businessMainLocation;
	public String businessMainDomain;
	public String businessFirstService;
	public String businessFirstServicePrice;

	public String businessPhoneNo;
	public String firstAddedSpecialistName;
	public String firstAddedSpecialistPhone, firstServiceMaxPersons;
	public String firstAddedSpecialistEmail;
	public String firstAddedSpecialistPassword;
	public String businessName;
	public String businessEmail;
	public String businessPassword;
	public String businessCategory;
	public String businessFirstServiceDuration;
	public String businessMainLocationCounty;
	public String businessMainLocationCity;
	public String locationScheduleMon, locationScheduleTue, locationScheduleWed, locationScheduleThu,
			locationScheduleFri, locationScheduleSat, locationScheduleSun;
	public String staffScheduleMon, staffScheduleTue, staffScheduleWed, staffScheduleThu, staffScheduleFri,
			staffScheduleSat, staffScheduleSun;

	public CreateNewBusinessAccountWithRealTestDataStory() {

	}

	@Before
	public void readFromFile() {

		parseExcelFile(Constants.OUTPUT_PATH+ConfigUtils.getOutputFileNameForXlsxFile(), Constants.OUTPUT_PATH_DATA_DRIVEN);
		writeToPropertiesFile(Constants.OUTPUT_PATH,ConfigUtils.getOutputFileNameForNewBusinessFromXlsx());
		String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForNewBusinessFromXlsx();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			businessName = props.getProperty("businessName", businessName);
			businessCategory = props.getProperty("businessCategory", businessCategory);
			businessPhoneNo = props.getProperty("businessPhoneNo", businessPhoneNo);
			businessEmail = props.getProperty("businessEmail", businessEmail);
			businessPassword = props.getProperty("businessPassword", businessPassword);
			businessAddress = props.getProperty("businessAddress", businessAddress);
			businessMainLocation = props.getProperty("businessMainLocation", businessMainLocation);
			businessMainLocationCounty = props.getProperty("businessMainLocationCounty", businessMainLocationCounty);
			businessMainLocationCity = props.getProperty("businessMainLocationCity", businessMainLocationCity);
			businessMainDomain = props.getProperty("businessMainDomain", businessMainDomain);
			businessFirstService = props.getProperty("businessFirstService", businessFirstService);
			businessFirstServicePrice = props.getProperty("businessFirstServicePrice", businessFirstServicePrice);
			businessFirstServiceDuration = props.getProperty("businessFirstServiceDuration",
					businessFirstServiceDuration);
			firstServiceMaxPersons = props.getProperty("firstServiceMaxPersons", firstServiceMaxPersons);
			firstAddedSpecialistName = props.getProperty("firstAddedSpecialistName", firstAddedSpecialistName);
			firstAddedSpecialistEmail = props.getProperty("firstAddedSpecialistEmail", firstAddedSpecialistEmail);
			firstAddedSpecialistPhone = props.getProperty("firstAddedSpecialistPhone", firstAddedSpecialistPhone);
			firstAddedSpecialistPassword = props.getProperty("firstAddedSpecialistPassword",
					firstAddedSpecialistPassword);
			locationScheduleMon = props.getProperty("orar_sediu_luni", locationScheduleMon);
			locationScheduleTue = props.getProperty("orar_sediu_marti", locationScheduleTue);
			locationScheduleWed = props.getProperty("orar_sediu_miercuri", locationScheduleWed);
			locationScheduleThu = props.getProperty("orar_sediu_joi", locationScheduleThu);
			locationScheduleFri = props.getProperty("orar_sediu_vineri", locationScheduleFri);
			locationScheduleSat = props.getProperty("orar_sediu_sambata", locationScheduleSat);
			locationScheduleSun = props.getProperty("orar_sediu_duminica", locationScheduleSun);
			staffScheduleMon = props.getProperty("orar_angajat_luni", locationScheduleMon);
			staffScheduleTue = props.getProperty("orar_angajat_marti", locationScheduleTue);
			staffScheduleWed = props.getProperty("orar_angajat_miercuri", locationScheduleWed);
			staffScheduleThu = props.getProperty("orar_angajat_joi", locationScheduleThu);
			staffScheduleFri = props.getProperty("orar_angajat_vineri", locationScheduleFri);
			staffScheduleSat = props.getProperty("orar_angajat_sambata", locationScheduleSat);
			staffScheduleSun = props.getProperty("orar_angajat_duminica", locationScheduleSun);
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
	public void creating_new_account_as_business_real_test_data() {

		endUser.navigateTo(ConfigUtils.getBaseUrl());
		endUser.refresh();
		endUser.click_on_inregistreaza_te();
		endUser.waitForPageToLoad();
		System.out.println("categorie " + businessCategory);
		endUser.selectBusinessCategory(businessCategory);
		endUser.fill_in_business_details(businessName, businessEmail, businessPhoneNo);

		endUser.click_on_register_button();

//		endUser.success_message_should_be_visible();
//		endUser.user_should_see_business_email_in_success_message();
		endUser.refresh();
		endUser.closeBrowser();
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
		loginStep.deleteAllCookies();
		// close browser
		loginStep.closeBrowser();
		
		// login with business account
		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.refresh();
		loginStep.login_into_business_account(businessEmail, businessPassword);
		// domain form
		businessWizardSteps.waitForWizardPageToLoad();

		businessWizardSteps.wizard_tex_should_be_dispayed(Constants.WIZARD_SUCCESS_MESSAGE_BUSINESS);
		businessWizardSteps.fill_in_business_address(businessAddress);
		businessWizardSteps.select_specific_county(businessMainLocationCounty);
		businessWizardSteps.select_specific_city(businessMainLocationCity);

		businessWizardSteps.fill_in_business_location_name(businessMainLocation);
		businessWizardSteps.fill_in_business_phone(businessPhoneNo);
		businessWizardSteps.click_on_set_business_schedule();
		businessWizardSteps.schedule_popup_should_appear();

		// schedule form

		List<String> daysOfweekList = new ArrayList<String>();
		daysOfweekList.add(locationScheduleMon);
		daysOfweekList.add(locationScheduleTue);
		daysOfweekList.add(locationScheduleWed);
		daysOfweekList.add(locationScheduleThu);
		daysOfweekList.add(locationScheduleFri);
		daysOfweekList.add(locationScheduleSat);
		daysOfweekList.add(locationScheduleSun);
		for (int i = 0; i < daysOfweekList.size(); i++) {
			if (!daysOfweekList.get(i).contentEquals(ro.evozon.tools.Constants.CLOSED_SCHEDULE)) {
				businessWizardSteps.fill_in_schedule_form_for_business(daysOfweekList.get(i), i);
			}
		}
		businessWizardSteps.click_on_save_schedule_business();
		// domain form
		businessWizardSteps.fill_in_domain_form(businessMainDomain);
		// service form
		//
		businessWizardSteps.fill_in_service_name(businessFirstService);
		businessWizardSteps.fill_in_service_max_persons(firstServiceMaxPersons);
		businessWizardSteps.fill_in_service_duration_per_service(businessFirstServiceDuration);
		businessWizardSteps.fill_in_service_price(businessFirstServicePrice);
		businessWizardSteps.click_on_save_service_form();

		businessWizardSteps.fill_in_staff_name(firstAddedSpecialistName);
		businessWizardSteps.fill_in_staff_email(firstAddedSpecialistEmail);
		businessWizardSteps.fill_in_staff_phone(firstAddedSpecialistPhone);

		// staff schedule

		businessWizardSteps.click_on_set_staff_schedule();
		List<String> daysOfweekStaffList = new ArrayList<String>();
		daysOfweekStaffList.add(staffScheduleMon);
		daysOfweekStaffList.add(staffScheduleTue);
		daysOfweekStaffList.add(staffScheduleWed);
		daysOfweekStaffList.add(staffScheduleThu);
		daysOfweekStaffList.add(staffScheduleFri);
		daysOfweekStaffList.add(staffScheduleSat);
		daysOfweekStaffList.add(staffScheduleSun);
		int length = daysOfweekStaffList.size();
		for (int i = 0; i < length; i++) {
			if (!daysOfweekStaffList.get(i).contentEquals(ro.evozon.tools.Constants.CLOSED_SCHEDULE)) {
				businessWizardSteps.fill_in_schedule_form_for_staff(daysOfweekStaffList.get(i), i);
			}
		}

		businessWizardSteps.click_on_save_staff_schedule();

		// assert overlay is displayed
		businessWizardSteps
				.expectedMessageShouldBeDispayedInWizardOverlay(Constants.SUCESS_MESSAGE_BUSINESS_WIZARD_COMPLETION);
		businessWizardSteps.dismiss_wizard_modal();
		businessWizardSteps.closeBrowser();
		loginStep.assertAll();

	}
}
