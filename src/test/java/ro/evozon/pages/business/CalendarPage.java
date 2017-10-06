package ro.evozon.pages.business;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;
import ro.evozon.AbstractPage;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;

public class CalendarPage extends AbstractPage {

	public void select_day_view() {
		clickOn(find(By.id("day-view")));
	}

	public String get_current_date_in_mini_calendar() {
		List<WebElementFacade> currentMonthL = findAll(
				By.cssSelector("div[class='datepicker-days'] > table > thead > tr > th[class='datepicker-switch']"));

		return currentMonthL.get(0).getText().trim();
	}

	public String get_current_day_of_month() {
		WebElementFacade dayEl = null;
		List<WebElementFacade> currentMonthL = findAll(
				By.cssSelector("div[class='datepicker-days'] > table > tbody td"));
		for (WebElementFacade el : currentMonthL) {
			if (el.getAttribute("class").contains("today active day")) {
				dayEl = el;
				break;
			}
		}
		return dayEl.getText().trim();
	}

	public void click_on_next_month_navigation() {
		click_on_element(
				(find(By.cssSelector("div[class='datepicker-days'] > table > thead > tr > th[class='next']"))));
	}

	public void click_on_prev_month_navigation() {
		clickOn(find(By.cssSelector("div[class='datepicker-days'] > table > thead > tr > th[class='prev']")));
	}

	public void click_on_mini_calendar() {
		clickOn(find(By.cssSelector("div#mini-calendar")));
		waitForPageToLoad();
	}

	public void click_anywhere_in_calendar() {
		clickOn(find(By.cssSelector("div[class='appointment-staff-slot-container ui-droppable']")));
	}

	public void expand_services_details(String serviceName) {
		List<WebElementFacade> viewModeElemList = findAll(
				By.cssSelector("div#appointment-cards div[class='viewMode-details']  > strong > div:nth-child(2)"));
		// System.out.println("detected in view mode ");
		// System.out.println("sizee "+viewModeElemList.size());
		if (viewModeElemList.size() >= 1) { // if in view mode any
			for (WebElementFacade el : viewModeElemList) {
				System.out.println("compare  " + el.getText() + " with " + serviceName.trim().toLowerCase());
				if (el.getText().trim().toLowerCase().contentEquals(serviceName.trim().toLowerCase())) {
					click_on_element(el);
					System.out.println("expanded ");
				}
			}
			// int index=count-1;

		}
	}

	public LocalDate parseTargetDate(String targetDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
		LocalDate targetDateF = LocalDate.parse(targetDate, formatter);
		System.out.println("target date is " + targetDateF);
		return targetDateF;
	}

	public LocalDate parseCurrentDate(String currentDate) {

		DateTimeFormatter formatterMonthYear = DateTimeFormatter.ofPattern("MMM yyyy d", Locale.ENGLISH);
		String[] strL = currentDate.split(" ");
		String month = strL[0];
		String year = strL[1];
		String day = strL[2];
		month = ConfigUtils.replaceMonthFromRoToEn(month);
		currentDate = month.concat(" ").concat(year).concat(" ").concat(day);
		System.out.println("current date replaced in EN " + currentDate);
		LocalDate currentDateF = LocalDate.parse(currentDate, formatterMonthYear);
		return currentDateF;
	}

	public long calculate_month_diff_for_target_date(String currentDate, String targetDate) {

		LocalDate targetDateF = parseTargetDate(targetDate);
		LocalDate currentDateF = parseCurrentDate(currentDate);
		long diff = ChronoUnit.MONTHS.between(currentDateF, targetDateF);
		System.out.println("diff month " + diff);
		return diff;
	}

