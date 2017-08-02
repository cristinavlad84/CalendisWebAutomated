package ro.evozon.steps.serenity.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.WebElementFacade;
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
	public void select_location_calendar_tab(String locationName) {
		calendarPage.select_location_on_calendar_tab(locationName);
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
	public void select_specific_month_year_for_appointment(String month) {
		calendarPage.select_specific_motnh_year_for_appointment(month);
	}

	@Step
	public String select_random_day() {
		return calendarPage.select_random_day_of_week_for_appointment();
	}

	@Step
	public void select_specific_day(String day) {
		calendarPage.select_specific_day_of_week_for_appointment(day);
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
	public void fill_in_first_five_chars(String clientLastName) {
		String fiveChars = clientLastName.substring(0, 4);
		fill_in_client_last_name(fiveChars);
	}

	@Step
	public void select_client_details_from_tooltip() {
		calendarPage.select_client_suggestion_from_tooltip();
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
	public BigDecimal get_left_amount_to_pay() {
		return calendarPage.get_left_amount_to_pay();
	}

	@Step
	public void verify_amount_left_to_pay_for_special_price_grup_client(String servicePrice) {
		// dsfsd
	}

	@Step
	public WebElementFacade get_service_payment_form_container(String serviceName) {
		return calendarPage.get_payment_form_element_for_service(serviceName);
	}

	@Step
	public void select_voucher_code_appointment_form(WebElementFacade servicePaymentElement, String voucherCode) {
		calendarPage.select_voucher_code_in_appointment_form_for_service(servicePaymentElement, voucherCode);
	}

	@Step
	public void fill_in_discount_value_for_voucher_payment_form(WebElementFacade servicePaymentElement,
			String discountValue) {
		calendarPage.fill_in_discount_value_for_voucher_payment_form(servicePaymentElement, discountValue);
	}

	@Step
	public void fill_in_additional_cost_payment_form(WebElementFacade servicePaymentElement, String cost) {
		calendarPage.fill_in_additional_cost(servicePaymentElement, cost);
	}

	@Step
	public BigDecimal get_amount_to_pay_for_service(WebElementFacade servicePaymentElement) {
		return calendarPage.get_amount_to_pay_for_service(servicePaymentElement);
	}

	@Step
	public BigDecimal get_total_amount_for_all_services() {
		return calendarPage.get_total_amount_to_pay_for_single_service();
	}

	@Step
	public BigDecimal get_amount_left_to_pay_for_all_services() {
		return calendarPage.get_amount_left_to_pay_for_single_service();
	}

	@Step
	public BigDecimal get_amount_left_to_pay_top() {
		return calendarPage.get_amount_left_to_pay_on_top();
	}

	@Step
	public BigDecimal get_total_amount_on_card_payment_bottom() {
		return calendarPage.get_total_amount_on_card_payment_bottom();
	}

	@Step
	public BigDecimal get_payment_paid_in_fieldbox() {
		return calendarPage.get_payment_paid_in_fieldbox();
	}

	@Step
	public void fill_in_paid_value(String partialAmountToPay) {
		calendarPage.fill_in_partial_amount_to_pay(partialAmountToPay);
	}

	@Step
	public BigDecimal calculateDiscountValue(BigDecimal pricePerService, int percentage) {
		BigDecimal discountValue = pricePerService.multiply(new BigDecimal(percentage)).divide(new BigDecimal(100));
		discountValue = discountValue.setScale(2, RoundingMode.HALF_UP);
		return discountValue;
	}

	@Step
	public BigDecimal calculate_amount_left_to_pay(BigDecimal pricePerService, BigDecimal discountValue,
			BigDecimal additionalCost, BigDecimal partiallyPaidAmount) {
		BigDecimal amountLeftToPay = pricePerService.subtract(discountValue).add(additionalCost)
				.subtract(partiallyPaidAmount);
		amountLeftToPay = amountLeftToPay.setScale(2, RoundingMode.HALF_UP);
		return amountLeftToPay;
	}

	@Step
	public BigDecimal get_price_with_discount_and_other_costs(BigDecimal servicePrice, BigDecimal discountValue,
			BigDecimal otherCosts) {
		BigDecimal dd = servicePrice.subtract(discountValue).add(otherCosts);
		dd = dd.setScale(2, RoundingMode.HALF_UP);
		System.out.println("additional cost " + dd);
		return dd;
	}

	@Step
	public BigDecimal get_price_with_discount_and_other_costs(BigDecimal servicePrice, BigDecimal discountValue) {
		BigDecimal dd = servicePrice.subtract(discountValue);
		dd = dd.setScale(2, RoundingMode.HALF_UP);
		System.out.println("******substarcted with 2 decimals " + dd);
		return dd;
	}

	@Step
	public void verify_final_amount_to_pay_for_service(BigDecimal calculatedPrice, BigDecimal amountPerService) {
		softly.assertThat(calculatedPrice).as("calculated amount to pay for service").isEqualTo(amountPerService);
	}

	@Step
	public void verify_total_amount_to_pay_for_all_services(BigDecimal calculatedPrice, BigDecimal totalAmount) {
		softly.assertThat(calculatedPrice).as("service price with discount and other costs").isEqualTo(totalAmount);
	}

	@Step
	public void verify_amount_left_to_pay_for_single_service(BigDecimal calculatedPrice, BigDecimal amountLeft) {
		softly.assertThat(calculatedPrice).as("amount lef to pay").isEqualTo(amountLeft);
	}

	@Step
	public void verify_amount_left_to_pay_on_top(BigDecimal calculatedPrice, BigDecimal amountLeft) {
		softly.assertThat(calculatedPrice).as("amount left to pay on top").isEqualTo(amountLeft);
	}

	@Step
	public void verify_total_amount_on_bottom_payment_form(BigDecimal calculatedPrice, BigDecimal total) {
		softly.assertThat(calculatedPrice).as("grand total on bottom payment form").isEqualTo(total);
	}

	@Step
	public void verify_payment_paid_value(BigDecimal amountLeftToPay, BigDecimal paymentInField) {
		softly.assertThat(amountLeftToPay).as("amount left to pay").isEqualTo(paymentInField);
	}

	@Step
	public void click_on_collect_button_on_client_card() {
		calendarPage.click_on_collect_button_on_client_card();
	}

	@Step
	public void click_on_finalize_button() {
		calendarPage.click_on_finalize_button();
	}

	@Step
	public void verify_total_price_on_appointment_form(BigDecimal price) {
		BigDecimal priceOnform = calendarPage.get_total_price_on_appointment_form();
		softly.assertThat(priceOnform).as("service price on appoint. form").isEqualTo(price);
	}

	@Step
	public void click_on_payment_history() {
		calendarPage.click_on_payment_history();
	}

	@Step
	public String get_last_payment_in_payment_history() {
		return calendarPage.get_last_payment_in_payment_history();
	}

	@Step
	public void verify_last_payment_in_payment_history(String foundPrice, String price) {

		softly.assertThat(foundPrice).as("service price in last history payment").contains(price);
	}

	@Step
	public void verify_amount_left_to_pay_on_card_view_mode(BigDecimal foundAmount, BigDecimal expectedAmount) {
		softly.assertThat(foundAmount).as("found amount on card view mode").isEqualTo(expectedAmount)
				.as("expected amount left to pay");
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

	@Step
	public void expand_appointment_service_details_card_when_collapsed(String serviceName) {
		calendarPage.expand_services_details(serviceName);

	}

	@StepGroup
	public void fill_in_service_details_for_appointment(String domainName, String specialistName, String serviceName,
			int serviceDuration) {
		select_domain_for_appointment(domainName);
		select_service_for_appointment(serviceName);
		
		select_specialist_for_appointment(specialistName);

		fill_in_duration_for_service_appointment(Integer.toString(serviceDuration));

	}

	@StepGroup
	public String select_time_details_for_service_appointment_form(String serviceName) {
		String monthYear = new String();
		String day = new String();
		String hour = new String();
		String minutes = new String();
		long diff = 0;
		LocalDate today = LocalDate.now();
		String appointmentDate = new String();
		DateTimeFormatter formatterMonthYear = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
		boolean isAppointmentOutOfInterval = true;
		while (isAppointmentOutOfInterval || diff <= 0) { // fills again date
															// time intervals
			// if out of interval message
			// occur

			// verify_if card details collapsed, then expand
			if (diff <= 0) {
				expand_appointment_service_details_card_when_collapsed(serviceName);
			}
			System.out.println("sunt aici");
			monthYear = select_random_month_year_for_appointment();
			monthYear = ConfigUtils.formatMonthString(monthYear);
			monthYear = ConfigUtils.formatYearString(monthYear);
			day = select_random_day();
			day = ConfigUtils.extractDayOfWeek(day);
			hour = select_random_hour_for_appointment();
			minutes = select_random_minutes_for_appointment();
			click_outside_service_card_for_validation();
			isAppointmentOutOfInterval = is_appointment_out_of_staff_interval();
			appointmentDate = monthYear.concat(" ").concat(day).concat(" ").concat(hour).concat(" ").concat(minutes);
			System.out.println("app date  " + appointmentDate);
			LocalDate currentDateF = LocalDate.parse(appointmentDate, formatterMonthYear);
			diff = ChronoUnit.DAYS.between(today, currentDateF);
			System.out.println("diff between selected date and current date  " + diff);

		}

		return appointmentDate;
	}

	@StepGroup
	public String select_time_details_for_service_appointment_form(String serviceName, String serviceMonth,
			String serviceYear, String serviceDay) {
		String selectMonthYear = new String();

		String hour = new String();
		String minutes = new String();
		long diff = 0;
		LocalDate today = LocalDate.now();
		String appointmentDate = new String();
		DateTimeFormatter formatterMonthYear = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
		boolean isAppointmentOutOfInterval = true;
		while (isAppointmentOutOfInterval || diff <= 0) { // fills again date
															// time intervals
			// if out of interval message
			// occur

			// verify_if card details collapsed, then expand
			if (diff <= 0) {
				expand_appointment_service_details_card_when_collapsed(serviceName);
			}

			selectMonthYear = serviceMonth.concat(" ").concat("'").concat(serviceYear);
			System.out.println("selectMonthYear :"+selectMonthYear);
			select_specific_month_year_for_appointment(selectMonthYear);

			select_specific_day(serviceDay);

			hour = select_random_hour_for_appointment();
			minutes = select_random_minutes_for_appointment();
			click_outside_service_card_for_validation();
			isAppointmentOutOfInterval = is_appointment_out_of_staff_interval();
			appointmentDate = serviceMonth.concat(" ").concat(serviceYear).concat(" ").concat(serviceDay).concat(" ").concat(hour)
					.concat(" ").concat(minutes);
			System.out.println("app date  " + appointmentDate);
			LocalDate currentDateF = LocalDate.parse(appointmentDate, formatterMonthYear);
			diff = ChronoUnit.DAYS.between(today, currentDateF);
			System.out.println("diff between selected date and current date  " + diff);

		}

		return appointmentDate;
	}

}
