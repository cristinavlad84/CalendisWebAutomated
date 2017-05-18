package ro.evozon.features.business.appointments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add  appointment with multiple clients to business account", "As business user ",
		"I want to be able to add new appointement with multiple clients and then see it is properly saved" })
@RunWith(SerenityRunner.class)
public class AddMultipleClientsAppointmentStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, businessMainLocation, businessMainLocationCounty,
			domainAssociatedLocationName, businessMainLocationCity, specialistName, serviceName, clientOneLastName,
			clientOneFirstName, clientOneEmail, clientOnePhoneNo, clientTwoLastName, clientTwoFirstName, clientTwoEmail,
			clientTwoPhoneNo, servicePrice;

	int serviceDuration;
	final String maxPersons = "2";

	public AddMultipleClientsAppointmentStory() {
		super();
		this.clientOneLastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientOneFirstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientOneEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.clientOnePhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		this.clientTwoLastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientTwoFirstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientTwoEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.clientTwoPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		this.servicePrice = new DecimalFormat("#.00").format(
				FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Constants.MAX_SERVICE_PRICE));
		this.serviceName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.serviceDuration = FieldGenerators.getRandomIntegerBetween(3, 12) * 5; // from
																					// 3
																					// for
																					// client
																					// preview
																					// appoitment
																					// in
																					// calendar->
																					// to
																					// be
																					// visible
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
			domainAssociatedLocationName = props.getProperty("selectedDomainForService", domainAssociatedLocationName);
			specialistName = props.getProperty("firstAddedSpecialistName", specialistName);
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
	public AddAppointmentToBusinessStep addAppointmentToBusinessStep;
	@Steps
	NavigationStep navigationStep;
	@Steps
	public AddServiceToBusinessStep addServiceStep;
	@Steps
	public AddStaffToBusinessStep addSpecialitsSteps;

	@Issue("#CLD-058")
	@Test
	public void add_multiple_appointment_then_verify_saved() {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.dismiss_any_popup_if_appears();
		// add service with multiple persons
		loginStep.click_on_settings();

		addItemToBusinessSteps.click_on_sevice_left_menu();
		addServiceStep.click_on_add_service();
		addServiceStep.fill_in_service_name(serviceName);
		addServiceStep.fill_in_service_price(servicePrice);
		addServiceStep.select_domain_to_add_service(domainAssociatedLocationName);
		addServiceStep.fill_in_service_duration_per_service(Integer.toString(serviceDuration));
		addServiceStep.fill_in_max_persons_per_service(maxPersons);
		addServiceStep.click_on_save_service_button();
		addServiceStep.verify_service_name_appears_in_service_section(serviceName);
		addServiceStep.verify_service_details_appears_in_service_section(serviceName, servicePrice,
				Integer.toString(serviceDuration), maxPersons);
		// assign newly created services to specialist
		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(specialistName);
		addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		addSpecialitsSteps.select_service_domain_location_for_specialist(businessMainLocation,
				domainAssociatedLocationName, serviceName);
		addSpecialitsSteps.click_on_save_staff_edits();
		addItemToBusinessSteps.wait_for_saving_alert();
		// end assigning
		// end create test data

		// create servie with multiple clients test
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.click_on_quick_appointment_button();
		// fill in service card details
		addAppointmentToBusinessStep.fill_in_service_details_for_appointment(domainAssociatedLocationName,
				specialistName, serviceName, serviceDuration);
		String appointmentDate = addAppointmentToBusinessStep.select_time_details_for_service_appointment_form();

		addAppointmentToBusinessStep.fill_in_client_details_card_appointment_form(clientOneLastName, clientOneFirstName,
				clientOneEmail, clientOnePhoneNo);
		addAppointmentToBusinessStep.click_on_add_another_client();
		addAppointmentToBusinessStep.fill_in_client_details_card_appointment_form(clientTwoLastName, clientTwoFirstName,
				clientTwoEmail, clientTwoPhoneNo);
		addAppointmentToBusinessStep.click_on_save_appointment();
		addAppointmentToBusinessStep.confirm_appointment_out_of_interval();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
		LocalDateTime date = LocalDateTime.parse(appointmentDate, formatter);
		System.out.println(date);
		LocalTime startHour = LocalTime.from(date);
		System.out.println("start time" + startHour);
		LocalDateTime endTime = date.plusMinutes(serviceDuration);
		LocalTime endHour = LocalTime.from(endTime);
		System.out.println("end time" + endHour);
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.click_on_day_view_in_calendar();
		addAppointmentToBusinessStep.click_on_mini_calendar();
		addAppointmentToBusinessStep.navigate_to_target_date_in_mini_calendar(appointmentDate);
		addAppointmentToBusinessStep.select_domain_calendar_left_menu(domainAssociatedLocationName);
		addAppointmentToBusinessStep.select_service_calendar_left_menu(domainAssociatedLocationName, serviceName);
		addAppointmentToBusinessStep.select_specialist_calendar_left_menu(specialistName);
		addAppointmentToBusinessStep.get_appointment_details_for(startHour.toString(), endHour.toString(), serviceName);

		// click on appointment and open form to see clients are saved
		addAppointmentToBusinessStep.click_on_appointment_on_calendar(startHour.toString(), endHour.toString(), serviceName);
		addAppointmentToBusinessStep.expand_client_card_by_index(0);
		addAppointmentToBusinessStep.verify_client_details_on_appointment_form(clientOneLastName, clientOneFirstName,
				clientOneEmail, clientOnePhoneNo);
		addAppointmentToBusinessStep.expand_client_card_by_index(1);
		addAppointmentToBusinessStep.verify_client_details_on_appointment_form(clientTwoLastName, clientTwoFirstName,
				clientTwoEmail, clientTwoPhoneNo);
		// verify name, lastname phone no

		addAppointmentToBusinessStep.assertAll();
	}
}