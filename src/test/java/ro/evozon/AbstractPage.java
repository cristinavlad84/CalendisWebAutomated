package ro.evozon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import com.google.common.base.Predicate;

import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import java.security.SecureRandom;

public class AbstractPage extends PageObject {

	public void navigateTo(String URL) {
		getDriver().get(URL);
		getDriver().manage().window().maximize();
	}

	public void navigateToUrl(String url) {
		getDriver().navigate().to(url);
		getDriver().manage().window().maximize();
	}

	public void focusElement(String cssSelector) {
		((JavascriptExecutor) getDriver()).executeScript("$('" + cssSelector
				+ "')[0].scrollIntoView(true);");
		waitForPageToLoad();
	}

	public void closeBrowser() {
		getDriver().close();
	}

	public void deleteAllCookies() {
		getDriver().manage().deleteAllCookies();
	}

	public void mouseOverElement(String cssSelector) {
		((JavascriptExecutor) getDriver()).executeScript("$('" + cssSelector
				+ "').mouseover();");
		waitForPageToLoad();
	}

	public void clickElement(String cssSelector) {
		((JavascriptExecutor) getDriver()).executeScript("$('" + cssSelector
				+ "').click();");
		waitForPageToLoad();
	}

	protected void waitForPageToLoad() {
		int retry = 0;

		String response = "";
		do {
			try {
				Thread.sleep(Constants.WAIT_TIME_CONSTANT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			response = String.valueOf(((JavascriptExecutor) getDriver())
					.executeScript("return document.readyState"));
			retry++;
		} while (retry <= Constants.PAGE_LOAD_MAX_RETRY
				&& response.equals("complete") != true);
	}

	public String select_random_option_in_dropdown(WebElementFacade dropdown) {

		element(dropdown).waitUntilVisible();
		Select select = new Select(dropdown);
		waitUntilSelectOptionsPopulated(select);
		List<WebElement> optionList = select.getOptions();
		int length = optionList.size();
		// is length-1,
		int random = FieldGenerators.getRandomIntegerBetween(1, length - 1);

		select.selectByIndex(random);
		System.out.println("Selected value in dropdown"
				+ optionList.get(random).getText());
		return optionList.get(random).getText();

	}

	private void waitUntilSelectOptionsPopulated(final Select select) {
		new FluentWait<WebDriver>(getDriver())
				.withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.MILLISECONDS)
				.until(new Predicate<WebDriver>() {
					public boolean apply(WebDriver d) {
						return (select.getOptions().size() > 1);
					}
				});
	}
	public void waitUntilOptionsPopulated(final List<WebElementFacade> select) {
		new FluentWait<WebDriver>(getDriver())
				.withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.MILLISECONDS)
				.until(new Predicate<WebDriver>() {
					public boolean apply(WebDriver d) {
						return (select.size() > 1);
					}
				});
	}
	public void select_day_of_week_schedule(String containerLocator,
			String locator) {
		List<String> checkedDays = new ArrayList<String>();
		List<WebElementFacade> dayOfWeekList = find(
				By.cssSelector(containerLocator)).thenFindAll(
				By.cssSelector(locator));
		int max = dayOfWeekList.size();
		System.out.println("days found " + max);
		int noOfDaysToBeChecked = FieldGenerators.getRandomIntegerBetween(1,
				max);
		System.out.println("days to be checekd " + noOfDaysToBeChecked);
		while (noOfDaysToBeChecked > 0) {

			int random = FieldGenerators.getRandomIntegerBetween(0, max - 1);
			System.out.println("random is" + random);
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			WebElement element = dayOfWeekList.get(random);
			WebElement checkedOpt = dayOfWeekList.get(random).findElement(
					By.cssSelector("span:nth-of-type(1)"));

			if (!checkedOpt.getAttribute("class").contentEquals(
					"week-day week-day-active")) {

				jse.executeScript("arguments[0].click();", element);
				// dayOfWeekList.get(random).click();
			} else {
				jse.executeScript("arguments[0].click();", element);// uncheck
				jse.executeScript("arguments[0].click();", element);// check
																	// again
			}
			noOfDaysToBeChecked--;

		}

	}

}
