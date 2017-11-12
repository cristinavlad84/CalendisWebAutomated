package ro.evozon.features.business.settings;

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
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.tools.enums.PhonePrefixGenerators;
import ro.evozon.tools.enums.StaffType;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = {

		"As business user ",
		"I want to be able to edit existing  specialist and then see saved edits in personal section settings" })
@RunWith(SerenityRunner.class)
public class EditSpecialistFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, specialistEmail, specialistPassword, specialistName,
			specialistPhoneNo, newSpecialistName, newSpecialistEmail, newSpecialistPhone, newSpecialistPassword;

	public EditSpecialistFromBusinessAccountStory() {
		super();

		this.specialistEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.newSpecialistEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.specialistPassword = FieldGenerators.generateRandomString(8, Mode.ALPHANUMERIC);
		this.newSpecialistPassword = FieldGenerators.generateRandomString(8, Mode.ALPHANUMERIC);
		this.specialistName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.newSpecialistName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.specialistPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		this.newSpecialistPhone = PhonePrefixGenerators.generatePhoneNumber();
	}

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileName();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			businessName = props.getProperty("businessName", businessName);
			businessEmail = props.getProperty("businessEmail", businessEmail);
			businessPassword = props.getProperty("businessPassword", businessPassword);

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
	@Steps
	public AddItemToBusinessSteps addItemsSteps;
	@Steps
	public AddStaffToBusinessStep addSpecialitsSteps;
	@Steps
	public StaffSteps staffSteps;

	@Issue("#CLD-043")
	@Test
	public void edit_specialist_details() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		addSpecialitsSteps.click_on_add_new_staff_button();
		addSpecialitsSteps.fill_in_staff_name(specialistName);
		addSpecialitsSteps.fill_in_staff_email(specialistEmail);
		addSpecialitsSteps.fill_in_staff_phone(specialistPhoneNo);
		addSpecialitsSteps.select_staff_type_to_add(StaffType.EMPL.toString());
		addSpecialitsSteps.check_default_location_for_staff();

		addSpecialitsSteps.click_on_set_staff_schedule();
		addSpecialitsSteps.select_day_of_week_for_staff_schedule();

		addSpecialitsSteps.click_on_save_staff_schedule();

		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(specialistName);
		addItemsSteps.wait_for_saving_alert_to_disappear();
		// edit specialist details
		addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		// Thread.sleep(9000);
		addSpecialitsSteps.fill_in_staff_name(newSpecialistName);
		addSpecialitsSteps.fill_in_staff_email(newSpecialistEmail);
		addSpecialitsSteps.fill_in_staff_phone(newSpecialistPhone);
		addSpecialitsSteps.click_on_save_staff_edits();
		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(newSpecialistName);
		addSpecialitsSteps.is_staff_email_displayed_in_personal_section(newSpecialistName, newSpecialistEmail);
		addSpecialitsSteps.is_staff_phone_displayed_in_personal_section(newSpecialistName, newSpecialistPhone);
		addSpecialitsSteps.click_on_delete_staff_link(newSpecialistName);
		addSpecialitsSteps.confirm_staff_deletion();
		addSpecialitsSteps.assertAll();

	}

}