package ro.evozon.pages.client;

import org.openqa.selenium.By;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import net.thucydides.core.annotations.WhenPageOpens;

import ro.evozon.AbstractPage;

public class ClientHomePage extends AbstractPage {

	public void click_on_intra_in_cont_link() {

		clickOn(find(By.cssSelector("a[class^='logInClick']")).waitUntilClickable());
	}

}