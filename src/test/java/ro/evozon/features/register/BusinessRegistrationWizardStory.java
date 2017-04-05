package ro.evozon.features.register;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

import org.jruby.ast.EnsureNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.FieldGenerators.Mode;
import ro.evozon.tools.GMailClient;
import ro.evozon.tools.HTMLLInkExtractor;
import ro.evozon.tools.HTMLLInkExtractor.HtmlLink;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.Tools;
import ro.evozon.pages.business.BusinessWizardPage;
import ro.evozon.steps.serenity.business.LoginBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NewBusinessAccountSteps;
import ro.evozon.steps.serenity.business.NewBusinessSteps;
import ro.evozon.steps.serenity.client.NewClientAccountSteps;
import ro.evozon.steps.serenity.client.EndUserSteps;
import ro.evozon.tests.BaseTest;

@Narrative(text = { "In order to login to business platform",
		"As business user ",
		"I want to be able to register and activate account via email link" })
@RunWith(SerenityRunner.class)
public class BusinessRegistrationWizardStory extends BaseTest {

	private static String businessEmail;
	private static String businessPhoneNo;
	private static String businessPassword;
	private final static String businessAddress;
	private final static String businessMainLocation;
	private final static String businessMainDomain;
	private final static String businessFirstService;
	private final static String businessServicePrice;
	private final static String staffName;
	private final static String staffPhone;
	private final static String staffEmail;

	static {

		String fileName = Constants.OUTPUT_PATH_BUSINESS_ACCOUNT
				+ ConfigUtils.getOutputFileNameForBusinessAccount();
		File file = new File(fileName);
		if (file != null) {
			Properties prop = new Properties();

			try {
				prop.load(new FileInputStream(fileName));
				businessEmail = prop.getProperty("businessEmail");
				businessPassword = prop.getProperty("businessPassword");
				businessPhoneNo = prop.getProperty("businessPhoneNo");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		businessAddress = FieldGenerators.generateRandomString(6, Mode.ALPHA)
				.concat(FieldGenerators.generateRandomString(2, Mode.NUMERIC));
		businessMainLocation = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
		businessMainDomain = FieldGenerators
				.generateRandomString(6, Mode.ALPHA);
		businessFirstService = FieldGenerators.generateRandomString(6,
				Mode.ALPHA);
		businessServicePrice = new DecimalFormat("#.00").format(FieldGenerators
				.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE,
						Constants.MAX_SERVICE_PRICE));
		System.out.println("price" + businessServicePrice);
		staffName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		staffEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA)
				.toLowerCase()
				+ FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(
						Constants.STAFF_FAKE_DOMAIN);
		staffPhone = PhonePrefixGenerators.generatePhoneNumber();
	}

	@After
	public void writeToPropertiesFile() {

		try {
			String fileName = Constants.OUTPUT_PATH_BUSINESS_ACCOUNT
					+ ConfigUtils.getOutputFileNameForNewBusiness();
			Properties props = new Properties();
			FileWriter writer = new FileWriter(fileName);
			props.setProperty("businessAddress", businessAddress);
			props.setProperty("businessMainLocation", businessMainLocation);
			props.setProperty("businessMainDomain", businessMainDomain);
			props.setProperty("businessFirstService", businessFirstService);
			props.setProperty("businessServicePrice", businessServicePrice);
			props.setProperty("staffName", staffName);
			props.setProperty("staffEmail", staffEmail);
			props.setProperty("staffPhone", staffPhone);
			props.store(writer, "new business details");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Before
	public void deleteFile() {
		// get business credentials from properties file

		String fileName = Constants.OUTPUT_PATH_BUSINESS_ACCOUNT
				+ ConfigUtils.getOutputFileNameForNewBusiness();
		File file = new File(fileName);
		boolean status = file.delete();
		if (status)
			System.out.println("File deleted successfully!!");
		else {
			System.out.println("File does not exists");

		}

	}

	@Steps
	public LoginBusinessAccountSteps loginStep;

	@Steps
	NewBusinessAccountSteps newBusinessAccountSteps;
	@Steps
	NewBusinessSteps newBusinessSteps;

	@Issue("#WIKI-1")
	@Test
	public void new_business_registration_wizard() throws Exception {

		List<String> checkedDays = new ArrayList<String>();
		// login with business account
		loginStep.navigateTo(ConfigUtils.getBaseUrl());

		loginStep.login_into_business_account(businessEmail, businessPassword);
		 // domain form
		 newBusinessSteps.waitForWizardPageToLoad();
		
		 newBusinessSteps
		 .wizard_tex_should_be_dispayed(Constants.WIZARD_SUCCESS_MESSAGE_BUSINESS);
		 newBusinessSteps.fill_in_location_wizard(businessAddress,
		 businessMainLocation, businessPhoneNo);
		 // schedule form
		
		 newBusinessSteps.fill_in_schedule_form();
		
		 // domain form
		 newBusinessSteps.fill_in_domain_form(businessMainDomain);
		 // service form
		 newBusinessSteps.fill_in_service_form(businessFirstService,
		 businessServicePrice);
		// staff form
		newBusinessSteps.fill_is_staff_form(staffName, staffEmail, staffPhone);
		// staff schedule

	//	newBusinessSteps.click_on_save_staff_schedule();
	}
}
