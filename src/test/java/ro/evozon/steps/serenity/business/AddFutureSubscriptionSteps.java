package ro.evozon.steps.serenity.business;

import java.util.List;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractSteps;
import ro.evozon.pages.business.SettingsPage;

public class AddFutureSubscriptionSteps extends AbstractSteps {

    SettingsPage settingsPage;

    @Step
    public void click_on_future_subscription() {
        settingsPage.click_on_future_subscription();

    }

    @Step
    public void click_on_add_company_details() {
        settingsPage.click_on_add_company_details();
    }

    @Step
    public void fill_in_business_address(String businessAddress) {
        settingsPage.fill_in_business_address(businessAddress);
    }

    @Step
    public void select_subscription_county(String county) {

        settingsPage.select_subscription_county(county);
    }

    @Step
    public void select_subscription_city(String city) {

        settingsPage.select_subscription_city(city);
    }

    // @Step
    // public void fill_in_business_main_location_city() {
    // settingsPage.
    // }
    //

    @Step
    public void fill_in_business_zip_code(String zipCode) {
        settingsPage.fill_in_business_zip_code(zipCode);
    }

    @Step
    public void fill_in_business_registration_no(String registrationNo) {
        settingsPage.fill_in_business_registration_no(registrationNo);
    }

    @Step
    public void fill_in_business_identification_no(String identificationNo) {
        settingsPage.fill_in_business_identification_no(identificationNo);
    }

    @Step
    public void fill_in_business_account(String businessAccount) {
        settingsPage.fill_in_business_account(businessAccount);
    }

    @Step
    public void click_on_save_business_details() {
        settingsPage.click_on_save_business_details();
    }

    @Step
    public void click_on_modify_business_details() { settingsPage.click_on_modify_business_details(); }

    @Step
    public void verify_future_subscription_is_present_in_subscription_section(String subscriptionBusinessAddress, String subscriptionCounty,
                                                                              String subscriptionCity, String businessZipCode, String businessRegsitrationNo, String businessIdentificationNo,
                                                                              String businessAccount) {

        softly.assertThat(settingsPage.get_subscription_business_address()).isEqualToIgnoringCase(subscriptionBusinessAddress);
        softly.assertThat(settingsPage.get_subscription_County()).isEqualToIgnoringCase(subscriptionCounty);
        softly.assertThat(settingsPage.get_subscription_City()).isEqualToIgnoringCase(subscriptionCity);
        softly.assertThat(settingsPage.get_subscription_business_Zip_Code()).isEqualToIgnoringCase(businessZipCode);
        softly.assertThat(settingsPage.get_subscription_business_Regsitration_No()).isEqualToIgnoringCase(businessRegsitrationNo);
        softly.assertThat(settingsPage.get_subscription_business_Identification_No()).isEqualToIgnoringCase(businessIdentificationNo);
        softly.assertThat(settingsPage.get_subscription_business_Account()).isEqualToIgnoringCase(businessAccount);
    }

    @Step
    public void click_on_subscription_tab() {
        settingsPage.click_on_subscription_tab();
    }

    @Step
    public void click_on_save_future_subscription(){
        settingsPage.click_on_save_future_subscription();
    }

}
