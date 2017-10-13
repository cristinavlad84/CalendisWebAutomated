package ro.evozon.pages.business;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import ro.evozon.AbstractPage;

public class NewBusinessAccountPage extends AbstractPage {

	@FindBy(css = "select#register-category")
	private WebElementFacade businessCategoryDropdown;

	@FindBy(css = "input[id='register-name']")
	private WebElementFacade businessNameField;

	@FindBy(css = "input[id='register-email']")
	private WebElementFacade businessEmailField;

	@FindBy(css = "input[id='register-phone']")
	private WebElementFacade businessPhoneField;

	@FindBy(css = "button[class='action-btn medium-btn register-btn']")
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
		WebElementFacade el = find(By.cssSelector("select#register-category"));
		select_random_option_in_dropdown(el);

	}
	public void select_business_category(String bCategory) {
		List<WebElementFacade> mList = findAll(By.cssSelector("select#register-category > option"));
		select_specific_option_in_list(mList, bCategory);

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
		businessRegisterButton.waitUntilClickable();
		scroll_in_view_then_click_on_element(businessRegisterButton);
	}

	public void success_message_should_be_visible() {
		find(By.cssSelector ("div[class='section-content']")).waitUntilVisible();
	}

	public String get_email_from_success_message() {
		String email = find(By.id("business-name")).getText();

		return email;
	}
	public String get_standard_text_from_success_message() {
		String text = find(By.cssSelector("div[class='section-content'] p:nth-child(1)")).getText().trim();

		return text;
	}
	public void fill_in_password(String paswd) {
		find(By.cssSelector("input[placeholder='Parola']")).waitUntilVisible();
		enter(paswd).into(find(By.cssSelector("input[placeholder='Parola']")));
	}

	public void fill_in_repeat_password(String paswd) {
		find(By.cssSelector("input[placeholder='Repetare Parola']")).waitUntilVisible();
		enter(paswd).into(find(By.cssSelector("input[placeholder='Repetare Parola']")));
	}

	public void checkTermsAndConditionBox() {
		find(By.cssSelector("label[for='checkboxFiveInput']")).waitUntilVisible();

		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement element = getDriver().findElement(By.cssSelector("label[for='checkboxFiveInput']"));
		jse.executeScript("arguments[0].click();", element);

	}

	public void click_on_ok_button() {
		find(By.id("submit-register")).waitUntilVisible();
		clickOn(find(By.id("submit-register")));
	}
}