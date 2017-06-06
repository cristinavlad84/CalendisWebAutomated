package ro.evozon;

import org.assertj.core.api.SoftAssertions;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class AbstractSteps extends ScenarioSteps {

	private static final long serialVersionUID = 9210642586998408049L;
	private AbstractPage abstractPage;

	protected SoftAssertions softly = new SoftAssertions();

	@Step
	public void navigateTo(String URL) {
		abstractPage.navigateTo(URL);
	}

	@Step
	public void navigate(String url) {
		abstractPage.navigateToUrl(url);
	}

	@Step
	public void closeBrowser() {
		abstractPage.closeBrowser();
	}

	@Step
	public void deleteAllCookies() {
		abstractPage.deleteAllCookies();
	}

	@Step
	public void assertAll() {
		softly.assertAll();
	}

	@Step
	public void refresh() {
		abstractPage.refresh();
	}
	@Step
	public void wait_for_saving_alert() {
		abstractPage.waitforAllert();
	}

	
}
