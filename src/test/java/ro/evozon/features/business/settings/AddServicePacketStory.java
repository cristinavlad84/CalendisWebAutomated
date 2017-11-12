package ro.evozon.features.business.settings;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.openqa.selenium.WebElement;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.business.AddDomainToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add new service packet  from business account", "As business user ",
		"I want to be able to add new service packet and then see packet is properly saved" })
@RunWith(SerenityRunner.class)
public class AddServicePacketStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, businessMainLocation, businessMainLocationCounty,
			businessMainLocationCity, businessFirstService, packetName, domainName, serviceName, serviceNameSecond,
			maxPersonsPerPacket, editmMaxPersonsPerPacket, maxPersons;
	int serviceDuration, editServiceDuration, serviceDurationSecond, editServiceDurationSecond, breakDuration,
			editBreakDuration, breakDurationSecond, editBreakDurationSecond;
	BigDecimal servicePrice, editServicePrice, servicePriceSecond, editServicePriceSecond;

	public AddServicePacketStory() {
		super();
		this.packetName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.serviceName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.domainName =  FieldGenerators.generateRandomString(8, Mode.ALPHA);
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
		this.editServiceDurationSecond = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		this.breakDuration = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		this.editBreakDuration = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		this.breakDurationSecond = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		this.editBreakDurationSecond = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
		double servicePriceInterim = FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
				Constants.MAX_SERVICE_PRICE);
		BigDecimal dd = BigDecimal.valueOf(servicePriceInterim);
		this.servicePrice = dd.setScale(2, RoundingMode.HALF_UP);
		double editServicePriceInterim = FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
				Constants.MAX_SERVICE_PRICE);
		BigDecimal dd4 = BigDecimal.valueOf(editServicePriceInterim);
		this.editServicePrice = dd4.setScale(2, RoundingMode.HALF_UP);
		double editServicePriceSecondInterim = FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
				Constants.MAX_SERVICE_PRICE);
		BigDecimal dd3 = BigDecimal.valueOf(editServicePriceSecondInterim);
		this.editServicePriceSecond = dd3.setScale(2, RoundingMode.HALF_UP);
		double servicePriceSecondInterim = FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
				Constants.MAX_SERVICE_PRICE);
		BigDecimal dd2 = BigDecimal.valueOf(servicePriceSecondInterim);
		this.servicePriceSecond = dd2.setScale(2, RoundingMode.HALF_UP);
		this.maxPersons = Integer.toString(FieldGenerators.getRandomIntegerBetween(1, 100));
		this.maxPersonsPerPacket = Integer.toString(FieldGenerators.getRandomIntegerBetween(1, 100));
		this.editmMaxPersonsPerPacket = Integer.toString(FieldGenerators.getRandomIntegerBetween(1, 100));
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
	public AddServiceToBusinessStep addNewPacketSteps;
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

	@Issue("#CLD-041")
	@Test
	public void add_new_service_packet_then_verify_saved() throws Exception {
		packetName = ConfigUtils.capitalizeFirstLetter(packetName);
		serviceName = ConfigUtils.capitalizeFirstLetter(serviceName);
		serviceNameSecond = ConfigUtils.capitalizeFirstLetter(serviceNameSecond);
		domainName = ConfigUtils.capitalizeFirstLetter(domainName);
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
		addServiceStep.fill_in_max_persons_per_service(maxPersons);
		addServiceStep.click_on_save_service_button();
		navigationStep.refresh();

		WebElement serviceEl = addServiceStep
				.get_service_webelement_in_list(the("Servicii individuale", containsString(serviceName)));
		addServiceStep.verify_service_name_is_displayed_in_service_section(serviceName);

		addServiceStep.verify_service_details_appears_in_service_section(serviceEl, servicePrice.toString(),
				Integer.toString(serviceDuration), maxPersons);
		// create 2'nd service
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addServiceStep.click_on_add_service();
		addServiceStep.fill_in_service_name(serviceNameSecond);
		addServiceStep.fill_in_service_price(servicePriceSecond.toString());
		addServiceStep.select_domain_to_add_service(domainName);
		addServiceStep.fill_in_service_duration_per_service(Integer.toString(serviceDurationSecond));
		addServiceStep.fill_in_max_persons_per_service(maxPersons);
		addServiceStep.click_on_save_service_button();
		navigationStep.refresh();
		WebElement serviceEl2 = addServiceStep
				.get_service_webelement_in_list(the("Servicii individuale", containsString(serviceNameSecond)));
		addServiceStep.verify_service_name_is_displayed_in_service_section(serviceNameSecond);

		addServiceStep.verify_service_details_appears_in_service_section(serviceEl2, servicePriceSecond.toString(),
				Integer.toString(serviceDurationSecond), maxPersons);
		// addlocationSteps.c
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addNewPacketSteps.click_on_services_packet_tab();
		addNewPacketSteps.click_on_add_services_packet();

		addNewPacketSteps.fill_in_packet_name(packetName);
		addNewPacketSteps.select_location_for_packet(businessMainLocation);
		addNewPacketSteps.select_service_for_packet(0, serviceName);

		addServiceStep.fill_in_break_duration_per_service_packet(0, Integer.toString(breakDuration));

		addServiceStep.click_on_add_more_services_to_packet();
		addNewPacketSteps.select_service_for_packet(1, serviceNameSecond);

		addServiceStep.fill_in_break_duration_per_service_packet(1, Integer.toString(breakDurationSecond));
		addServiceStep.fill_in_max_persons_per_packet(maxPersonsPerPacket);
		addNewPacketSteps.click_on_save_service_packet_button();
		// addItemToBusinessSteps.wait_for_saving_alert();
		addItemToBusinessSteps.refresh();
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addNewPacketSteps.click_on_services_packet_tab();
		addNewPacketSteps.verify_packet_displayed_in_list(packetName);
		addNewPacketSteps.click_on_modify_packet(packetName);
		addNewPacketSteps.verify_packet_name_saved(packetName);
		addNewPacketSteps.verify_packet_max_persons(Integer.parseInt(maxPersons));
		addNewPacketSteps.verify_location_for_packet_name_saved(businessMainLocation);
		addNewPacketSteps.verify_service_for_packet_saved(0, serviceName);
		addNewPacketSteps.verify_service_for_packet_saved(1, serviceNameSecond);
		addNewPacketSteps.verify_service_duration_for_packet_saved(0, serviceDuration);
		addNewPacketSteps.verify_service_duration_for_packet_saved(1, serviceDurationSecond);
		addNewPacketSteps.verify_service_break_for_packet_saved(0, breakDuration);
		addNewPacketSteps.verify_service_break_for_packet_saved(1, breakDurationSecond);
		addNewPacketSteps.verify_service_price_for_packet_saved(0, servicePrice);
		addNewPacketSteps.verify_service_price_for_packet_saved(1, servicePriceSecond);
		addNewPacketSteps
				.verify_packet_duration(serviceDuration + serviceDurationSecond + breakDuration + breakDurationSecond);
		addNewPacketSteps.verify_packet_price(servicePrice.add(servicePriceSecond));

		addServiceStep.fill_in_service_duration_per_packet(0, Integer.toString(editServiceDuration));
		addServiceStep.fill_in_break_duration_per_service_packet(0, Integer.toString(editBreakDuration));
		addServiceStep.fill_in_service_price_in_packet(0, editServicePrice.toString());

		addServiceStep.fill_in_service_duration_per_packet(1, Integer.toString(editServiceDurationSecond));
		addServiceStep.fill_in_break_duration_per_service_packet(1, Integer.toString(editBreakDurationSecond));
		addServiceStep.fill_in_service_price_in_packet(1, editServicePriceSecond.toString());
		addServiceStep.fill_in_max_persons_per_packet(editmMaxPersonsPerPacket);

		addNewPacketSteps.click_on_save_service_packet_button();

		// edit service packet
		addItemToBusinessSteps.refresh();
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addNewPacketSteps.click_on_services_packet_tab();
		addNewPacketSteps.verify_packet_displayed_in_list(packetName);
		addNewPacketSteps.click_on_modify_packet(packetName);
		addNewPacketSteps.verify_service_duration_for_packet_saved(0, editServiceDuration);
		addNewPacketSteps.verify_service_duration_for_packet_saved(1, editServiceDurationSecond);
		addNewPacketSteps.verify_service_break_for_packet_saved(0, editBreakDuration);
		addNewPacketSteps.verify_service_break_for_packet_saved(1, editBreakDurationSecond);
		addNewPacketSteps.verify_service_price_for_packet_saved(0, editServicePrice);
		addNewPacketSteps.verify_service_price_for_packet_saved(1, editServicePriceSecond);
		addNewPacketSteps.verify_packet_duration(
				editServiceDuration + editServiceDurationSecond + editBreakDuration + editBreakDurationSecond);
		addNewPacketSteps.verify_packet_price(editServicePrice.add(editServicePriceSecond));
		addNewPacketSteps.verify_packet_max_persons(Integer.parseInt(editmMaxPersonsPerPacket));
		// delete service packet
	/*	addItemToBusinessSteps.click_on_sevice_left_menu();
		addNewPacketSteps.click_on_services_packet_tab();
		addNewPacketSteps.click_on_delete_packet(packetName);
		addNewPacketSteps.confirm_packet_deletion();*/
		addNewPacketSteps.verify_packet_is_not_displayed_in_list(packetName);
		addNewPacketSteps.assertAll();
	}
}