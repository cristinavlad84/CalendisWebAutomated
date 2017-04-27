package ro.evozon.features.business.settings;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
import ro.evozon.steps.serenity.business.AddLocationSteps;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to have new domain in business account",
		"As business user ",
		"I want to be able to add new domain and then see domain saved" })
@RunWith(SerenityRunner.class)
public class AddNewDomainFromBusinessAccountStory extends BaseTest {

	private String businessName, businessEmail, businessPassword, domainName;

	public AddNewDomainFromBusinessAccountStory() {
		super();

		this.domainName = FieldGenerators.generateRandomString(8, Mode.ALPHA);
	}

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH
				+ ConfigUtils.getOutputFileName();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			businessName = props.getProperty("businessName", businessName);
			businessEmail = props.getProperty("businessEmail", businessEmail);
			businessPassword = props.getProperty("businessPassword",
					businessPassword);

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
	public AddLocationSteps addlocationSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;

	@Issue("#CLD-039")
	@Test
	public void add_new_domain_then_verify_saved() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		addlocationSteps.click_on_domain_left_menu();
		addlocationSteps.click_on_add_domain();
		addlocationSteps.fill_in_domain_name(domainName);

		addlocationSteps.click_on_save_domain_button();

		addlocationSteps.verify_domain_appears_in_domain_section(domainName);

		// delete domain
		addlocationSteps.click_on_delete_domain(domainName);
		addlocationSteps.confirm_item_deletion_in_modal();
		addlocationSteps
				.verify_domain_not_displayed_in_domain_section(domainName);
		addlocationSteps.assertAll();
	}

}