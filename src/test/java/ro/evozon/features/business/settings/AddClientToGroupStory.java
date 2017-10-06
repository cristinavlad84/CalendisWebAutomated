package ro.evozon.features.business.settings;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
import ro.evozon.steps.serenity.business.AddGroupToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddLocationToBusinessStep;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.AddVoucherToBusinessStep;
import ro.evozon.steps.serenity.business.ClientsStep;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add existing client to group", "As business user ",
		"I want to be able to add existing client to  group then see client is added" })
@RunWith(SerenityRunner.class)
public class AddClientToGroupStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, lastName, firstName, priceListName, groupName;
	BigDecimal discount;
	String listName = "Standard";

	public AddClientToGroupStory() {
		super();

		this.lastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.firstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.priceListName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.groupName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
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
	public AddServiceToBusinessStep addServiceStep;
	@Steps
	NavigationStep navigationStep;
	@Steps
	public AddLocationToBusinessStep addLocationToBusinessSteps;
	@Steps
	public AddDomainToBusinessStep addDomainSteps;
	@Steps
	public AddStaffToBusinessStep addSpecialitsSteps;
	@Steps
	public AddVoucherToBusinessStep addVoucherToBusinessStep;
	@Steps
	public AddServiceToBusinessStep addNewPriceListSteps;
	@Steps
	public ClientsStep clientsStep;
	@Steps
	public AddGroupToBusinessStep addGroupStep;

	@Issue("#CLD-")
	@Test
	public void add_client_to_group_then_verify_saved() {
		priceListName = ConfigUtils.capitalizeFirstLetterOnly(priceListName);
		groupName = ConfigUtils.capitalizeFirstLetter(groupName);
		lastName=ConfigUtils.capitalizeFirstLetter(lastName);
		firstName=ConfigUtils.capitalizeFirstLetter(firstName);
		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		loginStep.logout_link_should_be_displayed();
		navigationStep.acceptCookies();
		// user should be logged in --> Deconecteaza-te should be displayed
//		// create special price list
//		// add new price list
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		// addlocationSteps.c
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addNewPriceListSteps.click_on_price_list_tab();
		addNewPriceListSteps.click_on_add_price_list();
		// String price =
		// addNewPriceListSteps.getPriceListFor(priceListName).get().getServicePrice();
		// System.out.println(price);
		addNewPriceListSteps.fill_in_price_list_name(priceListName);
		addNewPriceListSteps.fill_in_all_prices_in_new_price_list_form();
		addNewPriceListSteps.save_new_price_list();
		addItemToBusinessSteps.wait_for_saving_alert();
		addItemToBusinessSteps.refresh();
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addNewPriceListSteps.click_on_price_list_tab();
		addNewPriceListSteps.verify_price_list_displayed_in_list(priceListName);
		// end- add new price list
		// addGroupStep.wait_for_saving_alert();
		loginStep.click_on_settings();
		addItemToBusinessSteps.click_on_groups_left_menu();
		addGroupStep.click_on_add_group();
		addGroupStep.fill_in_group_name(groupName);
		System.out.println("price list is" + priceListName);
		addGroupStep.select_price_list(priceListName);
		addGroupStep.click_on_save_group();
		//addGroupStep.wait_for_saving_alert();
		navigationStep.refresh();
		addItemToBusinessSteps.click_on_groups_left_menu();
		addGroupStep.search_for_group_in_table(the("Grupuri", containsString(groupName)));
		WebElement el = addGroupStep.get_row_element_containing_group(the("Grupuri", containsString(groupName)));
		addGroupStep.click_on_modify(el);
		
		// end add group with attached price list
		navigationStep.click_on_clients_tab();
		loginStep.dismiss_any_popup_if_appears();
		// add new client
		clientsStep.click_on_add_new_client();
		clientsStep.fill_in_client_last_name(lastName);
		clientsStep.fill_in_client_first_name(firstName);
		clientsStep.click_on_save_client_button();

		clientsStep.refresh();
		navigationStep.click_on_clients_tab();
		clientsStep.get_client_web_element_containig_client(
				the("Nume", containsString(lastName)),
				the("Prenume", containsString((firstName))));
		clientsStep.select_client_in_table(lastName,
				firstName);
		clientsStep.click_on_add_client_to_group();
		clientsStep.select_group_for_client_to_add_to(groupName);
		clientsStep.click_on_save_adding_client_to_group();
		navigationStep.refresh();
		navigationStep.click_on_clients_tab();
		// add new group with attacehd price list

		// clientsStep.search_group(priceListName);
		WebElement groupEl = clientsStep.get_row_web_element_with_group(the("GRUP", containsString(groupName)));
		clientsStep.check_group_label(groupEl);
		clientsStep.get_client_web_element_containig_client(
				the("Nume", containsString(lastName)),
				the("Prenume", containsString(firstName)));
		// String price =
		// addNewPriceListSteps.getPriceListFor(attachedPriceList).get().getServicePrice();
		// System.out.println(price);

		addServiceStep.assertAll();
	}

}