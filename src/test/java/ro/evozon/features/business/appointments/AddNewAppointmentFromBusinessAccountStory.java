package ro.evozon.features.business.appointments;

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
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.business.AddAppointmentToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add rapid appointment to business account", "As business user ",
		"I want to be able to add new appointm ent and then see it is properly saved" })
@RunWith(SerenityRunner.class)
public class AddNewAppointmentFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, businessMainLocation, businessMainLocationCounty,
			businessMainLocationCity, clientLastName, clientFirstName, clientEmail, clientPhoneNo;

	public AddNewAppointmentFromBusinessAccountStory() {
		super();
		this.clientLastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientFirstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.clientPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
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

	// @After
	// public void writeToPropertiesFile() {
	// FileOutputStream fileOut = null;
	// FileInputStream writer = null;
	// try {
	//
	// String fileName = Constants.OUTPUT_PATH +
	// ConfigUtils.getOutputFileName();
	// Properties props = new Properties();
	// File file = new File(fileName);
	// writer = new FileInputStream(file);
	// props.load(writer);
	//
	// props.setProperty("serviceName", serviceName);
	// props.setProperty("servicePrice", servicePrice);
	// props.setProperty("selectedDomainForService",
	// Serenity.sessionVariableCalled("selectedDomainForService").toString());
	// props.setProperty("serviceDuration",
	// Serenity.sessionVariableCalled("serviceDuration").toString());
	// props.setProperty("serviceMaxPersons",
	// Serenity.sessionVariableCalled("serviceMaxPersons").toString());
	//
	// fileOut = new FileOutputStream(file);
	// props.store(fileOut, "business user details");
	// writer.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Steps
	public LoginBusinessAccountSteps loginStep;

	@Steps
	public AddItemToBusinessSteps addItemToBusinessSteps;
	@Steps
	public AddAppointmentToBusinessStep addAppointmentToBusinessStep;
	@Steps
	NavigationStep navigationStep;

	@Issue("#CLD-056")
	@Test
	public void add_new_appontment_then_verify_saved() {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();

		// loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		// addlocationSteps.c
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.click_on_quick_appointment_button();
		addAppointmentToBusinessStep.select_random_domain();
		addAppointmentToBusinessStep.select_random_specialist();
		addAppointmentToBusinessStep.select_random_service();
		addAppointmentToBusinessStep.fill_in_client_last_name(clientLastName);
		addAppointmentToBusinessStep.fill_in_client_first_name(clientFirstName);
		addAppointmentToBusinessStep.fill_in_client_email(clientEmail);
		addAppointmentToBusinessStep.fill_in_client_phone_number(clientPhoneNo);
		addAppointmentToBusinessStep.click_on_save_appointment();
		addAppointmentToBusinessStep.confirm_appointment_out_of_interval();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// addServiceStep.click_on_add_service();
		// addServiceStep.fill_in_service_name(serviceName);
		// addServiceStep.fill_in_service_price(servicePrice);
		// Serenity.setSessionVariable("selectedDomainForService").to(addServiceStep.select_domain_to_add_service());
		// Serenity.setSessionVariable("serviceDuration").to(addServiceStep.select_random_service_duration());
		// Serenity.setSessionVariable("serviceMaxPersons").to(addServiceStep.select_random_max_persons_per_service());
		// addServiceStep.click_on_save_service_button();
		// addServiceStep.verify_service_name_appears_in_service_section(serviceName);
		// addServiceStep.verify_service_details_appears_in_service_section(serviceName,
		// servicePrice,
		// Serenity.sessionVariableCalled("serviceDuration").toString(),
		// Serenity.sessionVariableCalled("serviceMaxPersons").toString());
		addAppointmentToBusinessStep.assertAll();
	}
}