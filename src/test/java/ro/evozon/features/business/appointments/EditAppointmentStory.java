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
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to edit existing appointment from business account", "As business user ",
		"I want to be able to edit existing appointement and then see edits are properly saved" })
@RunWith(SerenityRunner.class)
public class EditAppointmentStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, clientLastName, clientFirstName, clientEmail,
			clientPhoneNo, clientEditLastName, clientEditFirstName, clientEditEmail, clientEditPhoneNo,
			selectedDomainForService, businessLocation, businessFirstServicePrice, businessFirstService,
			firstAddedSpecialistName, businessFirstServiceDuration;

	int serviceDuration, serviceDurationEdit;

	public EditAppointmentStory() {
		super();
		this.clientLastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientFirstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.clientPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		this.clientEditLastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientEditFirstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientEditEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.clientEditPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
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
		this.serviceDurationEdit = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
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
			selectedDomainForService = props.getProperty("businessMainDomain", selectedDomainForService);
			businessLocation = props.getProperty("businessMainLocation", businessLocation);
			businessFirstServicePrice = props.getProperty("businessFirstServicePrice", businessFirstServicePrice);
			businessFirstService = props.getProperty("businessFirstService", businessFirstService);
			firstAddedSpecialistName = props.getProperty("firstAddedSpecialistName", firstAddedSpecialistName);
			businessFirstServiceDuration = props.getProperty("businessFirstServiceDuration",
					businessFirstServiceDuration);
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

	@Issue("#CLD-061")
	@Test
	public void edit_existing_appointment_then_verify_saved() {
		selectedDomainForService = ConfigUtils.capitalizeFirstLetter(selectedDomainForService);
		businessFirstService = ConfigUtils.capitalizeFirstLetter(businessFirstService);
		firstAddedSpecialistName = ConfigUtils.capitalizeFirstLetter(firstAddedSpecialistName);
		businessLocation= ConfigUtils.capitalizeFirstLetter(businessLocation);
		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.dismiss_any_popup_if_appears();
		// addlocationSteps.c
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.select_location_calendar_tab(businessLocation);
		addAppointmentToBusinessStep.click_on_quick_appointment_button();
		// fill in service card details with first service created for business
		addAppointmentToBusinessStep.select_service_for_appointment(businessFirstService);

		addAppointmentToBusinessStep.select_specialist_for_appointment(firstAddedSpecialistName);
		addAppointmentToBusinessStep.select_domain_for_appointment(selectedDomainForService);

		addAppointmentToBusinessStep.fill_in_duration_for_service_appointment(businessFirstServiceDuration);
		String appointmentDate = addAppointmentToBusinessStep
				.select_time_details_for_service_appointment_form(businessFirstService,Constants.HOUR_MIN_LIMIT, Constants.HOUR_MAX_LIMIT);
		addAppointmentToBusinessStep.fill_in_client_details_card_appointment_form(clientLastName, clientFirstName,
				clientEmail, clientPhoneNo);
		addAppointmentToBusinessStep.click_on_save_appointment();
		addAppointmentToBusinessStep.confirm_appointment_out_of_interval();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
		LocalDateTime date = LocalDateTime.parse(appointmentDate, formatter);
		System.out.println(date);
		LocalTime startHour = LocalTime.from(date);
		System.out.println("start time" + startHour);
		LocalDateTime endTime = date.plusMinutes(Integer.parseInt(businessFirstServiceDuration));
		LocalTime endHour = LocalTime.from(endTime);
		System.out.println("end time" + endHour);
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.select_location_calendar_tab(businessLocation);
		addAppointmentToBusinessStep.click_on_day_view_in_calendar();
		addAppointmentToBusinessStep.click_on_mini_calendar();
		addAppointmentToBusinessStep.navigate_to_target_date_in_mini_calendar(appointmentDate);
		addAppointmentToBusinessStep.select_domain_calendar_left_menu(selectedDomainForService);
		addAppointmentToBusinessStep.select_service_calendar_left_menu(selectedDomainForService, businessFirstService);
		addAppointmentToBusinessStep.select_specialist_calendar_left_menu(firstAddedSpecialistName);

		addAppointmentToBusinessStep.get_appointment_details_for(startHour.toString(), endHour.toString(),
				businessFirstService);
		addAppointmentToBusinessStep.click_on_appointment_on_calendar(startHour.toString(), endHour.toString(),
				businessFirstService);
		addAppointmentToBusinessStep.click_on_service_card_to_edit_appointment_form();
		// edit all fields on appointment
		String serviceEdit = addAppointmentToBusinessStep.select_random_service();
		serviceEdit = ConfigUtils.capitalizeFirstLetter(serviceEdit);
		String domainEdit = addAppointmentToBusinessStep.select_random_domain();
		domainEdit = ConfigUtils.capitalizeFirstLetter(domainEdit);
		String specialistEdit = addAppointmentToBusinessStep.select_random_specialist();
		specialistEdit = ConfigUtils.capitalizeFirstLetter(specialistEdit);

		addAppointmentToBusinessStep.fill_in_duration_for_service_appointment(Integer.toString(serviceDurationEdit));
		String appointmentDateEdit = addAppointmentToBusinessStep
				.select_time_details_for_service_appointment_form(serviceEdit,Constants.HOUR_MIN_LIMIT, Constants.HOUR_MAX_LIMIT);
		System.out.println("edit app date" + appointmentDateEdit);
		addAppointmentToBusinessStep.click_on_save_appointment();
		addAppointmentToBusinessStep.confirm_appointment_out_of_interval();
		// verify edits are saved

		LocalDateTime dateEdit = LocalDateTime.parse(appointmentDateEdit, formatter);
		System.out.println("edit date" + dateEdit);
		LocalTime startHourEdit = LocalTime.from(dateEdit);
		System.out.println("start time edit" + startHourEdit);
		LocalDateTime endTimeEdit = dateEdit.plusMinutes(serviceDurationEdit);
		LocalTime endHourEdit = LocalTime.from(endTimeEdit);
		System.out.println("end time edit " + endHourEdit);
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.select_location_calendar_tab(businessLocation);
		addAppointmentToBusinessStep.click_on_day_view_in_calendar();
		addAppointmentToBusinessStep.click_on_mini_calendar();
		addAppointmentToBusinessStep.navigate_to_target_date_in_mini_calendar(appointmentDateEdit);
		addAppointmentToBusinessStep.select_domain_calendar_left_menu(domainEdit);
		addAppointmentToBusinessStep.select_service_calendar_left_menu(domainEdit, serviceEdit);

		addAppointmentToBusinessStep.select_specialist_calendar_left_menu(specialistEdit);
		addAppointmentToBusinessStep.get_appointment_details_for(startHourEdit.toString(), endHourEdit.toString(),
				serviceEdit);
		addAppointmentToBusinessStep.assertAll();
	}
}