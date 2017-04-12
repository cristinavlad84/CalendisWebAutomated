package ro.evozon;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.core.*;

public class AbstractSteps extends ScenarioSteps {

	private static final long serialVersionUID = 9210642586998408049L;
	private AbstractPage abstractPage;

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
}