	public void navigate_to_target_date(String targetDate) {
		String currentDate = get_current_date_in_mini_calendar().concat(" ").concat("1"); // first
																							// day
																							// of
																							// current
																							// month
																							// ->
																							// otherwise
																							// diff
																							// will
																							// evaluate
																							// wrong
																							// number
																							// of
																							// clicks
																							// on
																							// next
		int dayOfMonth = parseTargetDate(targetDate).getDayOfMonth();
		long diff = calculate_month_diff_for_target_date(currentDate, targetDate);
		while (diff > 0) {
			click_on_next_month_navigation();
			System.out.println("Clicked on next month mini calendar");
			diff--;
		}
		// navigate to day of month
		List<WebElementFacade> dayList = findAll(
				By.cssSelector("div[class='datepicker-days'] > table > tbody td[class='day']"));
		for (WebElementFacade day : dayList) {
			System.out.println("comparing " + day.getText().trim() + " with " + Integer.toString(dayOfMonth));
			if (day.getText().trim().contentEquals(Integer.toString(dayOfMonth))) {
				System.out.println("found");
				click_on_element(day);
				break;
			}
		}
	}

	public void click_on_add_quick_appointment() {
		WebElementFacade el = find(By.id("quick-appointment-trigger"));
		scroll_in_view_then_click_on_element(el);
	}

	public void select_location_on_calendar_tab(String locationName) {
		// WebElementFacade dropdown = find(By.cssSelector(""));

		List<WebElementFacade> mList = findAll(By.cssSelector("select#sidebar-locations-select"));
		if (mList.size() > 0) {
			select_option_in_dropdown(mList.get(0), locationName);
		}
	}

	public String select_domain_for_appointment() {
		WebElementFacade dropdown = find(
				By.cssSelector("form#group-appointment-fields > div:nth-of-type(1)  > select[name='domain']"));
		return select_random_option_in_dropdown(dropdown);
	}

	public void select_domain_for_appointment(String domain) {
		WebElementFacade dropdown = find(
				By.cssSelector("form#group-appointment-fields > div:nth-of-type(1)  > select[name='domain']"));
		String interim = domain.toLowerCase();
		String domainName = interim.substring(0, 1).toUpperCase() + interim.substring(1);
		select_option_in_dropdown(dropdown, domainName);

	}

	public String select_specialist_for_appointment() {
		List<WebElementFacade> dropdownL = findAll(
				By.cssSelector("form#group-appointment-fields > div:nth-of-type(2)  > select[name='staff'] > option"));
		return select_random_option_in_list(dropdownL);
	}

	public void select_specialist_for_appointment(String specialistName) {
		WebElementFacade dropdownL = find(
				By.cssSelector("form#group-appointment-fields > div:nth-of-type(2)  > select[name='staff']"));
		String interim = specialistName.toLowerCase();
		String specialist = interim.substring(0, 1).toUpperCase() + interim.substring(1);
		select_option_in_dropdown(dropdownL, specialist);
	}

	public String select_service_for_appointment() {
		List<WebElementFacade> dd = findAll(
				By.cssSelector("form#group-appointment-fields div[id='selectize_services_select_chosen']  > a > span"));
		System.out.println("de click size " + dd.size());
		dd.get(0).click();
		List<WebElementFacade> mList = findAll(By
				.cssSelector("form#group-appointment-fields div[class='chosen-drop'] > ul[class='chosen-results'] li"));
		System.out.println("list size " + mList.size());
		return select_random_option_in_list(mList);

	}

	public void select_service_for_appointment(String serviceName) {
		WebElementFacade dd = find(
				By.cssSelector("form#group-appointment-fields div[id='selectize_services_select_chosen']  > a > span"));
		dd.click();
		List<WebElementFacade> mList = findAll(By
				.cssSelector("form#group-appointment-fields div[class='chosen-drop'] > ul[class='chosen-results'] li"));
		System.out.println("list size " + mList.size());
		select_specific_option_in_list(mList, serviceName);

	}

	public void fill_in_service_duratioin_for_appointment(String serviceDuration) {
		enter(serviceDuration)
				.into(find(By.cssSelector("input[class='input-select-duration app-duration switch-focus']")));
	}

	public String select_random_month_year_for_appointment() {
		System.out.println("in the select random month method ");
		WebElementFacade monthDd = find(By.cssSelector("select[class^='calendis-select app-month switch-focus']"));
		return select_random_option_in_dropdown(monthDd);
	}

