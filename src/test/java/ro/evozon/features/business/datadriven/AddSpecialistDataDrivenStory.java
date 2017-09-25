package ro.evozon.features.business.datadriven;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.Tools;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.tools.utils.DaysOfWeekConverter;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.StaffType;
import ro.evozon.steps.serenity.business.AddItemToBusinessSteps;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to login to business account as specialist", "As business user ",
		"I want to be able to add new specialist and then login into specialist account" })
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "$DATADIR/angajati.csv")
public class AddSpecialistDataDrivenStory extends BaseTest {
	private String numeAngajat;
	private String emailAngajat;
	private String telefonAngajat;
	private String luni, marti, miercuri, joi, vineri, sambata, duminica;
	private String serviciuAsignat;

	public void setServiciuAsignat(String serviciuAsignat) {
		this.serviciuAsignat = serviciuAsignat;
	}

	private String businessName, businessEmail, businessPassword, businessMainLocation;

	public AddSpecialistDataDrivenStory() {
		super();

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
			businessMainLocation = props.getProperty("businessMainLocation", businessMainLocation);

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
	public AddStaffToBusinessStep addSpecialitsSteps;
	@Steps
	public StaffSteps staffSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;

	@Issue("#CLD-030; #CLD-043")
	@Test
	public void add_specialist_then_set_psw_and_login_into_specialist_account() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.refresh();
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		addSpecialitsSteps.click_on_add_new_staff_button();
		addSpecialitsSteps.fill_in_staff_name(numeAngajat);
		addSpecialitsSteps.fill_in_staff_email(emailAngajat);
		addSpecialitsSteps.fill_in_staff_phone(telefonAngajat);
		addSpecialitsSteps.select_staff_type_to_add(StaffType.EMPL.toString());
		addSpecialitsSteps.check_default_service_for_staff(serviciuAsignat);
		addSpecialitsSteps.click_on_set_staff_schedule();
		// addSpecialitsSteps.select_day_of_week_for_staff_schedule();
		List<String> daysOfweekStaffList = new ArrayList<String>();
		daysOfweekStaffList.add(luni);
		daysOfweekStaffList.add(marti);
		daysOfweekStaffList.add(miercuri);
		daysOfweekStaffList.add(joi);
		daysOfweekStaffList.add(vineri);
		daysOfweekStaffList.add(sambata);
		daysOfweekStaffList.add(duminica);
		int length = daysOfweekStaffList.size();
		for (int i = 0; i < length; i++) {
			if (!daysOfweekStaffList.get(i).contentEquals(ro.evozon.tools.Constants.CLOSED_SCHEDULE)) {
				addSpecialitsSteps.fill_in_schedule_form_for_staff(DaysOfWeekConverter.convertToDayOfWeek(i),
						daysOfweekStaffList.get(i), i);
			}
		}
		addSpecialitsSteps.click_on_save_staff_schedule();

		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(numeAngajat);

		loginStep.assertAll();
	}

}