package ro.evozon.features.business.datadriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
import ro.evozon.tools.utils.DaysOfWeekConverter;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddLocationToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order toadd new location to business account", "As business user ",
		"I want to be able to add new location and then see location saved" })
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "$DATADIR/locatii.csv")
public class AddNewLocationDataDrivenStory extends BaseTest {
	private String adresaLocatie;
	private String judetLocatie;
	private String localitateLocatie;
	private String telefonLocatie;
	private String numeLocatie;
	private String luni;

	private String marti;
	private String miercuri;
	private String joi;
	private String vineri;
	private String sambata;
	private String duminica;
	// private String
	private String businessName, businessEmail, businessPassword, newLocationName, newLocationStreet, newLocationPhone;

	public void setLocationStreet(String locationStreet) {
		this.adresaLocatie = locationStreet;
	}

	public void setLocationRegion(String locationRegion) {
		this.judetLocatie = locationRegion;
	}

	public void setLocationCity(String locationCity) {
		this.localitateLocatie = locationCity;
	}

	public AddNewLocationDataDrivenStory() {
		super();

	}

	public void setLuni(String luni) {
		this.luni = luni;
	}

	public void setMarti(String marti) {
		this.marti = marti;
	}

	public void setMiercuri(String miercuri) {
		this.miercuri = miercuri;
	}

	public void setJoi(String joi) {
		this.joi = joi;
	}

	public void setVineri(String vineri) {
		this.vineri = vineri;
	}

	public void setSambata(String sambata) {
		this.sambata = sambata;
	}

	public void setDuminica(String duminica) {
		this.duminica = duminica;
	}

	@Qualifier
	public String qualifier() {
		return adresaLocatie + "=>" + judetLocatie + "=>" + localitateLocatie + "=>" + telefonLocatie + "=>"
				+ numeLocatie + "=>" + luni + "=>" + marti + "=>" + miercuri + "=>" + joi + "=>" + vineri + "=>"
				+ sambata + "=>" + duminica;
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

	@Steps
	public LoginBusinessAccountSteps loginStep;
	@Steps
	public AddItemToBusinessSteps addItemToBusinessSteps;
	@Steps
	public AddLocationToBusinessStep addLocationToBusinessSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;

	@Issue("#CLD-038")
	@Test
	public void add_new_location_then_verify_saved() {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.refresh();
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		addItemToBusinessSteps.click_on_location_left_menu();
		addLocationToBusinessSteps.click_on_add_location();
		addLocationToBusinessSteps.fill_in_location_name(numeLocatie);
		addLocationToBusinessSteps.fill_in_location_address(adresaLocatie);
		addLocationToBusinessSteps.fill_in_location_phone(telefonLocatie);
		addLocationToBusinessSteps.select_location_region(judetLocatie);

		addLocationToBusinessSteps.select_location_city(localitateLocatie);

		addLocationToBusinessSteps.click_on_set_location_schedule();
		List<String> daysOfweekStaffList = new ArrayList<String>();
		daysOfweekStaffList.add(luni);
		daysOfweekStaffList.add(marti);
		daysOfweekStaffList.add(miercuri);
		daysOfweekStaffList.add(joi);
		daysOfweekStaffList.add(vineri);
		daysOfweekStaffList.add(sambata);
		daysOfweekStaffList.add(duminica);
		daysOfweekStaffList.stream().forEach(k -> System.out.println("orar " + k));
		System.out.println("size =" + daysOfweekStaffList.size());
		for (int i = 0; i < daysOfweekStaffList.size(); i++) {
			System.out.println("i = " + i);
			if (!daysOfweekStaffList.get(i).contentEquals(ro.evozon.tools.Constants.CLOSED_SCHEDULE)) {
				addLocationToBusinessSteps.check_schedule_day_of_week_location(daysOfweekStaffList.get(i), i);
			}
		}

		addLocationToBusinessSteps.click_on_save_location_button();
		addLocationToBusinessSteps.verify_location_address_appears_in_location_section(adresaLocatie);
		System.out.println("locationCity " + localitateLocatie);
		addLocationToBusinessSteps.verify_location_details_appears_in_location_section(adresaLocatie, judetLocatie,
				localitateLocatie, telefonLocatie, numeLocatie);

		addLocationToBusinessSteps.assertAll();
	}

}