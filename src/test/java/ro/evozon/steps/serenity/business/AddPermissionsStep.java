package ro.evozon.steps.serenity.business;

import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.BusinessWizardPage;
import ro.evozon.pages.business.PermissionPage;
import ro.evozon.pages.business.SettingsPage;
import ro.evozon.pages.business.StaffPage;
import ro.evozon.tools.utils.Time24HoursValidator;

public class AddPermissionsStep extends AbstractSteps {
	PermissionPage permsissionPage;

	@Step
	public void check_appointment_creation_permission(String specialistName) {

		permsissionPage.check_permission_appointment_creation(specialistName);
	}

	@Step
	public void check_appointment_edit_in_future_permission(String specialistName) {

		permsissionPage.check_permission_appointment_edit_future(specialistName);
	}

	@Step
	public void check_appointment_edit_in_past_permission(String specialistName) {

		permsissionPage.check_permission_appointment_edit_past(specialistName);
	}

	@Step
	public void check_calendar_view_for_others_permission(String specialistName) {

		permsissionPage.check_permission_calendar_view_for_others(specialistName);
	}

	@Step
	public void check_appointment_creation_for_others_permission(String specialistName) {

		permsissionPage.check_permission_appointment_creation_for_others(specialistName);
	}

	@Step
	public void check_appointment_edit_in_future_for_others_permission(String specialistName) {

		permsissionPage.check_permission_appointment_edit_future_for_others(specialistName);
	}

	@Step
	public void check_appointment_edit_in_past_for_others_permission(String specialistName) {

		permsissionPage.check_permission_appointment_edit_past_for_others(specialistName);
	}

	@Step
	public void check_schedule_permission(String specialistName) {

		permsissionPage.check_permission_schedule(specialistName);
	}

	@Step
	public void check_exception_permission(String specialistName) {

		permsissionPage.check_permission_exceptions(specialistName);
	}

	@Step
	public void check_client_contacts_permission(String specialistName) {

		permsissionPage.check_permission_clients_contacts(specialistName);
	}

	@Step
	public void check_client_database_permission(String specialistName) {

		permsissionPage.check_permission_clients_database_view(specialistName);
	}

	@Step
	public void check_client_info_edit_permission(String specialistName) {

		permsissionPage.check_permission_clients_info_edit(specialistName);
	}
}
