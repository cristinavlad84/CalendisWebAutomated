package ro.evozon.steps.serenity.business;

import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.BusinessHomePage;

public class NavigationStep extends AbstractSteps {
	BusinessHomePage businessHomePage;

	@Step
	public void click_on_calendar_tab() {
		businessHomePage.click_on_calendar_tab();
	}
}
