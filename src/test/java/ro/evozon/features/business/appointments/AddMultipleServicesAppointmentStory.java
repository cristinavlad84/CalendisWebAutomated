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

@Narrative(text = { "In order to add quick appointment to business account", "As business user ",
		"I want to be able to add new appointement and then see it is properly saved" })
@RunWith(SerenityRunner.class)
public class AddMultipleServicesAppointmentStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, 
			businessDomain, clientLastName, clientFirstName, clientEmail, clientPhoneNo;

	int serviceDuration;

	public AddMultipleServicesAppointmentStory() {
		super();
		this.clientLastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientFirstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.clientPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
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
			businessDomain = props.getProperty("businessPassword", businessPassword);
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

	@Issue("#CLD-056")
	@Test
	public void add_new_appointment_then_verify_saved() {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.dismiss_any_popup_if_appears();
		// addlocationSteps.c
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.click_anywhere_in_calendar();
		String domain = addAppointmentToBusinessStep.select_random_domain();
//		String specialist = addAppointmentToBusinessStep.select_random_specialist();
//		String service = addAppointmentToBusinessStep.select_random_service();
//		addAppointmentToBusinessStep.fill_in_duration_for_service_appointment(Integer.toString(serviceDuration));
//		String monthYear = addAppointmentToBusinessStep.select_random_month_year_for_appointment();
//		monthYear = ConfigUtils.formatMonthString(monthYear);
//		monthYear = ConfigUtils.formatYearString(monthYear);
//		String day = addAppointmentToBusinessStep.select_random_day();
//		day = ConfigUtils.extractDayOfWeek(day);
//		String hour = addAppointmentToBusinessStep.select_random_hour_for_appointment();
//		String minutes = addAppointmentToBusinessStep.select_random_minutes_for_appointment();
//
//		addAppointmentToBusinessStep.fill_in_client_last_name(clientLastName);
//		addAppointmentToBusinessStep.fill_in_client_first_name(clientFirstName);
//		addAppointmentToBusinessStep.fill_in_client_email(clientEmail);
//		addAppointmentToBusinessStep.fill_in_client_phone_number(clientPhoneNo);
//		addAppointmentToBusinessStep.click_on_save_appointment();
//		addAppointmentToBusinessStep.confirm_appointment_out_of_interval();
//
//		System.out.println(monthYear + day + hour + minutes);
//		String appointmentDate = monthYear + " " + day + " " + hour + " " + minutes;
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
//		LocalDateTime date = LocalDateTime.parse(appointmentDate, formatter);
//		System.out.println(date);
//		LocalTime startHour = LocalTime.from(date);
//		System.out.println("start time" + startHour);
//		LocalDateTime endTime = date.plusMinutes(serviceDuration);
//		LocalTime endHour = LocalTime.from(endTime);
//		System.out.println("end time" + endHour);
//		navigationStep.click_on_calendar_tab();
//		addAppointmentToBusinessStep.click_on_day_view_in_calendar();
//		addAppointmentToBusinessStep.click_on_mini_calendar();
//		addAppointmentToBusinessStep.navigate_to_target_date_in_mini_calendar(appointmentDate);
//		addAppointmentToBusinessStep.select_domain_calendar_left_menu(domain);
//		addAppointmentToBusinessStep.select_service_calendar_left_menu(domain, service);
//		addAppointmentToBusinessStep.select_specialist_calendar_left_menu(specialist);
//		addAppointmentToBusinessStep.get_appointment_details_for(startHour.toString(), endHour.toString(), service);	
		addAppointmentToBusinessStep.assertAll();
	}
}