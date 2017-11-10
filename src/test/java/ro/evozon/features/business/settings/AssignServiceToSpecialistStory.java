package ro.evozon.features.business.settings;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
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
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.business.AddDomainToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to login to business account as specialist", "As business user ",
		"I want to be able to assign service for specialist " })
@RunWith(SerenityRunner.class)
public class AssignServiceToSpecialistStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, locationName, domainAssociatedLocationName,
			specialistName, serviceName, domainName, servicePrice;
	int serviceDuration;
	final String maxPersons = "1";

	public AssignServiceToSpecialistStory() {
		super();
		this.domainName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.serviceName = FieldGenerators.generateRandomString(8, Mode.ALPHA);

		this.servicePrice = new DecimalFormat("#.00").format(
				FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Constants.MAX_SERVICE_PRICE));
		this.serviceDuration = FieldGenerators.getRandomIntegerBetween(3, 12) * 5;
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
			locationName = props.getProperty("businessMainLocation", locationName);
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
	public AddStaffToBusinessStep addSpecialitsSteps;
	@Steps
	public StaffSteps staffSteps;
	@Steps
	public NavigationStep navigationStep;
	@Steps
	public AddDomainToBusinessStep addDomainSteps;
	@Steps
	public AddItemToBusinessSteps addItemToBusinessSteps;
	@Steps
	public AddServiceToBusinessStep addServiceStep;

	@Issue("")
	@Test
	public void assign_service_to_specialist() throws Exception {
		domainName = ConfigUtils.capitalizeFirstLetter(domainName);
		locationName = ConfigUtils.capitalizeFirstLetter(locationName);
		serviceName = ConfigUtils.capitalizeFirstLetter(serviceName);
		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed
		navigationStep.acceptCookies();
		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		// add new domain
		// add new domain
		addItemToBusinessSteps.click_on_domain_left_menu();
		addDomainSteps.click_on_add_domain();
		addDomainSteps.select_location_in_domain_form(locationName);
		addDomainSteps.fill_in_domain_name(domainName);
		addDomainSteps.click_on_save_domain_button();
		addDomainSteps.verify_domain_name_appears_in_domain_section(domainName);
		// end add new domain
		// add new service
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addServiceStep.click_on_add_service();
		addServiceStep.fill_in_service_name(serviceName);
		addServiceStep.select_domain_to_add_service(domainName);
		addServiceStep.fill_in_service_price(servicePrice);

		addServiceStep.fill_in_service_duration_per_service(Integer.toString(serviceDuration));
		addServiceStep.fill_in_max_persons_per_service(maxPersons);
		addServiceStep.click_on_save_service_button();
		navigationStep.refresh();
		WebElement serviceElFirst = addServiceStep
				.get_service_webelement_in_list(the("Servicii individuale", containsString(serviceName)));
		addServiceStep.verify_service_name_not_displayed_in_service_section(serviceName);
		addServiceStep.verify_service_details_appears_in_service_section(serviceElFirst, servicePrice,
				Integer.toString(serviceDuration), maxPersons);
		// end add new service

		// assign newly created services to specialist
		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(specialistName);
		addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		addSpecialitsSteps.select_service_domain_location_for_specialist(locationName, domainName, serviceName);
		addSpecialitsSteps.click_on_save_staff_edits();
		// addItemToBusinessSteps.wait_for_saving_alert();
		// addItemToBusinessSteps.wait_for_saving_edits_disappear();
		addSpecialitsSteps.click_on_modify_staff_link(specialistName);
		addSpecialitsSteps.select_service_domain_location_for_specialist(locationName, domainName, serviceName);
		// addItemToBusinessSteps.wait_for_saving_alert();
		loginStep.assertAll();
	}

}