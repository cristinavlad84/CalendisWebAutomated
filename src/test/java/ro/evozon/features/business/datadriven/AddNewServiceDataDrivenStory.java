package ro.evozon.features.business.datadriven;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.containsString;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import ro.evozon.steps.serenity.business.AddAppointmentToBusinessStep;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddServiceToBusinessStep;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.utils.fileutils.ReadCSV;

@Narrative(text = { "In order to add new service to business account", "As business user ",
		"I want to be able to add new service and then see service is properly saved" })
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("$DATADIR/servicii.csv")
public class AddNewServiceDataDrivenStory extends BaseTest {
	private String domeniulAsociat;
	private String serviciu;
	private String durataServiciu;
	private String pretServiciu;
	private String persoaneServiciu;
	public String locatieDomeniu;
	public List<List<String>> values;

	@Qualifier
	public String qualifier() {
		return domeniulAsociat + "=>" + serviciu + "=>" + durataServiciu + "=>" + pretServiciu + "=>" + "=>"
				+ persoaneServiciu + "=>" + locatieDomeniu + "=>" + values;
	}

	public void setDomeniulAsociat(String domeniulAsociat) {
		this.domeniulAsociat = domeniulAsociat;
	}

	public void setServiciu(String serviciu) {
		this.serviciu = serviciu;
	}

	public void setDurataServiciu(String durataServiciu) {
		this.durataServiciu = durataServiciu;
	}

	public void setPretServiciu(String pretServiciu) {
		this.pretServiciu = pretServiciu;
	}

	public void setPersoaneServiciu(String persoaneServiciu) {
		this.persoaneServiciu = persoaneServiciu;
	}

	private String businessName, businessEmail, businessPassword, businessMainLocation, businessMainLocationCounty,
			businessMainLocationCity;

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
		String fileN = Constants.OUTPUT_PATH_DATA_DRIVEN + ConfigUtils.getOutputFileNameForAllDomains();
		values = ReadCSV.readFromCsvFile(fileN);

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
	public AddStaffToBusinessStep addSpecialitsSteps;
	@Steps
	public AddAppointmentToBusinessStep addAppointmentToBusinessStep;

	@Issue("#CLD-040")
	@Test
	public void add_new_service_then_verify_saved() throws Exception {
		locatieDomeniu = ReadCSV.get_domain_location_by_domain_name(values, domeniulAsociat);
		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.refresh();
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		// String locatie = // to do read from proeprties file domenii.propertis
		addAppointmentToBusinessStep.select_location_calendar_tab(ConfigUtils.capitalizeFirstLetter(locatieDomeniu));

		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		// // addlocationSteps.c
		addItemToBusinessSteps.click_on_sevice_left_menu();
		// addItemToBusinessSteps.click_on_sevice_left_menu();
		addServiceStep.click_on_add_service();
		addServiceStep.fill_in_service_name(serviciu);
		addServiceStep.fill_in_service_price(pretServiciu);
		addServiceStep.select_domain_to_add_service(domeniulAsociat);

		addServiceStep.fill_in_service_duration_per_service(durataServiciu);
		addServiceStep.fill_in_max_persons_per_service(persoaneServiciu);
		addServiceStep.click_on_save_service_button();
		addServiceStep.wait_for_saving_alert();
		loginStep.refresh();
		addItemToBusinessSteps.click_on_sevice_left_menu();
		addServiceStep.get_service_in_table_matching(
				the("Servicii individuale", containsString(ConfigUtils.capitalizeFirstLetter(serviciu))));
		WebElement serviceEl = addServiceStep.get_service_webelement_in_list(
				the("Servicii individuale", containsString(ConfigUtils.capitalizeFirstLetter(serviciu))));
		addServiceStep.verify_service_details_appears_in_service_section(serviceEl, pretServiciu, durataServiciu,
				persoaneServiciu);

		addServiceStep.assertAll();
	}
}