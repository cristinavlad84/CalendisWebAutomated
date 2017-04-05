package ro.evozon.pages.client;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import net.thucydides.core.annotations.WhenPageOpens;

import org.openqa.selenium.By;

import ro.evozon.AbstractPage;

public class SetPassswordNewAccountPage extends AbstractPage {

	@FindBy(id = "forPassword")
	private WebElementFacade passwordField;

	@FindBy(id = "forRepetPassword")
	private WebElementFacade repetPasswordField;

	@FindBy(id = "saveIt")
	private WebElementFacade saveButton;

	@WhenPageOpens
	public void waitUntilLinkAppears() {
		element(saveButton).waitUntilVisible();
	}

	public void fill_in_password(String password) {
		// WebElementFacade firstNameField = find(By.id("forLastName"));
		enter(password).into(passwordField);

	}

	public void fill_in_repeat_password(String password) {
		enter(password).into(repetPasswordField);
	}

	public void click_on_save_button() {
		clickOn(saveButton);
	}

	public void dropdown_user_should_be_visible() {

		shouldBeVisible(By.id("user-firstname"));
		shouldBeVisible(By.id("user-dropdown"));

	}

	

}