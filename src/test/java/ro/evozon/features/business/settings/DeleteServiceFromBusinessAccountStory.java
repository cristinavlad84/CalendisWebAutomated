package ro.evozon.features.business.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
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
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to delete exsting service from business account", "As business user ",
		"I want to be able to delet existing service and then see service is properly deleted" })
@RunWith(SerenityRunner.class)
public class DeleteServiceFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, serviceName, businessMainLocation,
			businessMainLocationCounty, businessMainLocationCity, servicePrice;

	public DeleteServiceFromBusinessAccountStory() {
		super();
		this.serviceName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.servicePrice = new DecimalFormat("#.00").format(
				FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Constants.MAX_SERVICE_PRICE));
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
			businessMainLocation = props.getProperty("businessMainLocation", businessMainLocation);
			businessMainLocationCounty = props.getProperty("businessMainLocationCounty", businessMainLocationCounty);
			businessMainLocationCity = props.getProperty("businessMainLocationCity", businessMainLocationCity);

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
	public AddItemToBusinessSteps addItemToBusinessSteps;
	@Steps
	public AddServiceToBusinessStep addServiceStep;
	@Steps
	BusinessWizardSteps businessWizardSteps;

	@Issue("#CLD-040")
	@Test
	public void delete_existing_service_then_verify_deleted() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		// addlocationSteps.c
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addServiceStep.click_on_add_service();
		addServiceStep.fill_in_service_name(serviceName);
		Serenity.setSessionVariable("selectedDomainForService").to(addServiceStep.select_random_domain_to_add_service());

		Serenity.setSessionVariable("serviceDuration").to(addServiceStep.select_random_service_duration());
		Serenity.setSessionVariable("serviceMaxPersons").to(addServiceStep.select_random_max_persons_per_service());
		addServiceStep.fill_in_service_price(servicePrice);
		addServiceStep.click_on_save_service_button();
		addServiceStep.verify_service_name_appears_in_service_section(serviceName);
		addServiceStep.verify_service_details_appears_in_service_section(serviceName, servicePrice,
				Serenity.sessionVariableCalled("serviceDuration").toString(),
				Serenity.sessionVariableCalled("serviceMaxPersons").toString());
		// // delete modified location
		addServiceStep.click_on_delete_service_link(serviceName);
		addItemToBusinessSteps.confirm_item_deletion_in_modal();
		addServiceStep.verify_service_name_not_displayed_in_service_section(serviceName);
		//
		addServiceStep.assertAll();
	}
}