package ro.evozon.features.business.settings;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
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
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.ClientsStep;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NavigationStep;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to add new clients  into business account", "As business user ",
		"I want to be able to add new client then see client is properly saved" })
@RunWith(SerenityRunner.class)
public class AddNewClientStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, lastName, firstName;
	BigDecimal discount;
	String listName = "Standard";

	public AddNewClientStory() {
		super();

		this.lastName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
		this.firstName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
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

	@Issue("#CLD-")
	@Test
	public void add_new_client_then_verify_saved() {
//		lastName=ConfigUtils.capitalizeFirstLetter(lastName);
//		firstName=ConfigUtils.capitalizeFirstLetter(firstName);
		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		loginStep.logout_link_should_be_displayed();
		navigationStep.acceptCookies();
		// user should be logged in --> Deconecteaza-te should be displayed
		// create special price list
		navigationStep.click_on_clients_tab();
		loginStep.dismiss_any_popup_if_appears();
		// add new client
		clientsStep.click_on_add_new_client();
		clientsStep.fill_in_client_last_name(lastName);
		clientsStep.fill_in_client_first_name(firstName);
		clientsStep.click_on_save_client_button();
		//clientsStep.wait_for_saving_alert();
		navigationStep.refresh();
		navigationStep.click_on_clients_tab();
		clientsStep.get_client_web_element_containig_client(
				the("Nume", containsString(lastName)),
				the("Prenume", containsString((firstName))));
		
		// String price =
		// addNewPriceListSteps.getPriceListFor(attachedPriceList).get().getServicePrice();
		// System.out.println(price);

		addServiceStep.assertAll();
	}

}