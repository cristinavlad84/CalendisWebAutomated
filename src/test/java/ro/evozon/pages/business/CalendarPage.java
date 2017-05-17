package ro.evozon.pages.business;

import java.text.DecimalFormat;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
		System.out.println("size is " + currentMonthL.size());
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
		scroll_in_view_then_click_on_element(
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

	public LocalDate parseTargetDate(String targetDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy d H mm", Locale.ENGLISH);
		LocalDate targetDateF = LocalDate.parse(targetDate, formatter);
		System.out.println("target date is " + targetDateF);
		return targetDateF;
	}

	public LocalDate parseCurrentDate(String currentDate) {

		DateTimeFormatter formatterMonthYear = DateTimeFormatter.ofPattern("MMM yyyy d", Locale.ENGLISH);
		currentDate = ConfigUtils.formatMonthString(currentDate);
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
			diff--;
		}
		// navigate to day of month
		List<WebElementFacade> dayList = findAll(By.cssSelector("div[class='datepicker-days'] > table > tbody td"));
		for (WebElementFacade day : dayList) {
			if (day.getText().trim().contentEquals(Integer.toString(dayOfMonth))) {
				scroll_in_view_then_click_on_element(day);
				break;
			}
		}
	}

	public void click_on_add_quick_appointment() {
		WebElementFacade el = find(By.id("quick-appointment-trigger"));
		scroll_in_view_then_click_on_element(el);
	}

	public String select_domain_for_appointment() {
		WebElementFacade dropdown = find(
				By.cssSelector("form#group-appointment-fields > div:nth-of-type(1)  > select[name='domain']"));
		return select_random_option_in_dropdown(dropdown);
	}

	public void select_domain_for_appointment(String domain) {
		WebElementFacade dropdown = find(
				By.cssSelector("form#group-appointment-fields > div:nth-of-type(1)  > select[name='domain']"));
		dropdown.selectByVisibleText(domain);
	}

	public String select_specialist_for_appointment() {
		List<WebElementFacade> dropdownL = findAll(
				By.cssSelector("form#group-appointment-fields > div:nth-of-type(2)  > select[name='staff']"));
		return select_random_option_in_list(dropdownL);
	}

	public void select_specialist_for_appointment(String specialistName) {
		List<WebElementFacade> dropdownL = findAll(
				By.cssSelector("form#group-appointment-fields > div:nth-of-type(2)  > select[name='staff'] > option"));
		select_specific_option_in_list(dropdownL, specialistName);
	}

	public String select_service_for_appointment() {
		WebElementFacade dd = find(
				By.cssSelector("form#group-appointment-fields div[id='selectize_services_select_chosen']  > a > span"));
		dd.click();
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
		WebElementFacade monthDd = find(By.cssSelector("select[class^='calendis-select app-month switch-focus']"));
		return select_random_option_in_dropdown(monthDd);
	}

	public String select_random_day_of_week_for_appointment() {
		WebElementFacade dayDd = find(By.cssSelector("select[class^='calendis-select app-day switch-focus']"));
		return select_random_option_in_dropdown(dayDd);
	}

	public String select_random_hour_for_appointment() {
		WebElementFacade hourDd = find(By.cssSelector("select[class^='calendis-select app-hour switch-focus']"));
		return select_random_option_in_dropdown(hourDd);
	}

	public String select_random_minutes_for_appointment() {
		WebElementFacade minDd = find(By.cssSelector("select[class^='calendis-select app-minute switch-focus']"));
		return select_random_option_in_dropdown(minDd);
	}

	public void click_on_add_another_service_on_appointment_form() {
		click_on_element(find(By.cssSelector(
				"div#appointment-group-modal div[class='modal-dialog appointment-group-modal-dialog'] section#appointment-group-services div[class='addItem-appointment'] > p")));
	}

	public boolean is_appointment_out_of_staff_interval() {
		boolean isPresent = false;
		List<WebElementFacade> lowerIntervalMessageList = findAll(By.cssSelector(
				"div#appointment-group-modal div[class='modal-dialog appointment-group-modal-dialog'] section#appointment-group-services div[class='warning-message lower-interval-message']"));
		if (lowerIntervalMessageList.size() > 0) {

			if (lowerIntervalMessageList.get(0).getText().trim().contains(Constants.LOWER_INTERVAL_MESSAGE)
					|| lowerIntervalMessageList.get(0).getText().trim().contains(Constants.OVERLAP_INTERVAL_MESSAGE)) {
				System.out.println("Error message " + lowerIntervalMessageList.get(0).getText());
				isPresent = true;
				// break;

			}

		}
		return isPresent;
	}

	public void click_outside_card_service_for_validation() {
		scroll_in_view_then_click_on_element(find(By.cssSelector(
				"div#appointment-group-modal div[class='modal-dialog appointment-group-modal-dialog'] div[class='section-title']")));
	}

	public void fill_in_client_last_name_for_appointment(String clientName) {

		enter(clientName).into(find(By.cssSelector("form#enroll-client input[name='lastName']")));
	}

	public void fill_in_client_first_name_for_appointment(String clientFName) {

		enter(clientFName).into(find(By.cssSelector("form#enroll-client input[name='firstName']")));
	}

	public void fill_in_client_phone_number_for_appointment(String clientPhoneNo) {

		enter(clientPhoneNo).into(find(By.cssSelector("form#enroll-client input[name='phone']")));
	}

	public void fill_in_client_email_for_appointment(String clientEmail) {

		enter(clientEmail).into(find(By.cssSelector("form#enroll-client input[name='email']")));
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
		return findAll(By.cssSelector(
				"div[class='appointment-staff-slot-container ui-droppable'] > div[class='appointment-ui day-appointment ui-draggable'] div[class='client-preview'] > span"));
	}

	public List<String> getAppointmentDetailsInCalendar() {
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<WebElementFacade> appointmentsDetailsList = new ArrayList<WebElementFacade>(getAppointmentsList());
		System.out.println("app nr " + appointmentsDetailsList.size());
		List<String> detailsL = appointmentsDetailsList.stream().map(p -> p.getText().trim())
				.collect(Collectors.toList());
		detailsL.forEach(p -> System.out.println("details" + p));
		return detailsL;
	}

	public Optional<String> getAppointmentsDetailsFor(String startTime, String endTime, String serviceName) {
		return getAppointmentDetailsInCalendar().stream().filter(item -> item.contains(startTime)
				&& item.contains(endTime) && item.toLowerCase().contains(serviceName.toLowerCase())).findFirst();

	}

	public void select_domain_calendar_left_menu(String domainName) {
		WebElementFacade element = getDomainWebElementContainer(domainName);
		String prop = element.find(By.tagName("a")).getAttribute("class");
		if (prop.contains("jstree-clicked")) {
			// do nothing

		} else {
			element.find(By.tagName("i")).click();
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
		WebElement serviceEl = null;
		WebElementFacade domainEl = getDomainWebElementContainer(domainName);
		List<WebElementFacade> servicesList = domainEl.thenFindAll(By.cssSelector("ul > li"));
		for (WebElementFacade el : servicesList) {
			WebElement elem = el.find(By.cssSelector("a > div > span"));
			WebElement indicator = el.find(By.tagName("a"));
			if (elem.getText().trim().toLowerCase().contains(serviceName.toLowerCase())) {
				if (!indicator.getAttribute("class").contains("jstree-clicked")) {
					elem.click();
					break;
				}
			}
		}

	}

	public void select_specialist_calendar_left_menu(String specialistName) {
		List<WebElementFacade> specialistsList = findAll(
				By.cssSelector("div#staff-accordion > div[id^='sidebar-staff-conainer']"));
		for (WebElementFacade el : specialistsList) {
			WebElementFacade element = el.find(By.tagName("span"));
			if (element.getText().trim().toLowerCase().contains(specialistName.toLowerCase())) {
				el.find(By.tagName("label")).click();
				break;
			}
		}
	}
}
