package ro.evozon.pages.business;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import ro.evozon.AbstractPage;

public class LoggedInBusinessPage extends AbstractPage {

	public void logout_link_should_be_displayed() {
		find(By.id("nav-disconect")).shouldBeVisible();
	}

	public void click_on_settings() {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement el = find(By.id("nav-settings"));
		jse.executeScript("arguments[0].click();", el);
		// clickOn(find(By.id("nav-settings")).waitUntilClickable());
		waitForPageToLoad();
	}
}