	public void select_specific_motnh_year_for_appointment(String month) {
		System.out.println("in the select random month method ");
		List<WebElementFacade> monthDd = findAll(
				By.cssSelector("select[class^='calendis-select app-month switch-focus'] > option"));
		System.out.println("options list size" + monthDd.size());
		select_specific_option_in_list(monthDd, month);
	}

	public String select_random_day_of_week_for_appointment() {
		WebElementFacade dayDd = find(By.cssSelector("select[class^='calendis-select app-day switch-focus']"));
		return select_random_option_in_dropdown(dayDd);
	}

	public void select_specific_day_of_week_for_appointment(String day) {
		List<WebElementFacade> dayDd = findAll(
				By.cssSelector("select[class^='calendis-select app-day switch-focus'] > option"));
		for (WebElementFacade el : dayDd) {
			if (el.getAttribute("value").contains(day)) {
				System.out.println("found day");
				el.click();
				break;
			}
		}
	}

	public String select_random_hour_for_appointment() {
		WebElementFacade hourDd = find(By.cssSelector("select[class^='calendis-select app-hour switch-focus']"));
		return select_random_option_in_dropdown(hourDd);
	}

	public String select_random_hour_for_appointment_in_schedule(int hourMinLimit, int hourMaxLimit) {
		WebElementFacade hourDd = find(By.cssSelector("select[class^='calendis-select app-hour switch-focus']"));
		return select_random_option_in_dropdown(hourDd, hourMinLimit, hourMaxLimit);
	}

	public String select_random_minutes_for_appointment() {
		WebElementFacade minDd = find(By.cssSelector("select[class^='calendis-select app-minute switch-focus']"));
		return select_random_option_in_dropdown(minDd);
	}

	public void click_on_add_another_service_on_appointment_form() {
		click_on_element(find(By.cssSelector(
				"div#appointment-group-modal div[class='modal-dialog appointment-group-modal-dialog'] section#appointment-group-services div[class='addItem-appointment'] > p")));
	}

	public void click_on_add_another_client_on_appointment_form() {
		clickOn(find(By.cssSelector(
				"div#appointment-group-modal div[class='modal-dialog appointment-group-modal-dialog'] section#appointment-group-clients div[class='addItem-client'] > p")));
	}

	public List<WebElementFacade> get_client_cards_appointment_form() {
		return findAll(By.cssSelector(
				"div#appointment-group-modal div[class='modal-dialog appointment-group-modal-dialog'] div#appointment_flow > section[id='appointment-group-clients'] > div > div[id='client-cards'] div[class='client-card-container']"));
	}

	public void expand_client_appointment_form(int index) {
		List<WebElementFacade> clientsList = get_client_cards_appointment_form();
		System.out.println("size is " + clientsList.size());
		clientsList.get(index).click();

	}

	public boolean is_appointment_out_of_staff_interval() {
		boolean isPresent = false;
		List<WebElementFacade> lowerIntervalMessageList = findAll(By.cssSelector(
				"div#appointment-group-modal div[class='modal-dialog appointment-group-modal-dialog'] section#appointment-group-services div[class='warning-message lower-interval-message']"));
		if (lowerIntervalMessageList.size() > 0) {

			if ((lowerIntervalMessageList.get(0).getText().trim().contains(Constants.LOWER_INTERVAL_MESSAGE))
					|| (lowerIntervalMessageList.get(0).getText().trim().contains(Constants.OVERLAP_INTERVAL_MESSAGE))
					|| (lowerIntervalMessageList.get(0).getText().trim()
							.contains(Constants.OVERLAP_INTERVAL_MESSAGE_SECOND))) {
				System.out.println("Error message " + lowerIntervalMessageList.get(0).getText());
				isPresent = true;
				// break;

			}

		}
		return isPresent;
	}

	public void click_outside_card_service_for_validation() {
		scroll_in_view_then_click_on_element(find(By.cssSelector(
				"div#appointment-group-recurrence div[class='section-wrapper'] div[class='section-title']")));
	}

