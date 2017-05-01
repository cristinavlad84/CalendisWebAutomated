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
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add new service to business account",
		"As business user ",
		"I want to be able to add new service and then see service is properly saved" })
@RunWith(SerenityRunner.class)
public class AddNewServiceFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, serviceName,
			businessMainLocation, businessMainLocationCounty,
			businessMainLocationCity, servicePrice;

	public AddNewServiceFromBusinessAccountStory() {
		super();
		this.serviceName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.servicePrice = new DecimalFormat("#.00").format(FieldGenerators
				.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
						Constants.MAX_SERVICE_PRICE));
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
			businessMainLocation = props.getProperty("businessMainLocation",
					businessMainLocation);
			businessMainLocationCounty = props.getProperty(
					"businessMainLocationCounty", businessMainLocationCounty);
			businessMainLocationCity = props.getProperty(
					"businessMainLocationCity", businessMainLocationCity);

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

			String fileName = Constants.OUTPUT_PATH
					+ ConfigUtils.getOutputFileName();
			Properties props = new Properties();
			File file = new File(fileName);
			writer = new FileInputStream(file);
			props.load(writer);

			props.setProperty("serviceName", serviceName);
			props.setProperty("servicePrice", servicePrice);
			props.setProperty("selectedDomainForService", Serenity
					.sessionVariableCalled("selectedDomainForService")
					.toString());
			props.setProperty("serviceDuration", Serenity
					.sessionVariableCalled("serviceDuration").toString());
			props.setProperty("serviceMaxPersons", Serenity
					.sessionVariableCalled("serviceMaxPersons").toString());

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
	public AddItemToBusinessSteps addlocationSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;

	@Issue("#CLD-040")
	@Test
	public void add_new_service_then_verify_saved() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		// addlocationSteps.c
		addlocationSteps.click_on_sevice_left_menu();
		addlocationSteps.click_on_add_service();
		addlocationSteps.fill_in_service_name(serviceName);
		addlocationSteps.fill_in_service_price(servicePrice);
		Serenity.setSessionVariable("selectedDomainForService").to(
				addlocationSteps.select_domain_to_add_service());
		Serenity.setSessionVariable("serviceDuration").to(
				addlocationSteps.select_random_service_duration());
		Serenity.setSessionVariable("serviceMaxPersons").to(
				addlocationSteps.select_random_max_persons_per_service());
		addlocationSteps.click_on_save_service_button();
		addlocationSteps
				.verify_service_name_appears_in_service_section(serviceName);
		addlocationSteps.verify_service_details_appears_in_service_section(
				serviceName, servicePrice,
				Serenity.sessionVariableCalled("serviceDuration").toString(),
				Serenity.sessionVariableCalled("serviceMaxPersons").toString());
		addlocationSteps.assertAll();
	}
}