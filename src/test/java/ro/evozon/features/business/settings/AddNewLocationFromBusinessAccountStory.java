package ro.evozon.features.business.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.serenitybdd.core.Serenity;
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
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddLocationToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order toadd new location to business account", "As business user ",
		"I want to be able to add new location and then see location saved" })
@RunWith(SerenityRunner.class)
public class AddNewLocationFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, locationName, locationStreet, locationPhone,
			newLocationName, newLocationStreet, newLocationPhone;

	public AddNewLocationFromBusinessAccountStory() {
		super();
		this.locationStreet = FieldGenerators.generateRandomString(6, Mode.ALPHA)
				.concat(FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		this.newLocationStreet = FieldGenerators.generateRandomString(6, Mode.ALPHA)
				.concat(FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		this.locationName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.newLocationName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.locationPhone = PhonePrefixGenerators.generatePhoneNumber();
		this.newLocationPhone = PhonePrefixGenerators.generatePhoneNumber();
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

	@After
	public void writeToPropertiesFile() {
		FileOutputStream fileOut = null;
		FileInputStream writer = null;
		try {

			String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileName();
			Properties props = new Properties();
			File file = new File(fileName);
			writer = new FileInputStream(file);
			props.load(writer);

			props.setProperty("locationName", locationName);
			props.setProperty("locationStreet", locationStreet);
			props.setProperty("locationPhone", locationPhone);
			props.setProperty("locationCity", Serenity.sessionVariableCalled("locationCity").toString());
			props.setProperty("locationRegion", Serenity.sessionVariableCalled("locationRegion").toString());

			fileOut = new FileOutputStream(file);
			props.store(fileOut, "business user details");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Steps
	public LoginBusinessAccountSteps loginStep;
	@Steps
	public AddItemToBusinessSteps addItemToBusinessSteps;
	@Steps
	public AddLocationToBusinessStep addLocationToBusinessSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;

	@Issue("#CLD-038")
	@Test
	public void add_new_location_then_verify_saved() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		addItemToBusinessSteps.click_on_location_left_menu();
		addLocationToBusinessSteps.click_on_add_location();
		addLocationToBusinessSteps.fill_in_location_name(locationName);
		addLocationToBusinessSteps.fill_in_location_address(locationStreet);
		addLocationToBusinessSteps.fill_in_location_phone(locationPhone);
		Serenity.setSessionVariable("locationRegion").to(addLocationToBusinessSteps.select_random_location_region());
		Serenity.setSessionVariable("locationCity").to(addLocationToBusinessSteps.select_random_location_city());
		addLocationToBusinessSteps.click_on_set_location_schedule();
		for (int i = 0; i < Constants.NO_OF_WEEK_DAYS; i++) {
			System.out.println("i = " + i);

			addLocationToBusinessSteps.check_schedule_day_of_week_location(Constants.RANGE_HOURS, i);

		}

		addLocationToBusinessSteps.click_on_save_location_button();
		addLocationToBusinessSteps.verify_location_address_appears_in_location_section(locationStreet);
		addLocationToBusinessSteps.verify_location_details_appears_in_location_section(locationStreet,
				Serenity.sessionVariableCalled("locationRegion").toString(),
				Serenity.sessionVariableCalled("locationCity").toString(), locationPhone, locationName);

		addLocationToBusinessSteps.assertAll();
	}

}