	public void fill_in_client_last_name_for_appointment(String clientName) {

		enter(clientName).into(find(By.cssSelector("form#enroll-client input[name='lastName']")));
	}

	public void select_client_suggestion_from_tooltip() {
		WebElementFacade el = find(By.cssSelector("form#enroll-client input[name='lastName']"));
		el.sendKeys(Keys.ARROW_DOWN);
		el.sendKeys(Keys.TAB);
	}

	public String get_client_last_name_appointment_form() {
		return find(By.cssSelector("form#enroll-client input[name='lastName']")).getAttribute("value");
	}

	public void fill_in_client_first_name_for_appointment(String clientFName) {

		enter(clientFName).into(find(By.cssSelector("form#enroll-client input[name='firstName']")));
	}

	public String get_client_first_name_appointment_form() {
		return find(By.cssSelector("form#enroll-client input[name='firstName']")).getAttribute("value");
	}

	public void fill_in_client_phone_number_for_appointment(String clientPhoneNo) {

		enter(clientPhoneNo).into(find(By.cssSelector("form#enroll-client input[name='phone']")));
	}

	public String get_client_phone_appointment_form() {
		return find(By.cssSelector("form#enroll-client input[name='phone']")).getAttribute("value");
	}

	public void fill_in_client_email_for_appointment(String clientEmail) {

		enter(clientEmail).into(find(By.cssSelector("form#enroll-client input[name='email']")));
	}

	public String get_client_email_appointment_form() {
		return find(By.cssSelector("form#enroll-client input[name='email']")).getAttribute("value");
	}

	public void click_on_save_appointment() {
		click_on_element(find(By.cssSelector(
				"button[class='save-appointment-group save_appointment action_button client_side_btn_xl']")));
	}

	public void confirm_appointment_creation_out_interval_popup() {
		List<WebElementFacade> saveButtonsList = findAll(By.cssSelector("div#appointment-out-of-schedule-modal"));
		if (saveButtonsList.size() >= 1
				&& saveButtonsList.get(0).getAttribute("style").toString().contains("display: block;")) {
			saveButtonsList.get(0)
					.find(By.cssSelector(
							"button[class='confirm-create-group erase_appointment validation_button client_side_btn_xl']"))
					.click();
		}
		waitForPageToLoad();

	}

	public List<WebElementFacade> getAppointmentsList() {
		scroll_in_view_element(find(By.cssSelector(
				"div[class='appointment-staff-slot-container ui-droppable'] > div[class='appointment-ui day-appointment ui-draggable'] div[class='client-preview'] > span")));
		List<WebElementFacade> mList = findAll(By.cssSelector(
				"div[class='appointment-staff-slot-container ui-droppable'] > div[class='appointment-ui day-appointment ui-draggable'] div[class='client-preview'] > span"));
		System.out.println("size of appointment list " + mList.size());
		return mList;
	}

	public List<String> getAppointmentDetailsInCalendar() {

		List<WebElementFacade> appointmentsDetailsList = new ArrayList<WebElementFacade>(getAppointmentsList());
		System.out.println("app nr " + appointmentsDetailsList.size());
		List<String> detailsL = appointmentsDetailsList.stream().map(p -> p.getText().trim())
				.collect(Collectors.toList());
		detailsL.forEach(p -> System.out.println("details" + p));
		return detailsL;
	}

	public WebElementFacade get_appointment_element_with_details(String startTime, String endTime, String serviceName) {
		WebElementFacade elAppointment = null;
		List<WebElementFacade> appointmentsDetailsList = new ArrayList<WebElementFacade>(getAppointmentsList());
		System.out.println("appointments list size: " + appointmentsDetailsList.size());
		for (WebElementFacade el : appointmentsDetailsList) {
			String str = ConfigUtils.removeAccents(el.getText()).toLowerCase();
			System.out.println("str strat time end timne" + str + startTime + endTime + serviceName);
			if (str.contains(startTime) && str.contains(endTime) && str.contains(serviceName.toLowerCase())) {
				// if (str.contains(startTime) && str.contains(endTime) &&
				// str.contains(serviceName.toLowerCase())) {
				if (str.contains(serviceName.toLowerCase())) {
				}
				System.out.println("found appointment" + ConfigUtils.removeAccents(el.getText()));
				elAppointment = el;

				break;
			}
		}
		return elAppointment;
	}

