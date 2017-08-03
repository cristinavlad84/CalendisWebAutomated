package ro.evozon.features.business.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add new service packet  from business account", "As business user ",
		"I want to be able to add new service packet and then see packet is properly saved" })
@RunWith(SerenityRunner.class)
public class AddServicePacketStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, businessMainLocation, businessMainLocationCounty,
			businessMainLocationCity, packetName;

	public AddServicePacketStory() {
		super();
		this.packetName = FieldGenerators.generateRandomString(8, Mode.ALPHA);

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

	@Steps
	public LoginBusinessAccountSteps loginStep;

	@Steps
	public AddServiceToBusinessStep addNewPriceListSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;
	@Steps
	public AddItemToBusinessSteps addItemToBusinessSteps;

	@Issue("#CLD-041")
	@Test
	public void add_new_service_packet_then_verify_saved() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		// loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		// addlocationSteps.c
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addNewPriceListSteps.click_on_services_packet_tab();
		addNewPriceListSteps.click_on_add_price_list();
		// String price =
		// addNewPriceListSteps.getPriceListFor(priceListName).get().getServicePrice();
		// System.out.println(price);
		addNewPriceListSteps.fill_in_packet_name(packetName);
		
		List<Map<String, String>> pList = addNewPriceListSteps.fill_in_all_prices_in_new_price_list_form();
		addNewPriceListSteps.save_new_price_list();
		addItemToBusinessSteps.wait_for_saving_alert();
		addItemToBusinessSteps.refresh();
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addNewPriceListSteps.click_on_price_list_tab();
		addNewPriceListSteps.verify_price_list_displayed_in_list(ConfigUtils.capitalizeFirstLetterOnly(packetName));
		addNewPriceListSteps.click_on_modify_price_list(ConfigUtils.capitalizeFirstLetterOnly(packetName));
		List<Map<String, WebElement>> finalList = addNewPriceListSteps
				.get_prices_elements_for_services_from_price_list_form();
		List<Map<String, String>> finalStringList = addNewPriceListSteps
				.get_prices_values_as_strings_for_services_from_price_list_form(finalList);
		addNewPriceListSteps.compareListsOfPrices(pList, finalStringList);
		addNewPriceListSteps.assertAll();
	}
}