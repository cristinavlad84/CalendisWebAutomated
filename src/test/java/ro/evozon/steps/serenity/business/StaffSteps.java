package ro.evozon.steps.serenity.business;

import ro.evozon.pages.business.StaffHomePage;
import net.thucydides.core.annotations.Step;
import static org.assertj.core.api.Assertions.*;
import ro.evozon.AbstractSteps;

public class StaffSteps extends AbstractSteps {

	StaffHomePage staffPage;

	@Step
	public void fill_in_staff_password(String password) {
		staffPage.fill_in_staff_password(password);
	}

	@Step
	public void repeat_staff_password(String password) {
		staffPage.repeat_staff_password(password);
	}

	@Step
	public void click_on_set_staff_password_button() {
		staffPage.click_on_set_password_button();
	}

	@Step
	public void intro_overlay_should_be_displayed() {
		staffPage.intro_overlay_should_be_displayed();
	}
	@Step
	public void close_intro_overlay(){
		staffPage.close_intro_overlay();
	}

}