package ro.evozon.steps.serenity.business;

import java.util.List;
import java.util.Optional;

import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.CalendarPage;
import ro.evozon.pages.business.ServicesPage;
import ro.evozon.pages.business.SettingsPage;
import ro.evozon.tools.models.PriceList;

public class AddAppointmentToBusinessStep extends AbstractSteps {
	CalendarPage calendarPage;
	SettingsPage settingsPage;

	@Step
	public void click_on_quick_appointment_button() {
		calendarPage.click_on_add_quick_appointment();
	}

	@Step
	public void select_random_domain() {
		calendarPage.select_domain_for_appointment();
	}

	@Step
	public void select_random_specialist() {
		calendarPage.select_specialist_for_appointment();
	}

	@Step
	public void select_random_service() {
		calendarPage.select_service_for_appointment();
	}

	@Step
	public String select_random_month_year_for_appointment() {
		return calendarPage.select_random_month_year_for_appointment();
	}

	@Step
	public String select_random_day() {
		return calendarPage.select_random_day_of_week_for_appointment();
	}

	@Step
	public String select_random_hour_for_appointment() {
		return calendarPage.select_random_hour_for_appointment();
	}

	@Step
	public String select_random_minutes_for_appointment() {
		return calendarPage.select_random_minutes_for_appointment();
	}

	@Step
	public void fill_in_client_last_name(String clientLastName) {
		calendarPage.fill_in_client_last_name_for_appointment(clientLastName);
	}

	@Step
	public void fill_in_client_first_name(String clientFirstName) {
		calendarPage.fill_in_client_first_name_for_appointment(clientFirstName);
	}

	@Step
	public void fill_in_client_phone_number(String clientPhoneNo) {
		calendarPage.fill_in_client_phone_number_for_appointment(clientPhoneNo);
	}

	@Step
	public void fill_in_client_email(String clientEmail) {
		calendarPage.fill_in_client_email_for_appointment(clientEmail);
	}

	@Step
	public void click_on_save_appointment() {
		calendarPage.click_on_save_appointment();
	}

	@Step
	public void confirm_appointment_out_of_interval() {
		calendarPage.confirm_appointment_creation_out_interval_popup();
	}
}
