package ro.evozon.features.business.datadriven;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.enums.StaffType;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to login to business account as receptionist", "As business user ",
		"I want to be able to add new receptionist and then login into receptionist account" })
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "$DATADIR/receptie.csv")
public class AddReceptionistDataDrivenStory extends BaseTest {
	private String numeReceptionist;
	private String emailReceptionist;
	private String telefonReceptionist;
	private String businessName, businessEmail, businessPassword;

	public void setNumeReceptionist(String numeReceptionist) {
		this.numeReceptionist = numeReceptionist;
	}

	public void setEmailReceptionist(String emailReceptionist) {
		this.emailReceptionist = emailReceptionist;
	}

	public void setTelefonReceptionist(String telefonReceptionist) {
		this.telefonReceptionist = telefonReceptionist;
	}



	@Qualifier
	public String qualifier() {
		return numeReceptionist + "=>" + emailReceptionist + "=>" + telefonReceptionist ;
	}

	public AddReceptionistDataDrivenStory() {
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

	@Issue("#CLD-030; #CLD-043")
	@Test
	public void add_receptionist_then_set_psw_and_login_into_receptionist_account() throws Exception {

		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.refresh();
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		addSpecialitsSteps.click_on_add_new_staff_button();
		addSpecialitsSteps.fill_in_staff_name(numeReceptionist);
		addSpecialitsSteps.fill_in_staff_email(emailReceptionist);
		addSpecialitsSteps.fill_in_staff_phone(telefonReceptionist);
		addSpecialitsSteps.select_staff_type_to_add(StaffType.REC.toString());
		// addSpecialitsSteps.check_default_location();
		//
		// addSpecialitsSteps.click_on_set_staff_schedule();
		// addSpecialitsSteps.select_day_of_week_for_staff_schedule();

		addSpecialitsSteps.click_on_save_receptionist();

		addSpecialitsSteps.is_staff_name_displayed_in_personal_section(numeReceptionist);

		addSpecialitsSteps.assertAll();
	}

}