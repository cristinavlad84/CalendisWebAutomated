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

import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.tools.PhonePrefixGenerators;

import ro.evozon.steps.serenity.business.AddLocationSteps;

import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order toadd new location to business account",
		"As business user ",
		"I want to be able to add new location and then see location saved" })
@RunWith(SerenityRunner.class)
public class AddNewLocationFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, locationName,
			locationStreet, locationPhone, newLocationName, newLocationStreet,
			newLocationPhone;

	public AddNewLocationFromBusinessAccountStory() {
		super();
		this.locationStreet = FieldGenerators.generateRandomString(6,
				Mode.ALPHA).concat(
				FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		this.newLocationStreet = FieldGenerators.generateRandomString(6,
				Mode.ALPHA).concat(
				FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		this.locationName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.newLocationName = FieldGenerators.generateRandomString(8,
				Mode.ALPHA);
		;
		this.locationPhone = PhonePrefixGenerators.generatePhoneNumber();
		this.newLocationPhone = PhonePrefixGenerators.generatePhoneNumber();
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

	@Steps
	public LoginBusinessAccountSteps loginStep;

	@Steps
	public AddLocationSteps addlocationSteps;
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
		addlocationSteps.click_on_location_left_menu();
		addlocationSteps.click_on_add_location();
		addlocationSteps.fill_in_location_name(locationName);
		addlocationSteps.fill_in_location_address(locationStreet);
		addlocationSteps.fill_in_location_phone(locationPhone);
		String locationRegion = addlocationSteps.select_random_region();
		String locationCity = addlocationSteps.select_random_city();
		addlocationSteps.click_on_set_location_schedule();
		addlocationSteps.select_days_of_week_for_location();
		addlocationSteps.click_on_save_location_button();
		addlocationSteps
				.verify_location_address_appears_in_location_section(locationStreet);
		addlocationSteps.verify_location_details_appears_in_location_section(
				locationStreet, locationRegion, locationCity, locationPhone,
				locationName);
		// modify created location
		addlocationSteps.click_on_modify_location_link(locationStreet);
		addlocationSteps.fill_in_location_name(newLocationName);
		addlocationSteps.fill_in_location_address(newLocationStreet);
		addlocationSteps.fill_in_location_phone(newLocationPhone);
		String newLocationRegion = addlocationSteps.select_random_region();
		String newLocationCity = addlocationSteps.select_random_city();
		addlocationSteps.click_on_set_location_schdule_editing();
		addlocationSteps.select_days_of_week_for_location();
		addlocationSteps.click_on_save_location_button();
		addlocationSteps
				.verify_location_address_appears_in_location_section(newLocationStreet);
		addlocationSteps.verify_location_details_appears_in_location_section(
				newLocationStreet, newLocationRegion, newLocationCity,
				newLocationPhone, newLocationName);
		// delete modified location
		addlocationSteps.click_on_delete_location_link(newLocationStreet);
		addlocationSteps.confirm_item_deletion_in_modal();
		addlocationSteps
				.verify_location_address_is_not_displayed_in_location_section(newLocationStreet);

		addlocationSteps.assertAll();
	}

}