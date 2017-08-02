package ro.evozon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

import ro.evozon.tools.ConfigUtils;
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
		((JavascriptExecutor) getDriver()).executeScript("$('" + cssSelector + "')[0].scrollIntoView(true);");
		waitForPageToLoad();
	}

	public void closeBrowser() {
		getDriver().close();
	}

	public void refresh() {
		getDriver().navigate().refresh();
	}

	public void deleteAllCookies() {
		getDriver().manage().deleteAllCookies();
	}

	public void mouseOverElement(String cssSelector) {
		((JavascriptExecutor) getDriver()).executeScript("$('" + cssSelector + "').mouseover();");
		waitForPageToLoad();
	}

	public void clickElement(String cssSelector) {
		((JavascriptExecutor) getDriver()).executeScript("$('" + cssSelector + "').click();");
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
			response = String.valueOf(((JavascriptExecutor) getDriver()).executeScript("return document.readyState"));
			retry++;
		} while (retry <= Constants.PAGE_LOAD_MAX_RETRY && response.equals("complete") != true);
	}

	public String select_random_option_in_dropdown(WebElementFacade dropdown) {

		// element(dropdown).waitUntilVisible();
		Select select = new Select(dropdown);
		waitUntilSelectOptionsPopulated(select);
		List<WebElement> optionList = select.getOptions();
		int length = optionList.size();
		System.out.println("size is " + length);
		boolean cont = true;
		int random = 0;

		while (cont == true) {
			if (length > 1) {
				random = FieldGenerators.getRandomIntegerBetween(0, length - 1);
				System.out.println("random generated " + random);
				cont = false;
			}
			if (optionList.get(random).getText().equals(Constants.SELECT_BUSINESS_CATEGORY)) {
				cont = true;
			}
			if (length == 1) {
				random = 0;
				cont = false;
			}
		}
		select.selectByIndex(random);
		System.out.println("Selected value in dropdown" + optionList.get(random).getText());
		return optionList.get(random).getText();

	}

	public void select_option_in_dropdown(WebElementFacade dropdown, String optText) {
		Select select = new Select(dropdown);
		waitUntilSelectOptionsPopulated(select);
		if (is_option_text_in_dropdown(dropdown, optText)) {
			select_specific_option_in_list(dropdown, optText);
		}
	}

	public boolean is_option_text_in_dropdown(WebElementFacade dropdown, String optText) {
		boolean isPresent = false;
		List<WebElementFacade> optionsList = dropdown.thenFindAll(By.tagName("option"));
		for (WebElementFacade el : optionsList) {
			System.out.println("option is " + el.getText() + "and opt to found is " + optText);
			if (el.getText().contains(optText)) {
				System.out.println("option found " + el.getText());
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	public String select_random_option_in_list(List<WebElementFacade> mList) {
		waitUntilOptionsPopulated(mList);
		int length = mList.size();
		int random = 0;
		if (length > 1) {

			random = FieldGenerators.getRandomIntegerBetween(0, length - 1);

		}
		String str = mList.get(random).getText().trim();
		while (str.contains("(nu apartine specialistului)")) {

			random = FieldGenerators.getRandomIntegerBetween(0, length - 1);
			str = mList.get(random).getText().trim();
		} // --> exclude option which change specialist also
		System.out.println("selected option in list " + random + " " + str);
		mList.get(random).click();
		return str;
	}

	public void select_specific_option_in_list(List<WebElementFacade> mList, String optionToSelect) {
		waitUntilOptionsPopulated(mList);
		for (WebElementFacade el : mList) {
			String comparator = ConfigUtils.removeAccents(el.getText().trim()).toLowerCase();
			String toCompare = optionToSelect.trim().toLowerCase();
			System.out.println("comparing " + comparator + " with " + toCompare);
			if (comparator.contains(toCompare)) {
				el.click();
				System.out.println("found");
				break;
			}
		}
	}

	public void select_specific_option_in_list(WebElementFacade dropdown, String optionToSelect) {
		List<WebElementFacade> optionsList = dropdown.thenFindAll(By.tagName("option"));
		for (WebElementFacade el : optionsList) {
			System.out.println("option is " + el.getText() + "and opt to found is " + optionToSelect);
			if (el.getText().contains(optionToSelect)) {
				System.out.println("option found " + el.getText());
				el.click();
				break;
			}
		}
	}

	private void waitUntilSelectOptionsPopulated(final Select select) {
		new FluentWait<WebDriver>(getDriver()).withTimeout(60, TimeUnit.SECONDS).pollingEvery(10, TimeUnit.MILLISECONDS)
				.until(new Predicate<WebDriver>() {
					public boolean apply(WebDriver d) {
						return (select.getOptions().size() >= 1);
					}
				});
	}

	public void waitUntilOptionsPopulated(final List<WebElementFacade> elemList) {
		new FluentWait<WebDriver>(getDriver()).withTimeout(60, TimeUnit.SECONDS).pollingEvery(10, TimeUnit.MILLISECONDS)
				.until(new Predicate<WebDriver>() {
					public boolean apply(WebDriver d) {
						return (elemList.size() >= 1);
					}
				});
	}

	public WebElement getElementByLocator(By locator, int timeout) {
		System.out.println("Calling method getElementByLocator: " + locator.toString());
		int interval = 5;
		if (timeout <= 20)
			interval = 3;
		if (timeout <= 10)
			interval = 2;
		if (timeout <= 4)
			interval = 1;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(interval, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
		WebElement we = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return we;
	}

	public void select_day_of_week_schedule(String containerLocator, String locator) {
		List<WebElementFacade> dayOfWeekList = find(By.cssSelector(containerLocator))
				.thenFindAll(By.cssSelector(locator));
		int max = dayOfWeekList.size();
		System.out.println("days found " + max);
		int noOfDaysToBeChecked = FieldGenerators.getRandomIntegerBetween(1, max);
		System.out.println("days to be checekd " + noOfDaysToBeChecked);
		while (noOfDaysToBeChecked > 0) {

			int random = FieldGenerators.getRandomIntegerBetween(0, max - 1);
			System.out.println("random is" + random);
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			WebElement element = dayOfWeekList.get(random);
			WebElement checkedOpt = dayOfWeekList.get(random).findElement(By.cssSelector("span:nth-of-type(1)"));

			if (!checkedOpt.getAttribute("class").contentEquals("week-day week-day-active")) {

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

	public void scroll_in_view_element(WebElement element) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(true);", element);
			waitForPageToLoad();// -> wait to save edits
		} catch (Exception e) {

		}

	}

	public void scroll_in_view_then_click_on_element(WebElementFacade element) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(true);", element);
			jse.executeScript("arguments[0].click();", element);
			waitForPageToLoad();// -> wait to save edits
		} catch (Exception e) {

		}

	}

	public void scroll_in_view_then_click_on_element(WebElement element) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(true);", element);
			jse.executeScript("arguments[0].click();", element);
			waitForPageToLoad();// -> wait to save edits
		} catch (Exception e) {

		}

	}

	public void focusOnElement(WebElementFacade element) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].focus();", element);

			waitForPageToLoad();// -> wait to save edits
		} catch (Exception e) {

		}

		waitForPageToLoad();

	}

	public void click_on_element(WebElementFacade element) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].click();", element);
		} catch (Exception e) {

		}
		waitForPageToLoad();
	}

	public boolean is_item_displayed_through_found_element(WebElementFacade elementContainer, String stringToFind,
			String cssLocator) {
		boolean found = false;
		WebElementFacade el = elementContainer.find(By.cssSelector(cssLocator));
		// System.out.println("Found !!!!" + el.getTextValue());
		if (ConfigUtils.removeAccents(el.getTextValue().trim().toLowerCase())
				.contains(ConfigUtils.removeAccents(stringToFind.toLowerCase()))) {
			found = true;
		}
		return found;

	}

	public boolean is_element_present_in_elements_list(String cssLocatorContainer, String cssLocatorElement,
			String stringToFind) {
		boolean found = false;
		List<WebElementFacade> elementsList = findAll(By.cssSelector(cssLocatorContainer));
		if (elementsList.size() > 0) {
			for (WebElementFacade el : elementsList) {
				String str = el.find(By.cssSelector(cssLocatorElement)).getTextValue().trim();
				if (ConfigUtils.removeAccents(str.toLowerCase())
						.contains(ConfigUtils.removeAccents(stringToFind.toLowerCase()))) {
					System.out.println("found " + str);
					found = true;
					break;
				}

			}
		}
		return found;
	}

	public WebElementFacade get_element_from_elements_list(String cssLocatorContainer, String cssLocatorElement,
			String stringToFind) {
		WebElementFacade elementsContainer = null;

		List<WebElementFacade> elementsList = findAll(By.cssSelector(cssLocatorContainer));
		for (WebElementFacade el : elementsList) {
			String str = el.find(By.cssSelector(cssLocatorElement)).getTextValue().trim();
			if (ConfigUtils.removeAccents(str.toLowerCase())
					.contains(ConfigUtils.removeAccents(stringToFind.toLowerCase()))) {
				elementsContainer = el;
				break;
			}

		}
		return elementsContainer;
	}

	public void waitforAllert() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='main']/div/div/div[@id='myAlert']")));

	}
}
