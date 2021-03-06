package ro.evozon.pages.client;

import java.util.List;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;

import org.openqa.selenium.By;

import ro.evozon.AbstractPage;
import ro.evozon.tools.ConfigUtils;

public class NewClientAccountPage extends AbstractPage {

	@FindBy(id = "signUpNow")
	private WebElementFacade createNewAccountLink;

	@WhenPageOpens
	public void waitUntilLinkAppears() {
		element(createNewAccountLink).waitUntilVisible();
	}

	public void click_on_link() {
		clickOn(createNewAccountLink.waitUntilClickable());
	}

	public void fill_in_client_email_address(String emailAddress) {
		enter(emailAddress).into(find(By.id("forEmail")).waitUntilVisible());
	}

	public void fill_in_client_passwod(String password) {
		enter(password).into(find(By.id("forPassword")).waitUntilVisible());
	}

	public void wait_for_fields_to_load_in_modal() {
		waitForAnyRenderedElementOf(By.id("forLastName"),
				By.id("forFirstName"), By.id("forEmailReg"));
	}

	public void fill_in_user_lastName(String lastName) {
		// WebElementFacade firstNameField = find(By.id("forLastName"));
		enter(lastName).into(find(By.id("forLastName")));

	}

	public void fill_in_user_firstName(String firstName) {
		enter(firstName).into(find(By.id("forFirstName")));
	}

	public void fill_in_user_Email(String email) {
		enter(email).into(find(By.id("forEmailReg")));
	}

	public void fill_in_user_PhoneNo(String phone) {
		enter(phone).into(find(By.id("forNumber")));
	}

	public void click_on_login_button() {

		List<WebElementFacade> mList = findAll(By
				.cssSelector("button[class='connect validation_button  client_side_btn_fluid ']"));
		System.out.println("Am gasit % butoane " + mList.size());
		for (WebElementFacade elem : mList) {
			if (ConfigUtils.removeAccents(elem.getText()).contentEquals(
					"CONECTEAZA-TE")) {
				clickOn(elem);
				break;
			}
		}

	}

	public void click_on_facebook_login_button() {
		clickOn(find(By.id("loginFacebook")).waitUntilVisible());
	}

	public void click_on_create_account_button() {
		clickOn(find(By.id("createAccount")).waitUntilClickable());
	}

	public void click_on_activate_account_button() {
		clickOn(find(By.id("activateAccount")).waitUntilEnabled());
		waitForWithRefresh();
	}

	public void click_on_create_personal_acount() {
		clickOn(find(
				By.cssSelector("div[id='login-and-create-client'] > form > button[type='submit']"))
				.waitUntilClickable());
	}

	public void fill_in_email_for_existing_account(String bEmail) {
		enter(bEmail).into(find(By.id("createEmail")));
	}

	public void fill_in_password_for_existing_acount(String bPassword) {
		enter(bPassword).into(find(By.id("createPassword")));
	}

	public void wait_for_success_message_account_created_load_in_modal() {

		// WebElementFacade el =
		// find((By.cssSelector("div#login-wrapper > div[class='calendis-login-steps-container'] > div[class^='accountCreated']")));
		waitForAnyRenderedElementOf(
				By.cssSelector("div[class^='accountCreated']"),
				By.id("successOkBtn"));

	}

	public void wait_for_success_message_account_activated_load_in_modal() {

		// WebElementFacade el =
		// find((By.cssSelector("div#login-wrapper > div[class='calendis-login-steps-container'] > div[class^='accountCreated']")));
		waitForAnyRenderedElementOf(
				By.cssSelector("div[class^='accountActivated']"),
				By.id("successOkBtn"));

	}

	public void should_see_select_account_option_popup() {
		find(By.id("accountsList")).shouldBeVisible();
	}

	public void should_see_client_account_option_listed_in_popup() {
		find(
				By.cssSelector("div[class^='chooseAccount customer-registered-account']"))
				.shouldBeVisible();
	}

	public void should_see_business_account_option_listed_in_popup() {
		find(By.cssSelector("div[class^='chooseAccount col-xs-12 col-md-6']"))
				.shouldBeVisible();
	}

	public String getTextFromSelectAccountPopup() {

		return (find(
				By.cssSelector("div#accountsList> div:nth-of-type(1) > p:nth-of-type(1)"))
				.getText().trim());
	}

	public String getTextForClientAccountSelectAccountPopup() {

		return (find(
				By.cssSelector("div[class^='chooseAccount customer-registered-account'] > p"))
				.getText().trim());
	}

	public String getTextForBusinessAccountSelectAccountPopup() {

		return (find(
				By.cssSelector("div[class^='chooseAccount col-xs-12 col-md-6'] > p"))
				.getText().replaceAll("[\n]", "").replaceAll("\\s", ""));
	}

	public void click_on_login_client_account() {
		clickOn(find(
				By.cssSelector("div[class^='chooseAccount col-xs-12 col-md-6'] > button:nth-of-type(1)"))
				.waitUntilClickable());
	}

	public void wait_for_warning_message_load() {
		find(By.cssSelector("div[class^='finish-client-account-creation']"))
				.waitUntilVisible();
	}

	public String get_warning_message_text() {
		return find(
				By.cssSelector("div[class^='finish-client-account-creation'] > div:nth-child(2) > p.existingAccount"))
				.getText();
	}

	public String get_success_message_account_created_text() {
		return find(
				By.cssSelector("div[class^='accountCreated '] > div:nth-child(2) >  p.congrats"))
				.getText();
	}

	public String get_success_message_account_activated_text() {
		return find(
				By.cssSelector("div[class^='accountActivated'] > div:nth-child(2) >  p.congrats"))
				.getText();
	}

	public void click_on_login_into_client_account() {
		clickOn(find(By.cssSelector("button#authCustomer"))
				.waitUntilClickable());

	}

	public String get_text_from_email_field() {
		return find(By.id("forEmailReg")).getAttribute("value").toString();
	}

	public void should_see_activate_account_modal() {
		find(By.id("activateAccount")).waitUntilVisible();
		find(By.id("forLastName")).waitUntilVisible();
		find(By.id("forFirstName")).waitUntilVisible();
		find(By.id("forNumber")).waitUntilVisible();
		find(By.id("forEmailReg")).waitUntilVisible();

	}

	public void click_on_forgot_password_link() {
		clickOn(find(By.id("forgottenPass")).waitUntilClickable());
	}

}