package ro.evozon.pages.client;



import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import ro.evozon.AbstractPage;

public class LoggedInClientHomePage extends AbstractPage {

	@FindBy(id = "user-firstname")
	private WebElementFacade userFirstNameDrop;

	@FindBy(id = "user-dropdown")
	private WebElementFacade userDropDown;

	public String get_user_name_in_dropdown() {
		return userFirstNameDrop.getText().trim();
	}
}