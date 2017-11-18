package ro.evozon.features.business.appointments;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;

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
import ro.evozon.tools.enums.PhonePrefixGenerators;
import ro.evozon.tools.enums.StaffType;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.business.AddAppointmentToBusinessStep;
import ro.evozon.steps.serenity.business.AddDomainToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add new service packet  from business account", "As business user ",
		"I want to be able to add new service packet and then see packet is properly saved" })
@RunWith(SerenityRunner.class)
public class CollectPaymentForServicePacketStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, businessMainLocation, businessMainLocationCounty,
			businessMainLocationCity, businessFirstService, packetName, domainName, serviceName, serviceNameSecond,
			maxPersonsPerPacket, specialistEmail, specialistName, specialistPhoneNo, clientLastName, clientFirstName,
			clientEmail, clientPhoneNo;
	int serviceDuration, editServiceDuration, serviceDurationSecond, breakDuration, breakDurationSecond;
	BigDecimal servicePrice, servicePriceSecond;
	int maxPersons = 1;

	public CollectPaymentForServicePacketStory() {
		super();
		this.packetName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.domainName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.serviceName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.serviceNameSecond = FieldGenerators.generateRandomString(8, Mode.ALPHA);
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
		this.serviceDurationSecond = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		this.editServiceDuration = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		this.breakDuration = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		this.breakDurationSecond = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		double servicePriceInterim = FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
				Constants.MAX_SERVICE_PRICE);
		BigDecimal dd = BigDecimal.valueOf(servicePriceInterim);
		this.servicePrice = dd.setScale(2, RoundingMode.HALF_UP);

		double servicePriceSecondInterim = FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
				Constants.MAX_SERVICE_PRICE);
		BigDecimal dd2 = BigDecimal.valueOf(servicePriceSecondInterim);
		this.servicePriceSecond = dd2.setScale(2, RoundingMode.HALF_UP);
		this.maxPersonsPerPacket = Integer.toString(FieldGenerators.getRandomIntegerBetween(1, 100));
		this.specialistEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.specialistName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.specialistPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
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
			businessFirstService = props.getProperty("businessFirstService", businessFirstService);
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
	public AddServiceToBusinessStep addServicePacketSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;
	@Steps
	public AddItemToBusinessSteps addItemToBusinessSteps;
	@Steps
	public AddServiceToBusinessStep addServiceStep;
	@Steps
	public AddDomainToBusinessStep addDomainSteps;
	@Steps
	NavigationStep navigationStep;
	@Steps
	public AddStaffToBusinessStep addSpecialitsSteps;
	@Steps
	public AddAppointmentToBusinessStep addAppointmentToBusinessStep;

	@Issue("#CLD-041")
	@Test
	public void collect_payment_for_service_packet() {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		// loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		// add new domain

		 addItemToBusinessSteps.click_on_domain_left_menu();
		 addDomainSteps.click_on_add_domain();
		 addDomainSteps.select_location_in_domain_form(businessMainLocation);
		 addDomainSteps.fill_in_domain_name(domainName);
		 addDomainSteps.click_on_save_domain_button();
		 addDomainSteps.verify_domain_name_appears_in_domain_section(domainName);
		
		 // add new service
		 addItemToBusinessSteps.click_on_sevice_left_menu();
		 addServiceStep.click_on_add_service();
		 addServiceStep.fill_in_service_name(serviceName);
		 addServiceStep.fill_in_service_price(servicePrice.toString());
		 addServiceStep.select_domain_to_add_service(domainName);
		 addServiceStep.fill_in_service_duration_per_service(Integer.toString(serviceDuration));
		 addServiceStep.fill_in_max_persons_per_service(Integer.toString(maxPersons));
		 addServiceStep.click_on_save_service_button();
		 navigationStep.refresh();
		
		 WebElement serviceEl = addServiceStep
		 .get_service_webelement_in_list(the("Servicii individuale",
		 containsString(serviceName)));
		 addServiceStep.verify_service_name_is_displayed_in_service_section(serviceName);
		
		 addServiceStep.verify_service_details_appears_in_service_section(serviceEl,
		 servicePrice.toString(),
		 Integer.toString(serviceDuration), Integer.toString(maxPersons));
		 // create 2'nd service
		 addItemToBusinessSteps.click_on_sevice_left_menu();
		 addServiceStep.click_on_add_service();
		 addServiceStep.fill_in_service_name(serviceNameSecond);
		 addServiceStep.fill_in_service_price(servicePriceSecond.toString());
		 addServiceStep.select_domain_to_add_service(domainName);
		 addServiceStep.fill_in_service_duration_per_service(Integer.toString(serviceDurationSecond));
		 addServiceStep.fill_in_max_persons_per_service(Integer.toString(maxPersons));
		 addServiceStep.click_on_save_service_button();
		 navigationStep.refresh();
		 WebElement serviceEl2 = addServiceStep
		 .get_service_webelement_in_list(the("Servicii individuale",
		 containsString(serviceNameSecond)));
		 addServiceStep.verify_service_name_is_displayed_in_service_section(serviceNameSecond);
		
		 addServiceStep.verify_service_details_appears_in_service_section(serviceEl2,
		 servicePriceSecond.toString(),
		 Integer.toString(serviceDurationSecond),
		 Integer.toString(maxPersons));
		 // add new specialist
		 addSpecialitsSteps.click_on_add_new_staff_button();
		 addSpecialitsSteps.fill_in_staff_name(specialistName);
		 addSpecialitsSteps.fill_in_staff_email(specialistEmail);
		 addSpecialitsSteps.fill_in_staff_phone(specialistPhoneNo);
		 addSpecialitsSteps.select_staff_type_to_add(StaffType.EMPL.toString());
		 addSpecialitsSteps.check_default_service_for_staff(serviceName);
		addSpecialitsSteps.check_default_service_for_staff(serviceNameSecond);
		addSpecialitsSteps.click_on_set_staff_schedule();
		 for (int i = 0; i < Constants.NO_OF_WEEK_DAYS; i++) {
				System.out.println("i = " + i);

				addSpecialitsSteps.fill_in_schedule_form_for_staff(Constants.RANGE_HOURS, i);

			}
		 addSpecialitsSteps.select_day_of_week_for_staff_schedule();
		
		 addSpecialitsSteps.click_on_save_staff_schedule();
		 navigationStep.refresh();
		 addSpecialitsSteps.is_staff_name_displayed_in_personal_section(specialistName);
		 // System.out.println("location name " + businessMainLocation);
		 // System.out.println("domain name " + domainName);
		 // System.out.println("service name " + serviceName);
		 // System.out.println("service name second " + serviceNameSecond);
		 // System.out.println("specialistName name " + specialistName);

		// assign services to specialist
		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(specialistName);
		addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		addSpecialitsSteps.select_service_domain_location_for_specialist(businessMainLocation, domainName, serviceName);
		addSpecialitsSteps.click_on_save_staff_edits();
		//navigationStep.refresh();
		addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		addSpecialitsSteps.select_service_domain_location_for_specialist(businessMainLocation, domainName,
				serviceNameSecond);
		addSpecialitsSteps.click_on_save_staff_edits();
		navigationStep.refresh();
		// add new service packet
		 addItemToBusinessSteps.click_on_sevice_left_menu();
		 addServicePacketSteps.click_on_services_packet_tab();
		 addServicePacketSteps.click_on_add_services_packet();
		
		 addServicePacketSteps.fill_in_packet_name(packetName);
		 addServicePacketSteps.select_location_for_packet(businessMainLocation);
		 addServicePacketSteps.select_service_for_packet(0, serviceName);
		
		 addServiceStep.fill_in_break_duration_per_service_packet(0,
		 Integer.toString(breakDuration));
		
		 addServiceStep.click_on_add_more_services_to_packet();
		 addServicePacketSteps.select_service_for_packet(1,
		 serviceNameSecond);
		
		 addServiceStep.fill_in_break_duration_per_service_packet(1,
		 Integer.toString(breakDurationSecond));
		 //
		 addServiceStep.fill_in_max_persons_per_packet(maxPersonsPerPacket);
		 addServicePacketSteps.click_on_save_service_packet_button();
		 // addItemToBusinessSteps.wait_for_saving_alert();
		 addItemToBusinessSteps.refresh();
		 addItemToBusinessSteps.click_on_sevice_left_menu();
		 addServicePacketSteps.click_on_services_packet_tab();
		 addServicePacketSteps.verify_packet_displayed_in_list(packetName);
		 addServicePacketSteps.click_on_modify_packet(packetName);
		 addServicePacketSteps.verify_packet_name_saved(packetName);
		 // addServicePacketSteps.verify_packet_max_persons(maxPersons);
		 addServicePacketSteps.verify_location_for_packet_name_saved(businessMainLocation);
		 addServicePacketSteps.verify_service_for_packet_saved(0,
		 serviceName);
		 addServicePacketSteps.verify_service_for_packet_saved(1,
		 serviceNameSecond);
		 addServicePacketSteps.verify_service_duration_for_packet_saved(0,
		 serviceDuration);
		 addServicePacketSteps.verify_service_duration_for_packet_saved(1,
		 serviceDurationSecond);
		 addServicePacketSteps.verify_service_break_for_packet_saved(0,
		 breakDuration);
		 addServicePacketSteps.verify_service_break_for_packet_saved(1,
		 breakDurationSecond);
		 addServicePacketSteps.verify_service_price_for_packet_saved(0,
		 servicePrice);
		 addServicePacketSteps.verify_service_price_for_packet_saved(1,
		 servicePriceSecond);
		 addServicePacketSteps
		 .verify_packet_duration(serviceDuration + serviceDurationSecond +
		 breakDuration + breakDurationSecond);
		 addServicePacketSteps.verify_packet_price(servicePrice.add(servicePriceSecond));
		 // assign packet to specialist
		// serviceDuration = 40;
		// specialistName = "Phoomp";
		// packetName = "Qwaeqkpl";
		// serviceName = "Ddvavwbt";
		 addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		 addSpecialitsSteps.check_service_pachet_for_specialist(packetName);
		 addSpecialitsSteps.click_on_save_staff_edits();
		 navigationStep.click_on_calendar_tab();
		 addAppointmentToBusinessStep.select_location_calendar_tab(businessMainLocation);
		 addAppointmentToBusinessStep.click_on_quick_appointment_button();
		 // fill in service card details with first service created for	 business
		 addAppointmentToBusinessStep.select_domain_for_appointment(domainName);
		 addAppointmentToBusinessStep.select_specialist_for_appointment(specialistName);
		 addAppointmentToBusinessStep.select_service_for_appointment(packetName);
		 //
		 addAppointmentToBusinessStep.fill_in_duration_for_service_appointment(Integer.toString(serviceDuration));
		 String appointmentDate = addAppointmentToBusinessStep
		 .select_time_details_for_service_appointment_form(packetName,Constants.HOUR_MIN_LIMIT, Constants.HOUR_MAX_LIMIT);
		 addAppointmentToBusinessStep.fill_in_client_details_card_appointment_form(clientLastName,
		 clientFirstName,
		 clientEmail, clientPhoneNo);
		 addAppointmentToBusinessStep.click_on_save_appointment();
		 addAppointmentToBusinessStep.confirm_appointment_out_of_interval();
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
		 LocalDateTime date = LocalDateTime.parse(appointmentDate, formatter);
		 System.out.println(date);
		 LocalTime startHour = LocalTime.from(date);
		 System.out.println("start time" + startHour);
		 LocalTime endHour = startHour.plusMinutes(serviceDuration);
		 System.out.println("end time" + endHour);
		 navigationStep.click_on_calendar_tab();
		 addAppointmentToBusinessStep.select_location_calendar_tab(businessMainLocation);
		 addAppointmentToBusinessStep.click_on_day_view_in_calendar();
		 addAppointmentToBusinessStep.click_on_mini_calendar();
		 addAppointmentToBusinessStep.navigate_to_target_date_in_mini_calendar(appointmentDate);
		 //
		 addAppointmentToBusinessStep.select_domain_calendar_left_menu(domainName);
		// servicePrice = new BigDecimal(407.11);
		// servicePriceSecond = new BigDecimal(224.68);
		// servicePrice = servicePrice.setScale(2, RoundingMode.HALF_UP);
		// //// servicePriceSecond = new BigDecimal(100);
		// servicePriceSecond = servicePriceSecond.setScale(2, RoundingMode.HALF_UP);
		 addAppointmentToBusinessStep.select_service_packet_calendar_left_menu(packetName);
		 addAppointmentToBusinessStep.select_specialist_calendar_left_menu(specialistName);
		
		 addAppointmentToBusinessStep.get_appointment_details_for(startHour.toString(),
		 endHour.toString(), packetName);
		 addAppointmentToBusinessStep.click_on_appointment_on_calendar(startHour.toString(),
		 endHour.toString(),
		 packetName);
		 addAppointmentToBusinessStep.click_on_collect_payment_appointment_form();
		 addAppointmentToBusinessStep.click_on_collect_button_on_client_card();
		
		 addAppointmentToBusinessStep.verify_total_price_on_appointment_form(servicePrice.add(servicePriceSecond));
		 addAppointmentToBusinessStep.click_on_finalize_button();
		 addAppointmentToBusinessStep.get_appointment_details_for(startHour.toString(),
		 endHour.toString(), packetName);
		 addAppointmentToBusinessStep.click_on_appointment_on_calendar(startHour.toString(),
		 endHour.toString(),
		 packetName);
		 addAppointmentToBusinessStep.click_on_payment_history();
		 String foundPrice =
		 addAppointmentToBusinessStep.get_last_payment_in_payment_history();
		 addAppointmentToBusinessStep.verify_last_payment_in_payment_history(foundPrice,
		 (servicePrice.add(servicePriceSecond)).toString());

		addServicePacketSteps.assertAll();
	}
}