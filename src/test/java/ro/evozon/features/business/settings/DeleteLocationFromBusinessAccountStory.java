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
		"I want to be able to delete location and then see location deleted" })
@RunWith(SerenityRunner.class)
public class DeleteLocationFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, locationName, locationStreet, locationPhone,
			newLocationName, newLocationStreet, newLocationPhone;

	public DeleteLocationFromBusinessAccountStory() {
		super();
		this.locationStreet = FieldGenerators.generateRandomString(6, Mode.ALPHA)
				.concat(FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		this.newLocationStreet = FieldGenerators.generateRandomString(6, Mode.ALPHA)
				.concat(FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		this.locationName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.newLocationName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		;
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

	@Steps
	public LoginBusinessAccountSteps loginStep;
	@Steps
	public AddItemToBusinessSteps addItemToBusinessStep;
	@Steps
	BusinessWizardSteps businessWizardSteps;
	@Steps
	public AddLocationToBusinessStep addLocationToBusinessSteps;

	@Issue("#CLD-038")
	@Test
	public void edit_location_then_verify_saved() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		addItemToBusinessStep.click_on_location_left_menu();
		addLocationToBusinessSteps.click_on_add_location();
		addLocationToBusinessSteps.fill_in_location_name(locationName);
		addLocationToBusinessSteps.fill_in_location_address(locationStreet);
		addLocationToBusinessSteps.fill_in_location_phone(locationPhone);
		Serenity.setSessionVariable("locationRegion").to(addLocationToBusinessSteps.select_random_location_region());
		Serenity.setSessionVariable("locationCity").to(addLocationToBusinessSteps.select_random_location_city());
		addLocationToBusinessSteps.click_on_set_location_schedule();
		addLocationToBusinessSteps.select_days_of_week_for_location();
		addLocationToBusinessSteps.click_on_save_location_button();
		addLocationToBusinessSteps.verify_location_address_appears_in_location_section(locationStreet);
		addLocationToBusinessSteps.verify_location_details_appears_in_location_section(locationStreet,
				Serenity.sessionVariableCalled("locationRegion").toString(),
				Serenity.sessionVariableCalled("locationCity").toString(), locationPhone, locationName);
		// modify created location
		addLocationToBusinessSteps.click_on_modify_location_link(locationStreet);
		addLocationToBusinessSteps.fill_in_location_name(newLocationName);
		addLocationToBusinessSteps.fill_in_location_address(newLocationStreet);
		addLocationToBusinessSteps.fill_in_location_phone(newLocationPhone);
		Serenity.setSessionVariable("newLocationRegion").to(addLocationToBusinessSteps.select_random_location_region());
		Serenity.setSessionVariable("newLocationCity").to(addLocationToBusinessSteps.select_random_location_city());
		addLocationToBusinessSteps.click_on_set_location_schdule_editing();
		addLocationToBusinessSteps.select_days_of_week_for_location();
		addLocationToBusinessSteps.click_on_save_location_button();
		addLocationToBusinessSteps.verify_location_address_appears_in_location_section(newLocationStreet);
		addLocationToBusinessSteps.verify_location_details_appears_in_location_section(newLocationStreet,
				Serenity.sessionVariableCalled("newLocationRegion").toString(),
				Serenity.sessionVariableCalled("newLocationCity").toString(), newLocationPhone, newLocationName);

		// to add to another test
		// delete modified location
		addLocationToBusinessSteps.click_on_delete_location_link(newLocationStreet);
		addItemToBusinessStep.confirm_item_deletion_in_modal();
		addLocationToBusinessSteps.verify_location_address_is_not_displayed_in_location_section(newLocationStreet);

		addLocationToBusinessSteps.assertAll();
	}

}