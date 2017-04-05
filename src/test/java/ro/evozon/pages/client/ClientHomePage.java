package ro.evozon.pages.client;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import net.thucydides.core.annotations.WhenPageOpens;

import ro.evozon.AbstractPage;

public class ClientHomePage extends AbstractPage {

	@FindBy(className = "logInClick ")
	private WebElementFacade loginClickLink;

	@WhenPageOpens
	public void waitUntilTitleAppears() {
		element(loginClickLink).waitUntilVisible();
	}

	public void click_on_intra_in_cont_link() {

		clickOn(loginClickLink);
	}

}