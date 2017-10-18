package ro.evozon.features.business.registration.api;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.steps.serenity.rest.RestSteps;
import ro.evozon.tests.BaseTest;
import ro.evozon.tools.Categories;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.Counties;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.Tools;
import ro.evozon.tools.FieldGenerators.Mode;
import io.restassured.response.Response;

@RunWith(SerenityRunner.class)
public class ApiTest extends BaseTest {
	private int businessCategory;
	private String businessName;
	private String businessEmail;
	private String businessPhone;
	private String businessPassword;
	private int businessCounty;

	public ApiTest() {
		super();
		// TODO Auto-generated constructor stub
		this.businessCategory = Categories.getRandomCategory().getOption();
		this.businessName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.businessEmail = "alain71018043316@automation.33mail.com";
		// FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
		// +
		// FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.BUSINESS_FAKE_MAIL_DOMAIN);
		this.businessPhone = PhonePrefixGenerators.generatePhoneNumber();
		this.businessPassword = "Calendis";
		// FieldGenerators.generateRandomString(8, Mode.ALPHANUMERIC);
		this.businessCounty = Counties.getRandomCounty().getOption();
	}

	@Steps
	public RestSteps restSteps;

	@Test
	public void registerFlowAPICall() {
		Response response = restSteps.registerNewUser(Integer.toString(businessCategory), businessName, businessEmail,
				businessPhone);

		System.out.print("data: " + response.getStatusCode());
		System.out.print("register response: " + response.prettyPrint());

		// aceesss email
		Tools emailExtractor = new Tools();
		String link = "";

		// Tools.RetryOnExceptionStrategy retry = new Tools.RetryOnExceptionStrategy();
		// while (retry.shouldRetry()) {
		// try {
		// link =
		// emailExtractor.getLinkFromEmails(Constants.BUSINESS_GMAIL_BASE_ACCOUNT_SUFFIX,
		// Constants.GMAIL_BUSINESS_BASE_PSW,
		// Constants.NEW_BUSINESS_ACCOUNT_SUCCESS_MESSAGE_SUBJECT,
		// Constants.LINK__BUSINESS_ACTIVATE, businessEmail);
		// break;
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// try {
		// System.out.println("in catch.....");
		// retry.errorOccured();
		// } catch (RuntimeException e1) {
		// throw new RuntimeException("Exception while searching email:", e);
		// } catch (Exception e1) {
		// throw new RuntimeException(e1);
		// }
		//
		// }
		// }
		// String link2 = emailExtractor.editBusinessActivationLink(link,
		// ConfigUtils.getBusinessEnvironment());
		// do create_account api call
		System.out.println("password" + businessPassword);
		Response createAccountResponse = restSteps.createAccount(businessName, businessPhone,
				Integer.toString(businessCategory), businessEmail, businessPassword);
		System.out.print("create account response: " + createAccountResponse.prettyPrint());
		Response loginResponse = restSteps.login(businessEmail, businessPassword);

		System.out.print("login response : " + loginResponse.prettyPrint());
		Response AddCountyResponse = restSteps.addCounty(Integer.toString(businessCounty));

		System.out.print("add county response: " + loginResponse.prettyPrint());
		restSteps.assertAll();
	}
}
