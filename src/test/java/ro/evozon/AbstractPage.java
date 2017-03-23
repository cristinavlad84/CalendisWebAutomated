package ro.evozon;

import org.openqa.selenium.JavascriptExecutor;

import ro.evozon.tools.Constants;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.WhenPageOpens;

public class AbstractPage extends PageObject {

	public void navigateTo(String URL) {
		getDriver().get(URL);
		getDriver().manage().window().maximize();
	}
	
	public void focusElement(String cssSelector) {
		((JavascriptExecutor) getDriver()).executeScript("$('" + cssSelector + "')[0].scrollIntoView(true);");
		waitForPageToLoad();
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
}