	public void click_on_appointment_with_details(String startTime, String endTime, String serviceName) {
		WebElementFacade appointmentEl = get_appointment_element_with_details(startTime, endTime, serviceName);
		// System.out.println("click on " + appointmentEl.getText());
		click_on_element(appointmentEl);
	}

	public void click_on_service_card_to_edit_appointment_form() {
		click_on_element(find(By.cssSelector(
				"div[id='appointment-group-modal'] section[id='appointment-group-services'] div[id='appointment-cards'] div[class='card-wrapper pull-left clearfix']")));
	}

	public void click_on_collect_payment() {
		click_on_element(
				find(By.cssSelector("button[class='show-collect action_button client_side_btn_m pull-right']")));
	}

	public BigDecimal get_left_amount_to_pay() {
		String amount = find(
				By.cssSelector("div[class='client-accepted-view'] div[class='viewMode-price pull-left'] > strong"))
						.getText();
		BigDecimal amountLeftToPay = ConfigUtils.convertStringToBigDecimalWithTwoDecimals(amount);
		return amountLeftToPay;
	}

	public WebElementFacade get_payment_form_element_for_service(String serviceName) {
		WebElementFacade serviceContainer = null;
		List<WebElementFacade> servicesList = findAll(By.cssSelector("div#payment_form_wrapper > div "));
		for (WebElementFacade el : servicesList) {
			if (el.find(
					By.cssSelector("div > div[class='payment-form-service'] > span[class='payment-form-service-name']"))
					.getText().toLowerCase().contains(serviceName.toLowerCase())) {
				System.out.println("found service container elem");
				serviceContainer = el;
				break;
			}
		}
		return serviceContainer;
	}

	public void select_voucher_code_in_appointment_form_for_service(WebElementFacade servicePaymentElement,
			String voucherName) {
		WebElementFacade dropdown = servicePaymentElement.find(By.cssSelector("select#discount-partener-voucher"));
		scroll_in_view_element(dropdown);
		select_option_in_dropdown(dropdown, ConfigUtils.capitalizeFirstLetter(voucherName));
	}

	public void fill_in_discount_value_for_voucher_payment_form(WebElementFacade servicePaymentElement,
			String discountValue) {
		// WebElementFacade element =
		// servicePaymentElement.find(By.cssSelector("input#payment-discount-value"));
		waitForElementEnabled(servicePaymentElement.find(By.cssSelector("input#payment-discount-value")));
		scroll_in_view_element(servicePaymentElement.find(By.cssSelector("input#payment-discount-value")));
		enter(discountValue).into(servicePaymentElement.find(By.cssSelector("input#payment-discount-value")));
	}

	public void fill_in_additional_cost(WebElementFacade servicePaymentElement, String cost) {
		enter(cost).into(servicePaymentElement.find(By.cssSelector("input#payment-additional-amount")));
	}

	public BigDecimal get_amount_to_pay_for_service(WebElementFacade servicePaymentElement) {
		String price = servicePaymentElement
				.find(By.cssSelector("div[class='amount-to-pay-for-service'] > strong > span")).getText();
		String str = price.replace(" RON", "");
		System.out.println("found amount for service " + str);
		return ConfigUtils.convertStringToBigDecimalWithTwoDecimals(str);
	}

	public BigDecimal get_total_amount_to_pay_for_single_service() {
		String price = find(By.cssSelector("div[class='payment-form-total-amount'] > strong")).getText();
		String str = price.replace(" RON", "");
		return ConfigUtils.convertStringToBigDecimalWithTwoDecimals(str);
	}

