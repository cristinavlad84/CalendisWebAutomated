package ro.evozon.steps.serenity.client;

import static org.assertj.core.api.Assertions.assertThat;
import ro.evozon.pages.client.ClientHomePage;
import ro.evozon.pages.client.LoggedInClientHomePage;
import ro.evozon.pages.client.NewClientAccountPage;
import ro.evozon.pages.client.SetPassswordNewClientAccountPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import ro.evozon.AbstractSteps;

public class NewClientAccountSteps extends AbstractSteps {

	NewClientAccountPage newAccountModalPage;
	ClientHomePage clientHomePage;
	SetPassswordNewClientAccountPage setPasswordPage;
	LoggedInClientHomePage loggedInClientPage;

	@Step
	public void clicks_on_intra_in_cont_link() {
		clientHomePage.click_on_intra_in_cont_link();
	}

	@Step
	public void wait_until_creeaza_cont_nou_vizibil() {
		newAccountModalPage.waitUntilLinkAppears();
	}

	@StepGroup
	public void click_on_creeaza_un_cont_nou() {
		newAccountModalPage.click_on_link();
		newAccountModalPage.wait_for_fields_to_load_in_modal();
	}

	@StepGroup
	public void fill_in_client_details(String fName, String lName,
			String email, String phone) {
		//
		// assertThat(dictionaryPage.getDefinitions(),
		// hasItem(containsString(definition)));

		newAccountModalPage.fill_in_user_lastName(lName);
		newAccountModalPage.fill_in_user_firstName(fName);
		newAccountModalPage.fill_in_user_Email(email);

		newAccountModalPage.fill_in_user_PhoneNo(phone);
	}

	@Step
	public void click_on_create_account_button() {
		newAccountModalPage.click_on_creeaza_cont_button();

	}

	@StepGroup
	public void should_see_success_message(String successMessage) {
		newAccountModalPage.wait_for_success_message_load_in_modal();
		String message = newAccountModalPage.get_success_message_text().trim();
		// System.out.println(message);
		assertThat(message.equals(successMessage)).isTrue();
	}

	@Step
	public void fill_in_password(String passw) {
		setPasswordPage.fill_in_password(passw);
	}

	@Step
	public void fill_in_repeat_password(String passw) {
		setPasswordPage.fill_in_repeat_password(passw);
	}

	@Step
	public void clik_on_save_button() {
		setPasswordPage.click_on_save_button();
	}

	@Step
	public void user_dropdown_as_logged_in_should_be_visible() {
		setPasswordPage.dropdown_user_should_be_visible();
	}

	@Step
	public void user_should_see_username_in_dropdown(String userFirstName) {
		assertThat(loggedInClientPage.get_user_name_in_dropdown()
				.contentEquals(userFirstName));
	}
}