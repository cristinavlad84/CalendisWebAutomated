package ro.evozon.features.business.datadriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;

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
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "$DATADIR/domenii.csv")
public class AddNewDomainDataDrivenStory extends BaseTest {
	private String numeDomeniu;
	private String locatiaDomeniului;

	public void setLocatiaDomeniului(String locatiaDomeniului) {
		this.locatiaDomeniului = locatiaDomeniului;
	}

	private String businessName, businessEmail, businessPassword;

	public void setDomainName(String domainName) {
		this.numeDomeniu = domainName;
	}

	public AddNewDomainDataDrivenStory() {
		super();

		this.numeDomeniu = FieldGenerators.generateRandomString(8, Mode.ALPHA);
	}

	@Qualifier
	public String qualifier() {
		return numeDomeniu + "=>" + locatiaDomeniului;
	}

	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForNewBusinessFromXlsx();
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
	public void getPropertiesListed() {
		String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForDomainAsociation();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			for (Map.Entry<Object, Object> entry : props.entrySet()) {
				System.out.println(entry.getKey() + " = " + entry.getValue());
			}

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

	public void writeToPropertiesFile() {
		FileOutputStream fileOut = null;
		FileInputStream writer = null;
		try {

			String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForDomainAsociation();
			Properties props = new Properties();
			File file = new File(fileName);
			Map<Object, Object> propMap = props;

			if (propMap.size() > 0) {
				props.load(writer);
				writer = new FileInputStream(file);
				props.setProperty(numeDomeniu, locatiaDomeniului);
				fileOut = new FileOutputStream(file);
				props.store(fileOut, "domain association");
				writer.close();
			} else {
				props.setProperty(numeDomeniu, locatiaDomeniului);
				props.store(fileOut, "domain association");
			}
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
		//writeToPropertiesFile();

		System.out.println("nume domeniu" + numeDomeniu);
		System.out.println("locatiaDomeniului" + locatiaDomeniului);
		 
		 loginStep.navigateTo(ConfigUtils.getBaseUrl());
		 loginStep.refresh();
		 loginStep.login_into_business_account(businessEmail,
		 businessPassword);
		 loginStep.dismiss_any_popup_if_appears();
		 // user should be logged in --> Deconecteaza-te should be displayed
		
		 loginStep.logout_link_should_be_displayed();
		 loginStep.click_on_settings();
		 loginStep.dismiss_any_popup_if_appears();
		 addItemToBusinessStep.click_on_domain_left_menu();
		 addDomainSteps.click_on_add_domain();
		 addDomainSteps.select_location_in_domain_form(locatiaDomeniului);
		 addDomainSteps.fill_in_domain_name(numeDomeniu);
		
		 addDomainSteps.click_on_save_domain_button();
		
		 addDomainSteps.verify_domain_name_appears_in_domain_section(numeDomeniu);
		
		 addDomainSteps.assertAll();
	}

}