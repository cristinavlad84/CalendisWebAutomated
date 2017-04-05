package ro.evozon.pages.business;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElements;
import org.openqa.selenium.support.ui.Select;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import ro.evozon.AbstractPage;
import ro.evozon.tools.FieldGenerators;

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
		WebElementFacade wizardText = popupHoover.find(By
				.cssSelector("p:nth-of-type(1)"));
		return element(wizardText).getText();
	}

	public void fill_in_business_adress(String businessAddress) {
		enter(businessAddress).into(businessAddressField);
	}

	public String select_random_business_county() {

		return select_random_option_in_dropdown(regionSelector);

	}

	public String select_random_city() {
		return select_random_option_in_dropdown(citySelector);
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

	public List<String> select_day_of_week_business() {
		return select_day_of_week_from_dropdown("div[id='wizard-schedule']",
				"label[for^='checkbox']");
	}

	public List<String> select_day_of_week_staff() {
		return select_day_of_week_from_dropdown(
				"div[id='wizard-staff-schedule']", "label[for^='checkbox']");
	}

	public List<String> select_day_of_week_from_dropdown(
			String containerLocator, String locator) {
		List<String> checkedDays = new ArrayList<String>();
		List<WebElementFacade> dayOfWeekList = find(
				By.cssSelector(containerLocator)).thenFindAll(
				By.cssSelector(locator));
		int max = dayOfWeekList.size();
		System.out.println("days found " + max);
		int noOfDaysToBeChecked = FieldGenerators.getRandomIntegerBetween(1,
				max);

		while (noOfDaysToBeChecked > 0) {

			int random = getRandomIntegerBetween(0, max - 1);
			System.out.println("max is" + random);
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			WebElement element = dayOfWeekList.get(random);

			jse.executeScript("arguments[0].click();", element);
			// dayOfWeekList.get(random).click();
			checkedDays.add(dayOfWeekList.get(random)
					.find(By.cssSelector("span")).getText());

			noOfDaysToBeChecked--;
		}
		return checkedDays;

	}

	public void click_on_save_location() {
		clickOn(find(By.id("wizard-save-location")));
	}

	public void fill_in_service_name(String service) {
		enter(service).into(find(By.id("service_name")).waitUntilVisible());
	}

	public void select_service_duration() {
		select_random_option_in_dropdown(find(By.id("pick-duration"))
				.waitUntilVisible());
	}

	public void select_service_max_persons() {
		select_random_option_in_dropdown(find(By.id("pick-max-users"))
				.waitUntilVisible());
	}

	public void fill_in_service_price(String price) {
		enter(price.toString()).into(
				find(By.id("wizard-service-price")).waitUntilVisible());
	}

	public void save_service_popup_content() {

		clickOn(find(By.id("wizard-save-service")).waitUntilVisible());
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
		clickOn(find(By.id("go-to-staff-schedule")));
	}

	public void click_on_save_staff_schedule_button() {
		clickOn(find(By.id("wizard-save-staff")).waitUntilEnabled());
	}

}