	public BigDecimal get_amount_left_to_pay_for_single_service() {
		String price = find(By.cssSelector(
				"div[class='payment-form-payment-amount-left-to-pay'] > div[class='payment-form-payment-amount'] > strong"))
						.getText();
		String str = price.replace(" RON", "");
		System.out.println("found amount for service " + str);
		return ConfigUtils.convertStringToBigDecimalWithTwoDecimals(str);
	}

	public BigDecimal get_amount_left_to_pay_on_top() {
		String price = find(
				By.cssSelector("div[class='no-collect clearfix']  div[class='viewMode-price pull-left'] > strong"))
						.getText();
		String str = price.replace(" RON", "");
		System.out.println("foundamount_left_to_pay_on_top " + str);
		return ConfigUtils.convertStringToBigDecimalWithTwoDecimals(str);
	}

	public BigDecimal get_total_amount_on_card_payment_bottom() {
		String price = find(By.id("group-total-price")).getText();
		String str = price.replace(" RON", "");
		System.out.println("found amount_on_card_payment_bottom" + str);
		return ConfigUtils.convertStringToBigDecimalWithTwoDecimals(str);
	}

	public BigDecimal get_payment_paid_in_fieldbox() {
		String price = find(By.cssSelector("input#payment-paid-value")).getAttribute("value");
		System.out.println("get_payment_paid_in_fieldbox" + price);
		return ConfigUtils.convertStringToBigDecimalWithTwoDecimals(price);
	}

	public void fill_in_partial_amount_to_pay(String partialAmount) {
		WebElementFacade element = find(By.cssSelector("input#payment-paid-value"));
		enter(partialAmount).into(element);
	}

	public void click_on_collect_button_on_client_card() {
		scroll_in_view_then_click_on_element(find(By.cssSelector(
				"div[id='appointment-group-modal'] section[id='appointment-group-clients'] div[id='client-cards'] button[class='payment-submit action_button client_side_btn_m']")));
	}

	public void click_on_finalize_button() {
		click_on_element(find(By.cssSelector(
				"button[class='save-appointment-group save_appointment action_button client_side_btn_xl']")));
		waitForPageToLoad();
	}

	public void click_on_payment_history() {
		click_on_element(find(By.cssSelector(
				"div[id='appointment-group-modal'] section[id='appointment-group-clients'] div[id='client-cards'] button[class='client_side_btn_m payment-history pull-right']")));
	}

	public BigDecimal get_total_price_on_appointment_form() {
		String price = find(By.cssSelector("span#group-total-price")).getText().trim();
		String str = price.replace(" RON", "");
		return ConfigUtils.convertStringToBigDecimalWithTwoDecimals(str);
	}

	public String get_last_payment_in_payment_history() {
		return findAll(By.cssSelector(
				"div[id='appointment-group-modal'] section[id='appointment-group-clients'] div[id='client-cards'] ul[class='payments-list'] li"))
						.get(0).getText().trim();
	}

	public void click_on_client_card_to_edit_appointment_form() {
		click_on_element(find(By.cssSelector(
				"div[id='appointment-group-modal'] section[id='appointment-group-clients'] div[id='client-cards'] div[class='card-wrapper pull-left']")));
	}

	public Optional<String> getAppointmentsDetailsFor(String startTime, String endTime, String serviceName) {
		getAppointmentDetailsInCalendar().stream().forEach(p->System.out.println(p));
		return getAppointmentDetailsInCalendar().stream().filter(item -> item.contains(startTime)
				&& item.contains(endTime) && item.toLowerCase().contains(serviceName.toLowerCase())).findFirst();

	}

	public void select_domain_calendar_left_menu(String domainName) {
		WebElementFacade element = getDomainWebElementContainer(domainName);
		String prop = element.find(By.tagName("a")).getAttribute("class");
		if (prop.contains("jstree-clicked")) {
			// do nothing

		} else {
			scroll_in_view_then_click_on_element(element.find(By.tagName("i")));
		}

	}

