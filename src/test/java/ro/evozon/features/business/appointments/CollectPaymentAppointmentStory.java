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

@Narrative(text = { "In order to collect payment for existing appointment from business account", "As business user ",
		"I want to be able to collect payment for existing appointement and then see payment is made properly" })
@RunWith(SerenityRunner.class)
public class CollectPaymentAppointmentStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, clientLastName, clientFirstName, clientEmail,
			clientPhoneNo, businessFirstServicePrice, businessFirstService, firstAddedSpecialistName,
			selectedDomainForService, businessFirstServiceDuration;

	int serviceDuration;

	public CollectPaymentAppointmentStory() {
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
			selectedDomainForService = props.getProperty("selectedDomainForService", selectedDomainForService);
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

	@Issue("#CLD-062")
	@Test
	public void edit_existing_appointment_then_verify_saved() {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed
		// use first added service
		loginStep.logout_link_should_be_displayed();
		loginStep.dismiss_any_popup_if_appears();
		navigationStep.acceptCookies();
		// addlocationSteps.c
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.click_on_quick_appointment_button();
		// fill in service card details with first service created for business
		addAppointmentToBusinessStep.select_domain_for_appointment(selectedDomainForService);
		addAppointmentToBusinessStep.select_specialist_for_appointment(firstAddedSpecialistName);
		addAppointmentToBusinessStep.select_service_for_appointment(businessFirstService);
		addAppointmentToBusinessStep.fill_in_duration_for_service_appointment(businessFirstServiceDuration);
		String appointmentDate = addAppointmentToBusinessStep.select_time_details_for_service_appointment_form();
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
		addAppointmentToBusinessStep.click_on_collect_payment_appointment_form();
		addAppointmentToBusinessStep.click_on_collect_button_on_client_card();
		addAppointmentToBusinessStep.verify_total_price_on_appointment_form(businessFirstServicePrice);
		addAppointmentToBusinessStep.click_on_payment_history();
		addAppointmentToBusinessStep.verify_last_payment_in_payment_history(businessFirstServicePrice);
		addAppointmentToBusinessStep.assertAll();
	}
}