package ro.evozon.pages.business;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import ro.evozon.AbstractPage;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.models.DaysOfWeek;

public class BusinessWizardPage extends AbstractPage {

	@FindBy(css = "div[class='popover fade right-placement in'] > div.popover-content > div.wizard-walkthrough-content")
	private WebElementFacade popupHoover;

	@FindBy(id = "wizard-location")
	private WebElementFacade locationWizardBox;

	@FindBy(id = "wizard-business-address")
	private WebElementFacade businessAddressField;

	@FindBy(id = "wizard-select-region")
	private WebElementFacade regionSelector;

	@FindBy(id = "wizard-select-city")
	private WebElementFacade citySelector;

	@FindBy(id = "wizard-phone")
	private WebElementFacade businessPhoneNo;

	@FindBy(id = "wizard-location-name")
	private WebElementFacade businessLocationName;

	@FindBy(id = "go-to-schedule")
	private WebElementFacade goToSchedule;

	@WhenPageOpens
	public void waitUntilElementsAppear() {
		waitForPageToLoad();
		// element(popupHoover).waitUntilVisible();
		// element(locationWizardBox).waitUntilVisible();
		// element(regionSelector).waitUntilVisible();
		// element(citySelector).waitUntilVisible();
	}

	public String get_text_from_welcome_wizard() {
		WebElementFacade wizardText = popupHoover.find(By.cssSelector("p:nth-of-type(1)"));
		return element(wizardText).getText();
	}

	public void fill_in_business_adress(String businessAddress) {
		enter(businessAddress).into(businessAddressField);
	}

	public String select_random_business_county() {

		return select_random_option_in_dropdown(regionSelector);

	}

	public void select_specific_business_county(String county) {
		List<WebElementFacade> mList = findAll(By.cssSelector("select#wizard-select-region > option"));
		select_specific_option_in_list(mList, county);

	}

	public String select_random_city() {
		return select_random_option_in_dropdown(citySelector);
	}

	public void select_specific_city(String city) {
		List<WebElementFacade> mList = findAll(By.cssSelector("select#wizard-select-city > option"));
		select_specific_option_in_list(mList, city);
	}

	public void fill_in_business_phone_no(String phoneNo) {
		enter(phoneNo).into(businessPhoneNo);
	}

	public void fill_in_business_location_name(String busLocationName) {
		enter(busLocationName).into(businessLocationName);

	}

	public void click_on_go_to_schedule() {
		clickOn(goToSchedule);
	}

	public void schedule_popup_should_appear() {
		find(By.cssSelector("div[class='check-schedule']")).shouldBeVisible();
	}

	public void domain_field_should_appear() {
		find(By.id("wizard-input-domain")).shouldBeVisible();
	}

	public void fill_in_domain(String businessDomain) {
		enter(businessDomain).into(find(By.id("wizard-input-domain")));
	}

	public void click_on_add_domain() {
		clickOn(find(By.id("new-domain-submit")));
	}

	public void submit_domain_form() {
		clickOn(find(By.id("wizard-save-domains")));
	}

	public Integer getRandomIntegerBetween(int min, int max) {
		SecureRandom random = new SecureRandom();
		int result = random.nextInt(max + 1);
		return (result >= min ? result : result + min);
	}

	public void select_day_of_week_business() {
		select_day_of_week_schedule("div[id='wizard-schedule']", "label[for^='checkbox']");
	}

	public void select_day_of_week_business(int position) {

		select_specific_day_of_week("div[id='wizard-schedule']", "label[for^='checkbox']", position);
	}

	public void select_day_of_week_staff() {
		select_day_of_week_schedule("div[id='wizard-staff-schedule']", "label[for^='checkbox']");
	}

	public void select_day_of_week_staff(int position) {
		select_specific_day_of_week("div[id='wizard-staff-schedule']", "label[for^='checkbox']", position);
	}

	public void select_startHour(String startHour, int position) {
		List<WebElementFacade> daysList = findAll(
				By.cssSelector("div[class*='left-select small-select-new-active']:nth-of-type(1)"));
		List<WebElementFacade> mList = daysList.get(daysList.size() - 1)
				.thenFindAll(By.cssSelector("select:nth-child(1) > option"));
		select_specific_option_in_list(mList, startHour);
	}

	public void select_endHour(String endHour, int position) {
		List<WebElementFacade> daysList = findAll(
				By.cssSelector("div[class*='right-select small-select-new-active']:nth-of-type(1)"));
		List<WebElementFacade> mList = daysList.get(daysList.size() - 1)
				.thenFindAll(By.cssSelector("select:nth-child(1) > option"));
		select_specific_option_in_list(mList, endHour);
	}

	public void click_on_save_location() {
		clickOn(find(By.id("wizard-save-location")));
	}

	public void fill_in_service_name(String service) {
		enter(service).into(find(By.id("service_name")).waitUntilVisible());
	}

	public String select_service_duration() {
		return select_random_option_in_dropdown(find(By.id("pick-duration")));
	}

	public void select_service_max_persons() {
		select_random_option_in_dropdown(find(By.id("pick-max-users")).waitUntilVisible());
	}

	public void fill_in_max_persons_per_service(String personsNo) {

		clickOn(find(By.cssSelector("select[id='pick-max-users'] > option[data-other='1']")).waitUntilVisible());
		enter(personsNo).into(find(By.cssSelector("input[id='pick-max-users-input']")));
	}

	public void fill_in_service_price(String price) {
		enter(price.toString()).into(find(By.id("wizard-service-price")).waitUntilVisible());
	}

	public void fill_in_duration_per_service(String duration) {
		clickOn(find(By.cssSelector("select[id='pick-duration'] > option[data-other='1']")));
		enter(duration).into(find(By.cssSelector("input[id='pick-duration-input']")));
	}

	public void save_service_popup_content() {

		// clickOn(find(By.id("wizard-save-service")).waitUntilVisible());
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement element = find(By.id("wizard-save-service"));
		jse.executeScript("arguments[0].click();", element);
	}

	public void fill_in_staff_name(String name) {
		enter(name).into(find(By.id("staff_name")).waitUntilVisible());
	}

	public void fill_in_staff_email(String email) {
		enter(email).into(find(By.id("staff_email")).waitUntilVisible());
	}

	public void fill_in_staff_phone(String phone) {
		enter(phone).into(find(By.id("staff_phone")).waitUntilVisible());
	}

	public void click_on_set_staff_schedule_button() {

		// clickOn(find(By.id("go-to-staff-schedule")).waitUntilEnabled());
//		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement element = find(By.id("go-to-staff-schedule"));
		element.click();
//		jse.executeScript("arguments[0].click();", element);
	}

	public void click_on_save_staff_schedule_button() {
		// clickOn(find(By.id("wizard-save-staff")).waitUntilEnabled());
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement element = find(By.id("wizard-save-staff"));
		element.click();
		// jse.executeScript("arguments[0].click();", element);
	}

	public String getTextFromWizardOverlay() {
		return ConfigUtils.removeAccents(
				find(By.cssSelector("div[class='wizard-walkthrough-content'] > p:nth-of-type(1)")).getText().trim());
	}

	public void dismiss_wizard_modal() {
		find(By.id("end-wizard-walkthrough")).click();
	}

}