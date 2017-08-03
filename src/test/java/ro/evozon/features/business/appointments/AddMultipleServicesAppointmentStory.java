package ro.evozon.features.business.appointments;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.business.AddAppointmentToBusinessStep;
import ro.evozon.steps.serenity.business.AddDomainToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add quick appointment with multiple services to business account", "As business user ",
		"I want to be able to add new appointement with multiple services and then see it is properly saved" })
@RunWith(SerenityRunner.class)
public class AddMultipleServicesAppointmentStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, clientLastName, clientFirstName, clientEmail,
			clientPhoneNo, businessLocation, domainAssociatedLocationName, specialistName, serviceNameFirst,
			serviceNameSecond, servicePriceFirst, servicePriceSecond,domainName;

	int serviceDurationFirst, serviceDurationSecond;
	final String maxPersons = "1";

	public AddMultipleServicesAppointmentStory() {
		super();
		this.clientLastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientFirstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.clientPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		this.serviceNameFirst = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.serviceNameSecond = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.servicePriceFirst = new DecimalFormat("#.00").format(
				FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Constants.MAX_SERVICE_PRICE));
		this.servicePriceSecond = new DecimalFormat("#.00").format(
				FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Constants.MAX_SERVICE_PRICE));
		this.serviceDurationFirst = FieldGenerators.getRandomIntegerBetween(3, 12) * 5; // from
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
		this.serviceDurationSecond = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		this.domainName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
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
			businessLocation = props.getProperty("businessMainLocation", businessLocation);
			domainAssociatedLocationName = props.getProperty("domainAssociatedLocationName", domainAssociatedLocationName);
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
	@Steps
	public AddDomainToBusinessStep addDomainSteps;

	@Issue("#CLD-057")
	@Test
	public void add_new_appointment_with_multiple_services_then_verify_saved() {
		businessLocation = ConfigUtils.capitalizeFirstLetter(businessLocation);
		domainAssociatedLocationName = ConfigUtils.capitalizeFirstLetter(domainAssociatedLocationName);
		domainName=ConfigUtils.capitalizeFirstLetter(domainName);
		serviceNameFirst = ConfigUtils.capitalizeFirstLetter(serviceNameFirst);
		serviceNameSecond = ConfigUtils.capitalizeFirstLetter(serviceNameSecond);
		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.dismiss_any_popup_if_appears();

		// create test data: 2 new services with 1 person

		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		//add new domain
		addItemToBusinessSteps.click_on_domain_left_menu();
		addDomainSteps.click_on_add_domain();
		addDomainSteps.select_location_in_domain_form(businessLocation);
		addDomainSteps.fill_in_domain_name(domainName);
		addDomainSteps.click_on_save_domain_button();
		addDomainSteps.verify_domain_name_appears_in_domain_section(domainName);
		//
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addServiceStep.click_on_add_service();
		addServiceStep.fill_in_service_name(serviceNameFirst);
		addServiceStep.select_domain_to_add_service(domainName);
		addServiceStep.fill_in_service_price(servicePriceFirst);

		addServiceStep.fill_in_service_duration_per_service(Integer.toString(serviceDurationFirst));
		addServiceStep.fill_in_max_persons_per_service(maxPersons);
		addServiceStep.click_on_save_service_button();
		navigationStep.refresh();
		WebElement serviceElFirst = addServiceStep.get_service_webelement_in_list(
				the("Servicii individuale", containsString(ConfigUtils.capitalizeFirstLetter(serviceNameFirst))));
		addServiceStep.verify_service_name_not_displayed_in_service_section(
				ConfigUtils.capitalizeFirstLetter(serviceNameFirst));
		addServiceStep.verify_service_details_appears_in_service_section(serviceElFirst, servicePriceFirst,
				Integer.toString(serviceDurationFirst), maxPersons);
		// create 2'nd service
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addServiceStep.click_on_add_service();
		addServiceStep.fill_in_service_name(serviceNameSecond);
		addServiceStep.fill_in_service_price(servicePriceSecond);
		addServiceStep.select_domain_to_add_service(domainName);
		addServiceStep.fill_in_service_duration_per_service(Integer.toString(serviceDurationSecond));
		addServiceStep.fill_in_max_persons_per_service(maxPersons);
		addServiceStep.click_on_save_service_button();
		WebElement serviceElSecond = addServiceStep.get_service_webelement_in_list(
				the("Servicii individuale", containsString(ConfigUtils.capitalizeFirstLetter(serviceNameSecond))));
		addServiceStep.verify_service_name_is_displayed_in_service_section(
				ConfigUtils.capitalizeFirstLetter(serviceNameSecond));
		addServiceStep.verify_service_details_appears_in_service_section(serviceElSecond, servicePriceSecond,
				Integer.toString(serviceDurationSecond), maxPersons);
		// assign newly created services to specialist
		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(specialistName);
		addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		addSpecialitsSteps.select_service_domain_location_for_specialist(businessLocation, domainName,
				serviceNameFirst);
		addSpecialitsSteps.click_on_save_staff_edits();
		addItemToBusinessSteps.wait_for_saving_alert();
		// addItemToBusinessSteps.wait_for_saving_edits_disappear();
		addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		addSpecialitsSteps.select_service_domain_location_for_specialist(businessLocation, domainName,
				serviceNameSecond);
		addSpecialitsSteps.click_on_save_staff_edits();
		addItemToBusinessSteps.wait_for_saving_alert();
		// addItemToBusinessSteps.wait_for_saving_edits_disappear();

		// create appointment with multiple services
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.select_location_calendar_tab(businessLocation);
		addAppointmentToBusinessStep.click_on_quick_appointment_button();

		addAppointmentToBusinessStep.fill_in_service_details_for_appointment(domainName,
				specialistName, serviceNameFirst, serviceDurationFirst);
		String appointmentDate = addAppointmentToBusinessStep
				.select_time_details_for_service_appointment_form(serviceNameFirst);
		String[] dateDetails = appointmentDate.split(" ");
		addAppointmentToBusinessStep.click_on_add_another_service_to_appointment();

		// create 2'nd service

		addAppointmentToBusinessStep.fill_in_service_details_for_appointment(domainName,
				specialistName, serviceNameSecond, serviceDurationSecond);
		String appointmentDate2 = addAppointmentToBusinessStep
				.select_time_details_for_service_appointment_form(serviceNameSecond,dateDetails[0],dateDetails[1],dateDetails[2] );
		// client card details and save
		
		addAppointmentToBusinessStep.fill_in_client_last_name(clientLastName);
		addAppointmentToBusinessStep.fill_in_client_first_name(clientFirstName);
		addAppointmentToBusinessStep.fill_in_client_email(clientEmail);
		addAppointmentToBusinessStep.fill_in_client_phone_number(clientPhoneNo);
		addAppointmentToBusinessStep.click_on_save_appointment();
		addAppointmentToBusinessStep.confirm_appointment_out_of_interval();

		// verify first service saved in calendar

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
		LocalDateTime date = LocalDateTime.parse(appointmentDate, formatter);
		
		
		System.out.println("first date" + date);
		LocalTime startHour = LocalTime.from(date);
		System.out.println("start time" + startHour);
		LocalDateTime endTime = date.plusMinutes(serviceDurationFirst);
		LocalTime endHour = LocalTime.from(endTime);
		System.out.println("end time" + endHour);
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.select_location_calendar_tab(businessLocation);
		addAppointmentToBusinessStep.click_on_day_view_in_calendar();
		addAppointmentToBusinessStep.click_on_mini_calendar();
		addAppointmentToBusinessStep.navigate_to_target_date_in_mini_calendar(appointmentDate);
		addAppointmentToBusinessStep.select_domain_calendar_left_menu(domainName);
		addAppointmentToBusinessStep.select_service_calendar_left_menu(domainName, serviceNameFirst);
		addAppointmentToBusinessStep
				.select_specialist_calendar_left_menu(ConfigUtils.capitalizeFirstLetter(specialistName));
		addAppointmentToBusinessStep.get_appointment_details_for(startHour.toString(), endHour.toString(),
				serviceNameFirst);
		// verify 2'nd service saved in calendar

		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
		LocalDateTime date2 = LocalDateTime.parse(appointmentDate2, formatter2);
		System.out.println("second date" + date2);
		LocalTime startHour2 = LocalTime.from(date2);
		System.out.println("start time 2" + startHour2);
		LocalDateTime endTime2 = date2.plusMinutes(serviceDurationSecond);
		LocalTime endHour2 = LocalTime.from(endTime2);
		System.out.println("end time 2" + endHour2);
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.select_location_calendar_tab(businessLocation);
		addAppointmentToBusinessStep.click_on_day_view_in_calendar();
		addAppointmentToBusinessStep.click_on_mini_calendar();
		addAppointmentToBusinessStep.navigate_to_target_date_in_mini_calendar(appointmentDate2);
		addAppointmentToBusinessStep.select_domain_calendar_left_menu(domainName);
		addAppointmentToBusinessStep.select_service_calendar_left_menu(domainName, serviceNameSecond);
		addAppointmentToBusinessStep
				.select_specialist_calendar_left_menu(ConfigUtils.capitalizeFirstLetter(specialistName));
		addAppointmentToBusinessStep.get_appointment_details_for(startHour2.toString(), endHour2.toString(),
				serviceNameSecond);
		addAppointmentToBusinessStep.assertAll();
	}
}