package ro.evozon.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import net.thucydides.core.annotations.WhenPageOpens;

import org.openqa.selenium.By;

import ro.evozon.AbstractPage;

public class NewAccountModalPage extends AbstractPage {

	@FindBy(id = "signUpNow")
	private WebElementFacade createNewAccountLink;

	@WhenPageOpens
	public void waitUntilLinkAppears() {
		element(createNewAccountLink).waitUntilVisible();
	}

	public void click_on_link() {
		createNewAccountLink.click();
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

	public void click_on_creeaza_cont_button() {
		clickOn(find(By.id("createAccount")));
	}
	public void wait_for_success_message_load_in_modal(){
		
		//WebElementFacade el = find((By.cssSelector("div#login-wrapper > div[class='calendis-login-steps-container'] > div[class^='accountCreated']")));
		waitForAnyRenderedElementOf(By.cssSelector("div[class^='accountCreated']"), By.id("successOkBtn"));
		
	}
	public String get_success_message_text() {
        return find(By.cssSelector("div[class^='accountCreated '] > div:nth-child(2) >  p.congrats")).getText();
    }


}