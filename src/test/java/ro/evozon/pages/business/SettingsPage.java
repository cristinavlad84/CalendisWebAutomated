package ro.evozon.pages.business;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.serenitybdd.core.annotations.findby.By;
import ro.evozon.AbstractPage;
import net.serenitybdd.core.pages.WebElementFacade;

public class SettingsPage extends AbstractPage {

	public void logout_link_should_be_displayed() {
		find(By.id("nav-disconect")).shouldBeVisible();
	}

	public void select_location_from_left_menu() {
		scroll_in_view_then_click_on_element(find(By.id("settings_locations_tab")));
	}

	public void select_service_from_left_menu() {
		scroll_in_view_then_click_on_element(find(By.id("settings_services_tab")));
	}

	public void select_groups_from_left_menu() {
		scroll_in_view_then_click_on_element(find(By.id("settings_group_tab")));
	}

	public void click_on_price_list_tab() {
		scroll_in_view_then_click_on_element(find(By.id("price-lists")));
	}

	public void click_on_services_packet_tab() {
		scroll_in_view_then_click_on_element(find(By.id("bundled-services")));
	}

	public void select_domain_from_left_menu() {
		scroll_in_view_then_click_on_element(find(By.id("settings_domains_tab")));
	}

	public void select_voucher_codes_from_left_menu() {
		scroll_in_view_then_click_on_element(find(By.id("add-new-partner-voucher")));
	}

	public void confirm_item_deletion_in_modal() {
		click_on_element(find(By.cssSelector("button#confirm-delete-item")));

	}

	public void wait_for_save_edits_popup_disappear() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#myAlert")));
	}

	public void click_on_modify_user() {
		find(By.cssSelector("div[class='clearfix user-show'] a[class='edit-info change-user'] > i")).click();
	}

	public void fill_in_new_business_email(String newBusinessEmail) {
		enter(newBusinessEmail).into(find(By.cssSelector("input#business-username")));
	}

	public void click_on_save_user_email() {
		find(By.cssSelector("button#save-username")).click();
	}

	public void click_on_modify_password() {
		find(By.cssSelector("a[class='edit-info change-password']")).click();
	}

	public void fill_in_old_password(String businessPassword) {
		find(By.id("old-psw")).type(businessPassword);
	}

	public void fill_in_new_password(String newPassword) {
		find(By.cssSelector("input#new-psw")).type(newPassword);
	}

	public void fill_in_new_password_confirmed(String newPassword) {
		find(By.cssSelector("input#new-psw-confirm")).type(newPassword);
	}

	public void click_on_save_user_password() {
		find(By.id("save-new-password")).click();
	}

	public void click_on_future_subscription() {
		find(By.cssSelector("a[class='edit-info new-subscription']")).click();
	}

	public void click_on_add_company_details() {
		find(By.cssSelector("div[class='buttons-wrapper'] #to-legal-info")).click();
	}

	public void fill_in_business_address(String businessAddress) {
		find(By.cssSelector("div[class='clearfix'] input[name='address']")).type(businessAddress);
	}

	public void fill_in_business_zip_code(String zipCode) {
		find(By.cssSelector("div[class='clearfix'] input[name='postalCode']")).type(zipCode);
	}

	public void fill_in_business_registration_no(String registrationNo) {
		find(By.cssSelector("div[class='clearfix'] input[name='JCode']")).type(registrationNo);

	}

	public void fill_in_business_identification_no(String identificationNo) {
		find(By.cssSelector("div[class='clearfix'] input[name='CUICode']")).type(identificationNo);
	}

	public void fill_in_business_account(String businessAccount) {
		find(By.cssSelector("div[class='clearfix'] input[name='IBAN']")).type(businessAccount);
	}

	public void select_subscription_county(String county) {
		List<WebElementFacade> mList = findAll(By.cssSelector("select[name='region'] > option"));
		select_specific_option_in_list(mList, county);
	}

	public void select_subscription_city(String city) {
		List<WebElementFacade> mList = findAll(By.cssSelector("select[name='city']  > option"));
		select_specific_option_in_list(mList, city);
	}
	
	public void click_on_save_business_details() {
		find(By.cssSelector("button[class='validation_button client_side_btn_m navigate-location']")).click();
	}

	public void click_on_modify_business_details() {
		find(By.cssSelector("a[class='edit-info update-business-info']")).click();
	}

	public String get_subscription_business_address() {
		String businessAddress = find(By.cssSelector("div[class='clearfix'] input[name='address']")).getValue();
		return businessAddress;
	}

	public String get_subscription_County() {
		String subscriptionCounty = find(By.cssSelector("select[name='region']")).getSelectedValue();
		return subscriptionCounty;
	}

	public String get_subscription_City() {
		String subscriptionCity = find(By.cssSelector("select[name='city']")).getSelectedValue();
		return subscriptionCity;
	}

	public String get_subscription_business_Zip_Code() {
		String businessZipCode = find(By.cssSelector("div[class='clearfix'] input[name='postalCode']")).getValue();
		return businessZipCode;
	}

	public String get_subscription_business_Regsitration_No() {
		String businessRegsitrationNo = find(By.cssSelector("div[class='clearfix'] input[name='JCode']")).getValue();
		return businessRegsitrationNo;
	}

	public String get_subscription_business_Identification_No() {
		String businessIdentificationNo = find(By.cssSelector("div[class='clearfix'] input[name='CUICode']")).getValue();
		return businessIdentificationNo;
	}

	public String get_subscription_business_Account() {
		String businessAccount = find(By.cssSelector("div[class='clearfix'] input[name='IBAN']")).getValue();
		return businessAccount;
	}



	public void verify_future_subscription_is_present_in_subscription_section(){

	}

}