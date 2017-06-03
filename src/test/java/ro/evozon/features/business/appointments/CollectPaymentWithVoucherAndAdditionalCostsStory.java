package ro.evozon.features.business.appointments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
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
import ro.evozon.tools.StaffType;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.business.AddAppointmentToBusinessStep;
import ro.evozon.steps.serenity.business.AddDomainToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddLocationToBusinessStep;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.AddVoucherToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.tests.BaseTest;

@Narrative(text = {
		"In order to collect payment with vocuher code and additional cost for existing appointment from business account",
		"As business user ",
		"I want to be able to collect payment with voucher code discount and and additional cost for existing appointement and then see payment is made properly" })
@RunWith(SerenityRunner.class)
public class CollectPaymentWithVoucherAndAdditionalCostsStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, clientLastName, clientFirstName, clientEmail,
			clientPhoneNo, serviceName, domainName, servicePrice, maxPersons, locationName, locationStreet,
			locationPhone, specialistEmail, specialistName, specialistPhoneNo, voucherName;

	int serviceDuration;
	BigDecimal price, discountValue, additionalCost;
	int percentage;

	public CollectPaymentWithVoucherAndAdditionalCostsStory() {
		super();
		this.clientLastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientFirstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.clientEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.clientPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		this.locationStreet = FieldGenerators.generateRandomString(6, Mode.ALPHA)
				.concat(FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		this.domainName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.locationName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.locationPhone = PhonePrefixGenerators.generatePhoneNumber();
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
		this.serviceName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.maxPersons = Integer.toString(FieldGenerators.getRandomIntegerBetween(1, 100));
		double price_interim = FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
				Constants.MAX_SERVICE_PRICE);
		double addCost = FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, price_interim);
		BigDecimal dd = BigDecimal.valueOf(price_interim);
		BigDecimal df = BigDecimal.valueOf(addCost);
		this.price = dd.setScale(2, RoundingMode.HALF_UP);
		// this.servicePrice = this.price.toString();
		this.additionalCost = df.setScale(2, RoundingMode.HALF_UP);
		this.percentage = FieldGenerators.getRandomIntegerBetween(1, 99);
		this.specialistEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
		this.specialistName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.specialistPhoneNo = PhonePrefixGenerators.generatePhoneNumber();
		this.voucherName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
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
	public AddItemToBusinessSteps addItemToBusinessSteps;
	@Steps
	public AddAppointmentToBusinessStep addAppointmentToBusinessStep;
	@Steps
	NavigationStep navigationStep;
	@Steps
	public AddLocationToBusinessStep addLocationToBusinessSteps;
	@Steps
	public AddServiceToBusinessStep addServiceStep;
	@Steps
	public AddDomainToBusinessStep addDomainSteps;
	@Steps
	public AddStaffToBusinessStep addSpecialitsSteps;
	@Steps
	public AddVoucherToBusinessStep addVoucherToBusinessStep;

	@Issue("#CLD-")
	@Test
	public void collect_payment_with_voucher_code_and_additional_cost() {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed
		// use first added service
		loginStep.logout_link_should_be_displayed();
		loginStep.dismiss_any_popup_if_appears();
		navigationStep.acceptCookies();
		// addlocationSteps.c
		loginStep.click_on_settings();
		// create test data: new location, new domain , new service, new
		// specialist, assign service to specialist , add new voucher code

		addItemToBusinessSteps.click_on_location_left_menu();
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
		navigationStep.refresh();
		System.out.println("location Name " + locationName);
		// add new domain

		addItemToBusinessSteps.click_on_domain_left_menu();
		addDomainSteps.click_on_add_domain();
		addDomainSteps.select_location_in_domain_form(locationName);
		addDomainSteps.fill_in_domain_name(domainName);
		addDomainSteps.click_on_save_domain_button();
		addDomainSteps.verify_domain_name_appears_in_domain_section(domainName);

		// add new service
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addServiceStep.click_on_add_service();
		addServiceStep.fill_in_service_name(serviceName);
		addServiceStep.fill_in_service_price(price.toString());
		addServiceStep.select_domain_to_add_service(domainName);
		addServiceStep.fill_in_service_duration(Integer.toString(serviceDuration));
		addServiceStep.fill_in_max_persons_per_service(maxPersons);
		addServiceStep.click_on_save_service_button();
		navigationStep.refresh();
		addServiceStep.verify_service_name_appears_in_service_section(serviceName);
		addServiceStep.verify_service_details_appears_in_service_section(serviceName, price.toString(),
				Integer.toString(serviceDuration), maxPersons);

		// add new specialist
		addSpecialitsSteps.click_on_add_new_staff_button();
		addSpecialitsSteps.fill_in_staff_name(specialistName);
		addSpecialitsSteps.fill_in_staff_email(specialistEmail);
		addSpecialitsSteps.fill_in_staff_phone(specialistPhoneNo);
		addSpecialitsSteps.select_staff_type_to_add(StaffType.EMPL.toString());
		addSpecialitsSteps.check_default_location_for_staff();

		addSpecialitsSteps.click_on_set_staff_schedule();
		addSpecialitsSteps.select_day_of_week_for_staff_schedule();

		addSpecialitsSteps.click_on_save_staff_schedule();
		navigationStep.refresh();
		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(specialistName);

		System.out.println("domain name " + domainName);
		System.out.println("service name " + serviceName);
		// assign service to specialist
		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(specialistName);
		addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		addSpecialitsSteps.select_service_domain_location_for_specialist(locationName, domainName, serviceName);
		addSpecialitsSteps.click_on_save_staff_edits();

		// add new voucher code
		addItemToBusinessSteps.click_on_voucher_codes_left_menu();
		addVoucherToBusinessStep.click_on_add_voucher();
		addVoucherToBusinessStep.fill_in_voucher_name(voucherName);
		addVoucherToBusinessStep.select_location_for_voucher(locationName);
		addVoucherToBusinessStep.click_on_save_voucher_button();
		navigationStep.refresh();
		addVoucherToBusinessStep.verify_voucher_name_appears_in_domain_section(voucherName);

		// end create test data

		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.select_location_calendar_tab(locationName);
		addAppointmentToBusinessStep.click_on_quick_appointment_button();
		// fill in service card details with first service created for business
		addAppointmentToBusinessStep.select_domain_for_appointment(domainName);
		addAppointmentToBusinessStep.select_specialist_for_appointment(specialistName);
		addAppointmentToBusinessStep.select_service_for_appointment(serviceName);
		addAppointmentToBusinessStep.fill_in_duration_for_service_appointment(Integer.toString(serviceDuration));
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
		LocalDateTime endTime = date.plusMinutes(serviceDuration);
		LocalTime endHour = LocalTime.from(endTime);
		System.out.println("end time" + endHour);
		navigationStep.click_on_calendar_tab();
		addAppointmentToBusinessStep.select_location_calendar_tab(locationName);
		addAppointmentToBusinessStep.click_on_day_view_in_calendar();
		addAppointmentToBusinessStep.click_on_mini_calendar();
		addAppointmentToBusinessStep.navigate_to_target_date_in_mini_calendar(appointmentDate);
		addAppointmentToBusinessStep.select_domain_calendar_left_menu(domainName);
		addAppointmentToBusinessStep.select_service_calendar_left_menu(domainName, serviceName);
		addAppointmentToBusinessStep.select_specialist_calendar_left_menu(specialistName);
		addAppointmentToBusinessStep.get_appointment_details_for(startHour.toString(), endHour.toString(), serviceName);
		addAppointmentToBusinessStep.click_on_appointment_on_calendar(startHour.toString(), endHour.toString(),
				serviceName);
		addAppointmentToBusinessStep.click_on_collect_payment_appointment_form();
		WebElementFacade servicePaymentContainer = addAppointmentToBusinessStep
				.get_service_payment_form_container(serviceName);
		addAppointmentToBusinessStep.select_voucher_code_appointment_form(servicePaymentContainer,voucherName);

		discountValue = addAppointmentToBusinessStep.calculateDiscountValue(price, percentage);
		System.out.println("Discount value " + discountValue + " percentage " + percentage + "price" + price
				+ "other costs " + additionalCost);
		addAppointmentToBusinessStep.fill_in_discount_value_for_voucher_payment_form(servicePaymentContainer,discountValue.toString());
		addAppointmentToBusinessStep.fill_in_additional_cost_payment_form(servicePaymentContainer,additionalCost.toString());
		BigDecimal amountToPayForService = addAppointmentToBusinessStep.get_amount_to_pay_for_service(servicePaymentContainer);
		BigDecimal dd = addAppointmentToBusinessStep.get_price_with_discount_and_other_costs(price, discountValue,
				additionalCost);// aici am ramas
		addAppointmentToBusinessStep.verify_amount_to_pay_for_service(dd, amountToPayForService);
		BigDecimal grandTotal = addAppointmentToBusinessStep.get_total_amount_for_all_services();

		addAppointmentToBusinessStep.verify_total_amount_to_pay_for_all_services(dd, grandTotal);
		addAppointmentToBusinessStep.click_on_collect_payment_appointment_form();
		BigDecimal amountLeftToPay = addAppointmentToBusinessStep.get_amount_left_to_pay_for_all_services();
		addAppointmentToBusinessStep.verify_amount_left_to_pay_for_single_service(dd, amountLeftToPay);
		BigDecimal payment_paid_value = addAppointmentToBusinessStep.get_payment_paid_in_fieldbox();
		addAppointmentToBusinessStep.verify_payment_paid_value(amountLeftToPay, payment_paid_value);
		addAppointmentToBusinessStep.click_on_collect_button_on_client_card();
		addAppointmentToBusinessStep.verify_total_price_on_appointment_form(addAppointmentToBusinessStep
				.get_price_with_discount_and_other_costs(price, discountValue, additionalCost));
		addAppointmentToBusinessStep.click_on_payment_history();
		String foundPrice = addAppointmentToBusinessStep.get_last_payment_in_payment_history();
		addAppointmentToBusinessStep.verify_last_payment_in_payment_history(foundPrice, payment_paid_value.toString());

		addAppointmentToBusinessStep.assertAll();
	}
}