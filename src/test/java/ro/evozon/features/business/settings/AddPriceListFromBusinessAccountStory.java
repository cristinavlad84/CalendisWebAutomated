package ro.evozon.features.business.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add new service price to business account", "As business user ",
		"I want to be able to add new service price and then see service is properly saved" })
@RunWith(SerenityRunner.class)
public class AddPriceListFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, businessMainLocation, businessMainLocationCounty,
			businessMainLocationCity, servicePrice, newServicePrice, priceListName;

	public AddPriceListFromBusinessAccountStory() {
		super();
		this.priceListName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.servicePrice = new DecimalFormat("#.00").format(
				FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Constants.MAX_SERVICE_PRICE));
		this.newServicePrice = new DecimalFormat("#.00").format(
				FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Constants.MAX_SERVICE_PRICE));

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

	@Issue("#CLD-042")
	@Test
	public void add_new_price_list_then_verify_saved() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		// loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		// addlocationSteps.c
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addNewPriceListSteps.click_on_price_list_tab();
		addNewPriceListSteps.click_on_add_price_list();
		String price = addNewPriceListSteps.getPriceListFor("Sdfsd").get().getServicePrice();
		System.out.println(price);
		addNewPriceListSteps.fill_in_price_list_name(priceListName);
		List<String> pList = new ArrayList<String>(addNewPriceListSteps.fill_in_all_prices_in_new_price_list_form());
		addNewPriceListSteps.save_new_price_list();
		addItemToBusinessSteps.wait_for_saving_alert();
		addNewPriceListSteps.click_on_modify_price_list(priceListName);
		List<String> pricesSaved = addNewPriceListSteps.get_saved_prices_list();
		addItemToBusinessSteps.prices_lists_should_be_equal(pList, pricesSaved);

		addNewPriceListSteps.assertAll();
	}
}