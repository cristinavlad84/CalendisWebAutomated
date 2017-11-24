package ro.evozon.features.business.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.thucydides.core.annotations.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import ro.evozon.features.business.datadriven.ParseXlsxUtils;
import ro.evozon.steps.serenity.business.AddFutureSubscriptionSteps;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.tests.BaseTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;

@Narrative(text = { "In order to continue the subscription of a new created business", "As business user ",
"I want to be able to add company's details and select the future subscription and than see it in Settings page" })
@RunWith(SerenityRunner.class)

public class AddFutureSubscription extends BaseTest {

	private String businessName;
	private String businessEmail;
	private String businessPassword;
	private String subscriptionBusinessAddress;
	private String subscriptionCounty;
	private String subscriptionCity;
	private String businessZipCode;
	private String businessRegsitrationNo;
	private String businessIdentificationNo;
	private String businessAccount;
	
	public AddFutureSubscription() {
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void readFromFile() {
		String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileName();
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			props.load(input);
			businessName = props.getProperty("businessName", businessName);
			businessEmail = props.getProperty("businessEmail", businessEmail);
			businessPassword = props.getProperty("businessPassword", businessPassword);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String fileNameSubscription = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForSubscription();
		Properties propsSubscription = new Properties();
		InputStream inputSubscripton = null;
		try {
			inputSubscripton = new FileInputStream(fileNameSubscription);
			propsSubscription.load(inputSubscripton);
			subscriptionBusinessAddress = propsSubscription.getProperty("adresa", subscriptionBusinessAddress);
			subscriptionCounty = propsSubscription.getProperty("judet", subscriptionCounty);
			subscriptionCity = propsSubscription.getProperty("localitate", subscriptionCity);
			businessZipCode = propsSubscription.getProperty("codPostal", businessZipCode);
			businessRegsitrationNo = propsSubscription.getProperty("nrRegComert", businessRegsitrationNo);
			businessAccount=propsSubscription.getProperty("iban", businessAccount);
			businessIdentificationNo = propsSubscription.getProperty("cui", businessIdentificationNo);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Steps
	public LoginBusinessAccountSteps loginStep;
	
	@Steps
	public AddFutureSubscriptionSteps addFutureSubscriptionSteps;

	@Issue("#CLD-035")
	@Test
	public void add_future_subscription_data(){
		loginStep.navigateTo(ConfigUtils.getBaseUrl());
		loginStep.login_into_business_account(businessEmail, businessPassword);
		loginStep.dismiss_any_popup_if_appears();
		// user should be logged in --> Deconecteaza-te should be displayed

		loginStep.logout_link_should_be_displayed();
		loginStep.click_on_settings();
		loginStep.dismiss_any_popup_if_appears();
		addFutureSubscriptionSteps.click_on_future_subscription();
		addFutureSubscriptionSteps.click_on_add_company_details();
		addFutureSubscriptionSteps.fill_in_business_address(subscriptionBusinessAddress);
		addFutureSubscriptionSteps.select_subscription_county(subscriptionCounty);
		addFutureSubscriptionSteps.select_subscription_city(subscriptionCity);
		addFutureSubscriptionSteps.fill_in_business_zip_code(businessZipCode);
		addFutureSubscriptionSteps.fill_in_business_registration_no(businessRegsitrationNo);
		addFutureSubscriptionSteps.fill_in_business_identification_no(businessIdentificationNo);
		addFutureSubscriptionSteps.fill_in_business_account(businessAccount);
		addFutureSubscriptionSteps.click_on_save_business_details();
		addFutureSubscriptionSteps.click_on_modify_business_details();
		addFutureSubscriptionSteps.verify_future_subscription_is_present_in_subscription_section(subscriptionBusinessAddress, subscriptionCounty, subscriptionCity, businessZipCode, businessRegsitrationNo, businessIdentificationNo, businessAccount);
		addFutureSubscriptionSteps.click_on_subscription_tab();
		addFutureSubscriptionSteps.click_on_future_subscription();
		addFutureSubscriptionSteps.click_on_save_future_subscription();
		addFutureSubscriptionSteps.assertAll();

	
	
		
	}
	
}
