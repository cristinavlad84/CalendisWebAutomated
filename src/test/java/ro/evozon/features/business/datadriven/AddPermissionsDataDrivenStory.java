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
import ro.evozon.steps.serenity.business.AddPermissionsStep;
import ro.evozon.steps.serenity.business.AddStaffToBusinessStep;
import ro.evozon.steps.serenity.business.BusinessWizardSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.StaffSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to set permission for specialist", "As business user ",
		"I want to be able to add permission for specialist accounts" })
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "$DATADIR/permisiuni.csv")
public class AddPermissionsDataDrivenStory extends BaseTest {
	private String creareProgramari;
	private String modificariProgramariInViitor;
	private String modificariProgramariInTrecut;
	private String vizualizareCalendar;
	private String creareProgramariAltiSpecialisti;
	private String modificariProgramariInViitorAltiSpecialisti;
	private String modificariProgramariInTrecutAltiSpecialisti;
	private String dateDeContactClienti;
	private String vizualizareBazaDeDateClienti;
	private String editareInformatiiClienti;
	private String setariOrar;
	private String setariExceptii;
	private String businessName, businessEmail, businessPassword, businessMainLocation;

	public void setCreareProgramari(String creareProgramari) {
		this.creareProgramari = creareProgramari;
	}

	public void setModificariProgramariInViitor(String modificariProgramariInViitor) {
		this.modificariProgramariInViitor = modificariProgramariInViitor;
	}

	public void setModificariProgramariInTrecut(String modificariProgramariInTrecut) {
		this.modificariProgramariInTrecut = modificariProgramariInTrecut;
	}

	public void setVizualizareCalendar(String vizualizareCalendar) {
		this.vizualizareCalendar = vizualizareCalendar;
	}

	public void setCreareProgramariAltiSpecialisti(String creareProgramariAltiSpecialisti) {
		this.creareProgramariAltiSpecialisti = creareProgramariAltiSpecialisti;
	}

	public void setModificariProgramariInViitorAltiSpecialisti(String modificariProgramariInViitorAltiSpecialisti) {
		this.modificariProgramariInViitorAltiSpecialisti = modificariProgramariInViitorAltiSpecialisti;
	}

	public void setModificariProgramariInTrecutAltiSpecialisti(String modificariProgramariInTrecutAltiSpecialisti) {
		this.modificariProgramariInTrecutAltiSpecialisti = modificariProgramariInTrecutAltiSpecialisti;
	}

	public void setSetariOrar(String setariOrar) {
		this.setariOrar = setariOrar;
	}

	public void setSetariExceptii(String setariExceptii) {
		this.setariExceptii = setariExceptii;
	}

	public AddPermissionsDataDrivenStory() {
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
	public AddPermissionsStep addPermissionsStep;
	@Steps
	public StaffSteps staffSteps;
	@Steps
	BusinessWizardSteps businessWizardSteps;

	@Issue("#CLD; #CLD")
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
		System.out.println("specialist name for creare programari " + creareProgramari);
		addPermissionsStep.check_appointment_creation_permission(creareProgramari);
		addPermissionsStep.check_appointment_edit_in_future_permission(modificariProgramariInViitor);
		System.out.println("modificari in trecut "+modificariProgramariInTrecut);
		addPermissionsStep.check_appointment_edit_in_past_permission(modificariProgramariInTrecut);
		addPermissionsStep.check_calendar_view_for_others_permission(vizualizareCalendar);
		addPermissionsStep.check_appointment_creation_for_others_permission(creareProgramariAltiSpecialisti);
		addPermissionsStep
				.check_appointment_edit_in_future_for_others_permission(modificariProgramariInViitorAltiSpecialisti);
		addPermissionsStep
				.check_appointment_edit_in_past_for_others_permission(modificariProgramariInTrecutAltiSpecialisti);
		addPermissionsStep.check_client_contacts_permission(dateDeContactClienti);
		addPermissionsStep.check_client_database_permission(vizualizareBazaDeDateClienti);
		addPermissionsStep.check_client_info_edit_permission(editareInformatiiClienti);
		addPermissionsStep.check_schedule_permission(setariOrar);
		addPermissionsStep.check_exception_permission(setariExceptii);
		loginStep.assertAll();
	}

}