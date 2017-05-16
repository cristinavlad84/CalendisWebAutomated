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
	public String select_random_domain() {
		return calendarPage.select_domain_for_appointment();
	}

	@Step
	public String select_random_specialist() {
		return calendarPage.select_specialist_for_appointment();
	}

	@Step
	public String select_random_service() {
		return calendarPage.select_service_for_appointment();
	}

	@Step
	public void fill_in_duration_for_service_appointment(String duration) {
		calendarPage.fill_in_service_duratioin_for_appointment(duration);
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

	@Step
	public void get_appointment_details_for(String startTime, String endTime, String serviceName) {
		Optional<String> opt = calendarPage.getAppointmentsDetailsFor(startTime, endTime, serviceName);
		System.out.println("found appointment with details" + opt.get());
		softly.assertThat(!opt.get().isEmpty());

	}

	@Step
	public void select_domain_calendar_left_menu(String domainName) {
		calendarPage.select_domain_calendar_left_menu(domainName);
	}

	@Step
	public void select_service_calendar_left_menu(String domainName, String serviceName) {
		calendarPage.select_service_calendar_left_menu(domainName, serviceName);
	}

	@Step
	public void select_specialist_calendar_left_menu(String specialistName) {
		calendarPage.select_specialist_calendar_left_menu(specialistName);
	}

	@Step
	public void navigate_to_target_date_in_mini_calendar(String targetDate) {
		calendarPage.navigate_to_target_date(targetDate);
	}

	@Step
	public void click_on_day_view_in_calendar() {
		calendarPage.select_day_view();
	}

	@Step
	public void click_on_mini_calendar() {
		calendarPage.click_on_mini_calendar();
	}
}
