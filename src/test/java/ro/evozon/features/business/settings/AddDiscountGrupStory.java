package ro.evozon.features.business.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.steps.serenity.business.AddDomainToBusinessStep;
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

@Narrative(text = { "In order to add new clients group with discount into business account", "As business user ",
		"I want to be able to add client group with discount and then see group is properly saved" })
@RunWith(SerenityRunner.class)
public class AddDiscountGrupStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, groupName;
	BigDecimal discount;
	String listName = "Standard";

	public AddDiscountGrupStory() {
		super();
		double discountValue = FieldGenerators.getRandomDoubleBetween(1, 99);
		BigDecimal dd = BigDecimal.valueOf(discountValue);
		this.discount = dd.setScale(0, RoundingMode.HALF_UP);
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
	BusinessWizardSteps businessWizardSteps;
	@Steps
	public AddLocationToBusinessStep addLocationToBusinessSteps;
	@Steps
	public AddDomainToBusinessStep addDomainSteps;
	@Steps
	public AddStaffToBusinessStep addSpecialitsSteps;
	@Steps
	public AddVoucherToBusinessStep addVoucherToBusinessStep;
	@Steps
	NavigationStep navigationStep;
	@Steps
	ClientsStep clientsStep;

	@Issue("#CLD-")
	@Test
	public void add_new_client_group_with_discount_then_verify_saved() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		navigationStep.acceptCookies();
		navigationStep.click_on_clients_tab();
		clientsStep.click_on_clients_group_tab();
		// add new clients group with discount % from standard list
		clientsStep.click_on_add_new_group();
		clientsStep.fill_in_client_group_name(groupName);
		clientsStep.select_price_list(listName);
		clientsStep.fill_in_discount_group_value(discount.toString());
		clientsStep.click_on_save_grouo_button();
		clientsStep.wait_for_saving_alert();
		navigationStep.refresh();
		clientsStep.click_on_clients_group_tab();
		clientsStep.search_for_saved_group(groupName, discount.toString().concat(" %"));
		addServiceStep.assertAll();
	}

}