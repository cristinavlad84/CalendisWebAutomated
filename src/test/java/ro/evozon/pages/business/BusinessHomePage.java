package ro.evozon.pages.business;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import ro.evozon.AbstractPage;

public class BusinessHomePage extends AbstractPage {

	@FindBy(css = "button[class='register-button open-registration-modal']")
	private WebElementFacade registerButton;

	@FindBy(id = "sign_in_submit_button")
	private WebElementFacade signInLink;

	@WhenPageOpens
	public void waitUntilButtonAppears() {
		element(registerButton).waitUntilVisible();
	}

	public void click_on_inregistreaza_te() {

		// clickOn(registerButton);
		element(
				find(By.cssSelector("button[class='register-button open-registration-modal']")))
				.waitUntilVisible();

		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		WebElement element = getDriver()
				.findElement(
						By.cssSelector("button[class='register-button open-registration-modal']"));
		jse.executeScript("arguments[0].click();", element);
	}

	public void click_on_sign_in_as_business() {
		clickOn(signInLink);

	}

	public void fill_in_business_email(String busEmail) {
		enter(busEmail).into(find(By.id("login-email")));
	}

	public void dismiss_popup() {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		List<WebElementFacade> myList = findAll(By
				.cssSelector("a[class='introjs-button introjs-donebutton introjs-disabled']"));
		if (myList.size() > 0) {
			jse.executeScript("arguments[0].click();", myList.get(0));
		}

	}

	public void fill_in_business_password(String buspwd) {
		enter(buspwd).into(find(By.id("login-password")));
	}

	public void click_on_login_submit_button() {
		clickOn(find(By.id("sign_in_now")));
	}
}