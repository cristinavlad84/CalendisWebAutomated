package ro.evozon.steps.serenity.business;

import java.util.List;
import java.util.Optional;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.CalendarPage;
import ro.evozon.pages.business.ServicesPage;
import ro.evozon.pages.business.SettingsPage;
import ro.evozon.tools.ConfigUtils;
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
	public void select_domain_for_appointment(String domain) {
		calendarPage.select_domain_for_appointment(domain);
	}

	@Step
	public String select_random_specialist() {
		return calendarPage.select_specialist_for_appointment();
	}

	@Step
	public void select_specialist_for_appointment(String specialistName) {
		calendarPage.select_specialist_for_appointment(specialistName);
	}

	@Step
	public String select_random_service() {
		return calendarPage.select_service_for_appointment();
	}

	@Step
	public void select_service_for_appointment(String serviceName) {
		calendarPage.select_service_for_appointment(serviceName);
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
	public void click_on_add_another_service_to_appointment() {
		calendarPage.click_on_add_another_service_on_appointment_form();
	}

	@Step
	public boolean is_appointment_out_of_staff_interval() {
		return calendarPage.is_appointment_out_of_staff_interval();
	}

	@Step
	public void click_outside_service_card_for_validation() {
		calendarPage.click_outside_card_service_for_validation();
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

	@StepGroup
	public void fill_in_client_details_card_appointment_form(String clientLastName, String clientFirstName,
			String clientEmail, String clientPhone) {
		fill_in_client_last_name(clientLastName);
		fill_in_client_first_name(clientFirstName);
		fill_in_client_email(clientEmail);
		fill_in_client_phone_number(clientPhone);
	}

	@Step
	public String get_client_last_name_appointment_form() {
		return calendarPage.get_client_last_name_appointment_form();
	}

	@Step
	public String get_client_first_name_appointment_form() {
		return calendarPage.get_client_first_name_appointment_form();
	}

	@Step
	public String get_client_phone_appointment_form() {
		return calendarPage.get_client_phone_appointment_form();
	}

	@Step
	public String get_client_email_appointment_form() {
		return calendarPage.get_client_email_appointment_form();
	}

	@Step
	public void expand_client_card_by_index(int index) {
		calendarPage.expand_client_appointment_form(index);
	}

	@StepGroup
	public void verify_client_details_on_appointment_form(String clientLastName, String clientFirstName,
			String clientEmail, String clientPhone) {
		softly.assertThat(get_client_last_name_appointment_form().toLowerCase()).as("client last name")
				.isEqualTo(clientLastName.toLowerCase());
		softly.assertThat(get_client_first_name_appointment_form().toLowerCase()).as("client first name")
				.isEqualTo(clientFirstName.toLowerCase());
		softly.assertThat(get_client_email_appointment_form().toLowerCase()).as("client email")
				.isEqualTo(clientEmail.toLowerCase());
		softly.assertThat(get_client_phone_appointment_form().toLowerCase()).as("client phone")
				.isEqualTo(clientPhone.toLowerCase());
	}

	@Step
	public void click_on_add_another_client() {
		calendarPage.click_on_add_another_client_on_appointment_form();
	}

	@Step
	public void get_appointment_details_for(String startTime, String endTime, String serviceName) {
		Optional<String> opt = calendarPage.getAppointmentsDetailsFor(startTime, endTime, serviceName);
		System.out.println("found appointment with details" + opt.get());
		softly.assertThat(!opt.get().isEmpty());

	}

	@Step
	public void click_on_appointment_on_calendar(String startTime, String endTime, String serviceName) {
		calendarPage.click_on_appointment_with_details(startTime, endTime, serviceName);
	}

	@Step
	public void click_on_service_card_to_edit_appointment_form() {
		calendarPage.click_on_service_card_to_edit_appointment_form();
	}

	@Step
	public void click_on_collect_payment_appointment_form() {
		calendarPage.click_on_collect_payment();
	}

	@Step
	public void click_on_collect_button_on_client_card() {
		calendarPage.click_on_collect_button_on_client_card();
	}

	@Step
	public void verify_total_price_on_appointment_form(String price) {
		String priceOnform = calendarPage.get_total_price_on_appointment_form();
		softly.assertThat(priceOnform).as("service price on appoint. form").isEqualTo(price);
	}
	@Step
	public void click_on_payment_history(){
		calendarPage.click_on_payment_history();
	}
	@Step
	public void verify_last_payment_in_payment_history(String price){
		String foundPrice= calendarPage.get_last_payment_in_payment_history();
		softly.assertThat(foundPrice).as("service price in last history payment").contains(price);
	}
	@Step
	public void click_on_client_card_to_edit_appointment_form() {
		calendarPage.click_on_client_card_to_edit_appointment_form();
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

	@Step
	public void click_anywhere_in_calendar() {
		calendarPage.click_anywhere_in_calendar();
	}

	@StepGroup
	public void fill_in_service_details_for_appointment(String domainName, String specialistName, String serviceName,
			int serviceDuration) {
		select_domain_for_appointment(domainName);
		select_specialist_for_appointment(specialistName);
		select_service_for_appointment(serviceName);
		fill_in_duration_for_service_appointment(Integer.toString(serviceDuration));

	}

	@StepGroup
	public String select_time_details_for_service_appointment_form() {
		String monthYear = new String();
		String day = new String();
		String hour = new String();
		String minutes = new String();
		boolean isAppointmentOutOfInterval = true;
		while (isAppointmentOutOfInterval) { // fills again date time intervals
												// if out of interval message
												// occur
			monthYear = select_random_month_year_for_appointment();
			monthYear = ConfigUtils.formatMonthString(monthYear);
			monthYear = ConfigUtils.formatYearString(monthYear);
			day = select_random_day();
			day = ConfigUtils.extractDayOfWeek(day);
			hour = select_random_hour_for_appointment();
			minutes = select_random_minutes_for_appointment();
			click_outside_service_card_for_validation();
			isAppointmentOutOfInterval = is_appointment_out_of_staff_interval();

		}
		return monthYear.concat(" ").concat(day).concat(" ").concat(hour).concat(" ").concat(minutes);
	}

}
