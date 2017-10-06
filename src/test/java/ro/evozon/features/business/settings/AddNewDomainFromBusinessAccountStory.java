package ro.evozon.features.business.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import ro.evozon.steps.serenity.business.AddDomainToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to have new domain in business account", "As business user ",
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

	@After
	public void writeToPropertiesFile() {
		FileOutputStream fileOut = null;
		FileInputStream writer = null;
		try {

			String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileName();
			Properties props = new Properties();
			File file = new File(fileName);
			writer = new FileInputStream(file);
			props.load(writer);
			props.setProperty("domainName", domainName);
			props.setProperty("domainAssociatedLocationName",
					Serenity.sessionVariableCalled("domainAssociatedLocationName").toString());

			fileOut = new FileOutputStream(file);
			props.store(fileOut, "business user details");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Steps
	public LoginBusinessAccountSteps loginStep;

	@Steps
	public AddDomainToBusinessStep addDomainSteps;
	@Steps
	public AddItemToBusinessSteps addItemToBusinessStep;
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
		addItemToBusinessStep.click_on_domain_left_menu();
		addDomainSteps.click_on_add_domain();
		Serenity.setSessionVariable("domainAssociatedLocationName")
				.to(addDomainSteps.select_random_location_in_domain_form());
		addDomainSteps.fill_in_domain_name(domainName);

		addDomainSteps.click_on_save_domain_button();

		addDomainSteps.verify_domain_name_appears_in_domain_section(domainName);

		addDomainSteps.assertAll();
	}

}