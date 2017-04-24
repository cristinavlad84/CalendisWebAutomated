package ro.evozon.pages.business;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import ro.evozon.AbstractPage;

public class LoggedInBusinessPage extends AbstractPage {

	public void logout_link_should_be_displayed() {
		find(By.id("nav-disconect")).shouldBeVisible();
	}

	public void click_on_settings() {
		clickOn(find(By.id("nav-settings")).waitUntilClickable());
	}
}