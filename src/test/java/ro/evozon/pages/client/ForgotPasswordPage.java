package ro.evozon.pages.client;

import org.openqa.selenium.By;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import ro.evozon.AbstractPage;
import ro.evozon.tools.ConfigUtils;

public class ForgotPasswordPage extends AbstractPage {

	public void click_on_sent_button_in_forgot_password_form() {

		clickOn(find(
				By.cssSelector("#lost-password-form > form:nth-of-type(1) > div:nth-of-type(1) > button:nth-of-type(1)"))
				.waitUntilClickable());
	}

	public void fill_in_email_in_lost_password_form(String email) {
		enter(email).into(find(By.id("user-mail-check")).waitUntilVisible());
	}

	public String get_success_message_for_reset_password() {
		return (ConfigUtils
				.removeAccents(find(
						By.cssSelector("div[class^='resetPassMailSent'] > div:nth-of-type(2) > p:nth-of-type(1)"))
						.getText().trim()));
	}

	public void fill_in_new_password(String password) {
		enter(password).into(find(By.id("forPassword")));
	}

	public void repeat_password(String pasw) {
		enter(pasw).into(find(By.id("forRepetPassword")));
	}

	public void click_on_reset_password_button() {
		clickOn(find(By.id("saveIt")));
	}

	public String get_text_from_reset_password_form() {
		return ConfigUtils
				.removeAccents(find(
						By.cssSelector("div#changePasswordSuccess > div:nth-of-type(2) > p:nth-of-type(1)"))
						.getText().trim());
	}
}