	public WebElementFacade getDomainWebElementContainer(String domainName) {
		WebElementFacade domainEl = null;
		List<WebElementFacade> domainList = findAll(By.cssSelector("ul[class='jstree-container-ul'] > li"));

		for (WebElementFacade elem : domainList) {
			WebElementFacade element = elem.find(By.tagName("i"));
			System.out.println("tag name" + element.getAttribute("data-original-title").trim());
			if (element.getAttribute("data-original-title").trim().toLowerCase().contains(domainName.toLowerCase())) {
				System.out.println("found domain " + element.getAttribute("data-original-title").trim().toLowerCase());
				domainEl = elem;
				break;
			}
		}
		return domainEl;
	}

	public void select_service_calendar_left_menu(String domainName, String serviceName) {
		WebElementFacade domainEl = getDomainWebElementContainer(domainName);
		List<WebElementFacade> servicesList = domainEl.thenFindAll(By.cssSelector("ul > li"));
		System.out.println("services list" + servicesList.size());
		for (WebElementFacade el : servicesList) {
			WebElementFacade elem = el.find(By.cssSelector("a > div > span"));
			WebElementFacade indicator = el.find(By.tagName("a"));
			String serviceText = elem.getText().trim().toLowerCase();
			System.out.println("service now is " + serviceText);
			if (serviceName.toLowerCase().contains(serviceText)) {
				System.out.println("In left menu found service" + elem.getText().trim().toLowerCase());
				if (!indicator.getAttribute("class").contains("jstree-clicked")) {
					focusOnElement(elem);
					scroll_in_view_then_click_on_element(elem);
					break;
				}
			} else
				System.out.println("Not matching services " + serviceText + " with " + serviceName.toLowerCase());
		}

	}

	public void select_service_packet_calendar_left_menu(String packetName) {
		WebElementFacade domainEl = find(By.cssSelector("ul[class='jstree-container-ul'] > li:last-child"));
		domainEl.click();
		List<WebElementFacade> servicesList = findAll(
				By.cssSelector("ul[class='jstree-container-ul'] > li:last-child > ul > li"));
		System.out.println("packet list" + servicesList.size());
		for (WebElementFacade el : servicesList) {
			WebElementFacade elem = el.find(By.cssSelector("a > div > span"));
			WebElementFacade indicator = el.find(By.tagName("a"));
			String serviceText = elem.getText().trim().toLowerCase();
			System.out.println("service now is " + serviceText);
			if (packetName.toLowerCase().contains(serviceText)) {
				System.out.println("In left menu found packet service" + elem.getText().trim().toLowerCase());
				if (!indicator.getAttribute("class").contains("jstree-clicked")) {
					focusOnElement(elem);
					scroll_in_view_then_click_on_element(elem);
					break;
				}
			} else
				System.out.println("Not matching packet " + serviceText + " with " + packetName.toLowerCase());
		}

	}

	public void select_specialist_calendar_left_menu(String specialistName) {
		System.out.println("Specialist name param is " + specialistName);
		List<WebElementFacade> specialistsList = findAll(By.cssSelector(
				"section[class='pushmenu-push pushmenu-push-toright'] div[id='staff-accordion']  > div > div[class^='sidebar_filter_item sidebar_filter_bundle']"));
		System.out.println("size of specialist list" + specialistsList.size());
		if (!specialistsList.get(0).find(By.cssSelector("span[class='sidebar_item_counter staff-selected-count']"))
				.getText().startsWith("0")) {
			scroll_in_view_then_click_on_element(specialistsList.get(0).find(By.tagName("label")));
		}
		for (WebElementFacade el : specialistsList) {
			WebElementFacade element = el.find(By.tagName("span"));
			String specialistText = ConfigUtils.removeAccents(element.getText().trim().toLowerCase());
			System.out.println("this isssss in span specialist " + specialistText + " and specialist name is "
					+ specialistName.toLowerCase());
			if (specialistText.contains(specialistName.toLowerCase())) {
				System.out.println("in left menu found specialist" + specialistText);
				focusOnElement(el.find(By.tagName("label")));
				scroll_in_view_then_click_on_element(el.find(By.tagName("label")));
				break;
			} else
				System.out.println("Not found compared " + specialistText + " with " + specialistName.toLowerCase());
		}
	}
}
