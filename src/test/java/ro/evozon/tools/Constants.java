package ro.evozon.tools;

import java.io.File;

public class Constants {

	public static final String RESOURCES_PATH = "src" + File.separator + "test"
			+ File.separator + "resources" + File.separator + "config"
			+ File.separator;
	public static final String OUTPUT_PATH_BUSINESS_ACCOUNT = "src"
			+ File.separator + "test" + File.separator + "resources"
			+ File.separator + "output" + File.separator;
	public static final String OUTPUT_PATH_BUSINESS_DETAILS = "src"
			+ File.separator + "test" + File.separator + "resources"
			+ File.separator + "output" + File.separator;
	public static final String EMAIL_SUFFIX = "@gmail.com";
	public static final String GMAIL_CLIENT_BASE_ACCOUNT_SUFFIX = "calendisautomation";
	public static final String GMAIL_CLIENT_BASE_PSW = "Calendis01";
	public static final String GMAIL_BUSINESS_BASE_ACCOUNT_SUFFIX = "automation.cris";

	public static final String GMAIL_BUSINESS_BASE_PSW = "Calendis01";
	public static final String BUSINESS_FAKE_MAIL_DOMAIN = "@automation.33mail.com"; //alll generated emails collected in gmail account: automation.cris@gmail.com / pwd: Calendis01
	public static final String STAFF_FAKE_DOMAIN="@staffcalendis.33mail.com"; //(-> all generated emails collected in gmail account staffcalendis@gmail.com/ pwd: Calendis01 )
	// webdriver timeouts
	public static final long WAIT_TIME_SHORT_SEC = 1;
	public static final long WAIT_TIME_LARGE_SEC = 10;
	public static final int PAGE_LOAD_MAX_RETRY = 30;
	public static final long WAIT_TIME_CONSTANT = 500;
	public static final long WAIT_TIME_MEAN = 3000;

	public static final String NEW_CLIENT_ACCOUNT_SUCCESS_MESSAGE_SUBJECT = "Felicitari!";
	public static final String NEW_BUSINESS_ACCOUNT_SUCCESS_MESSAGE_SUBJECT = "Inregistrare in Calendis";
	public static final String LINK__CLIENT_ACTIVATE = "/register/activate";
	public static final String LINK__BUSINESS_ACTIVATE = "/register?email=";
	public static final String NEW_ACCOUNT_SUCCESS_MESSAGE_WEB = "Felicitări! Contul tău a fost creat!";
	public static final String WIZARD_SUCCESS_MESSAGE_BUSINESS = "Perfect, parola ta a fost salvată cu succes! Hai să continuăm cu configurarea contului.";
	public static final Double MIN_SERVICE_PRICE = 0.00;
	public static final Double MAX_SERVICE_PRICE = 1000.00;

}
