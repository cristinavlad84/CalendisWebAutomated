package ro.evozon.pages.business;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import ro.evozon.AbstractPage;

public class StaffPage extends AbstractPage {

	public void fill_in_staff_password(String password) {

		enter(password).into(find(By.id("setpsw")).waitUntilVisible());
	}

	public void repeat_staff_password(String password) {

		enter(password).into(find(By.id("setpswr")).waitUntilVisible());
	}

	public void click_on_set_password_button() {
		clickOn(find(
				By.cssSelector("button[class='btn save-data save-data-password']"))
				.waitUntilVisible());
	}

	public void intro_overlay_should_be_displayed() {
		find(By.cssSelector("div[class='introjs-tooltip']")).shouldBeVisible();
	}

	public void close_intro_overlay() {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement element = find(By
				.cssSelector("a[class='introjs-button introjs-skipbutton']"));
		jse.executeScript("arguments[0].click();", element);

	}

}