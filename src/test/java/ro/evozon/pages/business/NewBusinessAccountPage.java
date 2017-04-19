package ro.evozon.pages.business;



import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


import ro.evozon.AbstractPage;


public class NewBusinessAccountPage extends AbstractPage {

	@FindBy(id = "pick-category")
	private WebElementFacade businessCategoryDropdown;

	@FindBy(css = "input[placeholder='Nume Afacere']")
	private WebElementFacade businessNameField;

	@FindBy(css = "input[placeholder='E-Mail Afacere']")
	private WebElementFacade businessEmailField;

	@FindBy(css = "input[name='phone']")
	private WebElementFacade businessPhoneField;

	@FindBy(id = "sign_up_submit_button")
	private WebElementFacade businessRegisterButton;

	@WhenPageOpens
	public void waitUntilDropdownLoads() {
		waitForPageToLoad();
		element(businessCategoryDropdown).waitUntilVisible();
	}

	/*
	 * returns the index of selected option in dropdown list
	 */

	public void select_random_business_category() {
		select_random_option_in_dropdown(businessCategoryDropdown);

	}

	public void fill_in_business_name(String businessName) {
		enter(businessName).into(businessNameField);
	}

	public void fill_in_business_email(String businessEmail) {
		enter(businessEmail).into(businessEmailField);
	}

	public void fill_in_business_phone(String businessPhone) {
		enter(businessPhone).into(businessPhoneField);
	}

	public void click_on_register() {
		clickOn(businessRegisterButton.waitUntilClickable());
	}

	public void success_message_should_be_visible() {
		shouldBeVisible(find(By.id("subscribe-message")));
	}

	public String get_text_from_success_message() {
		return find(By.cssSelector("div#subscribe-message > strong")).getText();
	}

	public void fill_in_password(String paswd) {
		find(By.cssSelector("input[placeholder='Parola']")).waitUntilVisible();
		enter(paswd).into(find(By.cssSelector("input[placeholder='Parola']")));
	}

	public void fill_in_repeat_password(String paswd) {
		find(By.cssSelector("input[placeholder='Repetare Parola']"))
				.waitUntilVisible();
		enter(paswd).into(
				find(By.cssSelector("input[placeholder='Repetare Parola']")));
	}

	public void checkTermsAndConditionBox() {
		element(find(By.cssSelector("label[for='checkboxFiveInput']")))
				.waitUntilVisible();

		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement element = getDriver().findElement(
				By.cssSelector("label[for='checkboxFiveInput']"));
		jse.executeScript("arguments[0].click();", element);

	}

	public void click_on_ok_button() {
		find(By.id("submit-register")).waitUntilVisible();
		clickOn(find(By.id("submit-register")));
	}
}