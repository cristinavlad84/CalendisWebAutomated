package ro.evozon.pages.business;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import ro.evozon.AbstractPage;

public class LoggedInBusinessPage extends AbstractPage {

	public void logout_link_should_be_displayed() {
		find(By.id("nav-disconect")).shouldBeVisible();
	}
}