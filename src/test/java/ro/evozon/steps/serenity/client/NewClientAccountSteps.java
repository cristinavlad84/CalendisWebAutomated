package ro.evozon.steps.serenity.client;

import ro.evozon.pages.client.ClientHomePage;
import ro.evozon.pages.client.LoggedInClientHomePage;
import ro.evozon.pages.client.NewClientAccountPage;
import ro.evozon.pages.client.SetPassswordNewClientAccountPage;
import ro.evozon.tools.ConfigUtils;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.StepGroup;

import ro.evozon.AbstractSteps;
import static org.assertj.core.api.Assertions.*;

public class NewClientAccountSteps extends AbstractSteps {

	NewClientAccountPage newAccountModalPage;
	ClientHomePage clientHomePage;
	SetPassswordNewClientAccountPage setPasswordPage;
	LoggedInClientHomePage loggedInClientPage;

	@Step
	public void clicks_on_intra_in_cont_link() {
		clientHomePage.click_on_intra_in_cont_link();
	}

	@StepGroup
	public void click_on_creeaza_un_cont_nou() {
		newAccountModalPage.click_on_link();
		newAccountModalPage.wait_for_fields_to_load_in_modal();
	}

	@Step
	public void fill_in_client_email_address(String clientEmail) {
		newAccountModalPage.fill_in_client_email_address(clientEmail);
	}

	@Step
	public void fill_in_client_password(String password) {
		newAccountModalPage.fill_in_client_passwod(password);
	}

	@StepGroup
	public void fill_in_client_details(String fName, String lName,
			String email, String phone) {
		fill_in_last_name_field(fName);
		fill_in_first_name_field(lName);
		fill_in_email_field(email);
		fill_in_phone_number_field(phone);

	}

	@Step
	public void fill_in_last_name_field(String lastName) {
		newAccountModalPage.fill_in_user_lastName(lastName);
	}

	@Step
	public void fill_in_first_name_field(String firstName) {
		newAccountModalPage.fill_in_user_firstName(firstName);
	}

	@Step
	public void fill_in_phone_number_field(String phone) {
		newAccountModalPage.fill_in_user_PhoneNo(phone);
	}

	@Step
	public void fill_in_email_field(String email) {
		newAccountModalPage.fill_in_user_Email(email);
	}

	@Step
	public void click_on_create_account_button() {
		newAccountModalPage.click_on_create_account_button();

	}

	@Step
	public void click_on_login_button() {
		newAccountModalPage.click_on_login_button();
	}
	@Step
	public void click_on_facebook_login_button()
	{
		newAccountModalPage.click_on_facebook_login_button();
	}

	@Step
	public void fill_in_email_for_existing_account(String email) {
		newAccountModalPage.fill_in_email_for_existing_account(email);
	}

	@Step
	public void fill_in_password_for_existing_account(String password) {
		newAccountModalPage.fill_in_password_for_existing_acount(password);
	}

	@Step
	public void click_on_activate_account_button() {
		newAccountModalPage.click_on_activate_account_button();
	}

	@Step
	public void click_on_create_personal_account() {
		newAccountModalPage.click_on_create_personal_acount();
	}

	@StepGroup
	public void should_see_success_message_account_created(String successMessage) {
		newAccountModalPage
				.wait_for_success_message_account_created_load_in_modal();
		String message = ConfigUtils.removeAccents(newAccountModalPage
				.get_success_message_account_created_text().trim());
		// System.out.println(message);
		assertThat(message)
				.isEqualTo(ConfigUtils.removeAccents(successMessage));
	}

	@StepGroup
	public void should_see_success_message_account_activated(
			String successMessage) {
		newAccountModalPage
				.wait_for_success_message_account_activated_load_in_modal();

		// System.out.println(message);
		assertThat(ConfigUtils.removeAccents(successMessage.trim())).isEqualTo(
				ConfigUtils.removeAccents(newAccountModalPage
						.get_success_message_account_activated_text().trim()));
	}

	@StepGroup
	public void should_see_warning_message_existing_account(
			String warningMessage) {
		newAccountModalPage.wait_for_warning_message_load();
		String message = ConfigUtils.removeAccents(newAccountModalPage
				.get_warning_message_text());
		assertThat(message)
				.isEqualTo(ConfigUtils.removeAccents(warningMessage));
	}

	@Step
	public void should_see_activate_account_modal_with_prefilled_email(
			String email) {

		assertThat(newAccountModalPage.get_text_from_email_field()).isEqualTo(
				email);
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

		assertThat(loggedInClientPage.get_user_name_in_dropdown()).isEqualTo(
				userFirstName);
	}

	@Step
	public void click_on_forgot_password_link() {
		newAccountModalPage.click_on_forgot_password_link();
	}

	@Step
	public void user_should_see_select_account_popup() {
		newAccountModalPage.should_see_select_account_option_popup();
	}

	@Step
	public void user_should_see_client_area_in_select_account_popup() {
		newAccountModalPage.should_see_client_account_option_listed_in_popup();
	}

	@Step
	public void user_should_see_business_area_in_select_account_popup() {
		newAccountModalPage
				.should_see_business_account_option_listed_in_popup();
	}

	@Step
	public void user_should_see_message_for_select_account_in_popup(
			String expectedMEssage) {
		assertThat(
				ConfigUtils.removeAccents(newAccountModalPage
						.getTextFromSelectAccountPopup())).as(
				"check text from select account popup ").contains(
				expectedMEssage);

	}

	@Step
	public void user_should_see_client_name_in_client_account_area_select_account_popup(
			String clientExpectedMessage) {
		assertThat(
				newAccountModalPage.getTextForClientAccountSelectAccountPopup())
				.as("check text from client area").isEqualToIgnoringCase(
						clientExpectedMessage);

	}

	@Step
	public void user_should_see_business_name_in_busines_account_area_select_account_popup(
			String businessExpectedMessage) {
		assertThat(
				newAccountModalPage
						.getTextForBusinessAccountSelectAccountPopup()).as(
				"check text from business area").containsIgnoringCase(
				businessExpectedMessage.replaceAll("\\s", ""));

	}

	@Step
	public void click_on_login_into_client_account_button() {
		newAccountModalPage.click_on_login_into_client_account();
	}

}