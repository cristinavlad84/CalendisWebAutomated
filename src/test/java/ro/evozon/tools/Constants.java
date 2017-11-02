package ro.evozon.tools;

import java.io.File;

public class Constants {

	public static final String RESOURCES_PATH = "src" + File.separator + "test" + File.separator + "resources"
			+ File.separator + "config" + File.separator;
	public static final String OUTPUT_PATH = "src" + File.separator + "test" + File.separator + "resources"
			+ File.separator + "output" + File.separator;
	public static final String OUTPUT_PATH_DATA_DRIVEN = "src" + File.separator + "test" + File.separator + "resources"
			+ File.separator + "output" + File.separator + "datadriven" + File.separator;
	public static final String OUTPUT_PATH_DATA_DRIVEN_API = "src" + File.separator + "test" + File.separator + "resources"
			+ File.separator + "output" + File.separator + "datadriven" + File.separator + "api" + File.separator;
	public static final String EMAIL_SUFFIX = "@gmail.com";
	public static final String CLIENT_GMAIL_BASE_ACCOUNT_SUFFIX = "calendisautomation";
	public static final String GMAIL_CLIENT_BASE_PSW = "Calendis01";
	public static final String BUSINESS_GMAIL_BASE_ACCOUNT_SUFFIX = "automation.cris";

	public static final String GMAIL_BUSINESS_BASE_PSW = "Brutus01";
	public static final String CLIENT_FAKE_MAIL_DOMAIN = "@calendisautomation.33mail.com";
	public static final String BUSINESS_FAKE_MAIL_DOMAIN = "@automation.33mail.com"; // alll
																						// generated
																						// emails
																						// collected
																						// in
																						// gmail
																						// account:
																						// automation.cris@gmail.com
																						// /
																						// pwd:
																						// Brutus01
	public static final String STAFF_FAKE_DOMAIN = "@staffcalendis.33mail.com"; // (->
																				// all
																				// generated
																				// emails
																				// collected
																				// in
																				// gmail
																				// account
																				// staffcalendis@gmail.com/
																				// pwd:
																				// Calendis01
																				// )
	public static final String STAFF_GMAIL_BASE_ACCOUNT = "staffcalendis@gmail.com";
	public static final String STAFF_PASSWORD_GMAIL_BASE_ACCOUNT = "Calendis01";
	// webdriver timeouts
	public static final long WAIT_TIME_SHORT_SEC = 1;
	public static final long WAIT_TIME_LARGE_SEC = 10;
	public static final int PAGE_LOAD_MAX_RETRY = 30;
	public static final long WAIT_TIME_CONSTANT = 500;
	public static final long WAIT_TIME_MEAN = 3000;
	public static final String SELECT_BUSINESS_CATEGORY = "Selectează categoria afacerii";
	public static final String NEW_CLIENT_ACCOUNT_SUCCESS_MESSAGE_SUBJECT = "Felicitari!";
	public static final String NEW_BUSINESS_ACCOUNT_SUCCESS_MESSAGE_SUBJECT = "Bine ai venit pe";
	public static final String STAFF_INVITATION_TO_JOIN_CALENDIS_MESSAGE_SUBJECT = "Invitatie in Calendis";
	public static final String LINK__CLIENT_ACTIVATE = "/register/activate";
	public static final String LINK_FORGOT_PASSWORD = "/password/reset?";
	public static final String LINK__BUSINESS_ACTIVATE = "/register?email=";
	public static final String LINK__STAFF_INVITATED = "/user/invited/?";
	public static final String NEW_ACCOUNT_SUCCESS_MESSAGE_WEB = "Felicitări! Contul tău a fost creat!";
	public static final String ACTIVATED_ACCOUNT_SUCCESS_MESSAGE = "Felicitări! Contul tău a fost activat și vei fi autentificat automat!";
	public static final String EXISTING_BUSINESS_ACCOUNT_CREATION = "Hopa! Pentru această adresă de e-mail există deja un cont (business).";
	public static final String WIZARD_SUCCESS_MESSAGE_BUSINESS = "Perfect, parola ta a fost salvată cu succes! Hai să continuăm cu configurarea contului.";
	public static final String SELECT_REGISTERED_ACCOUNT_CLIENT = "Selecteaza contul pe care doresti sa-l accesezi:";
	public static final String BUSINESS_ACCOUNT_MESSAGE_SELECT_REGISTERED_ACCOUNT_CLIENT = "Administrator la ";
	public static final String SUCCESS_MESSAGE_SENT_EMAIL_FORGOT_PASSWORD = "Datele au fost trimise la adresa de e-mail introdusa";
	public static final String RESET_PASSWORD_EMAIL_SUBJECT = "Resetare parola Calendis.";
	public static final String SUCCESS_MESSAGE_AFTER_RESET_PASSWORD = "Nu uita sa folosesti parola noua pentru a-ti accesa contul.";
	public static final String SUCESS_MESSAGE_BUSINESS_WIZARD_COMPLETION = "Felicitari, contul tau este acum configurat!";
	public static final String SUCEESS_MESSAGE_NEW_BUSINESS_REGISTER="Verifică-ți email-ul cu care te-ai înscris pentru a-ți activa contul Calendis Business. În cazul în care nu-l găsești în Inbox, verifică și în folderul Spam.";
	public static final Double MIN_SERVICE_PRICE = 1.00;
	public static final Double MAX_SERVICE_PRICE = 1000.00;
	public static final String LOWER_INTERVAL_MESSAGE = "Specialistul nu este disponibil in intervalul selectat.";
	public static final String OVERLAP_INTERVAL_MESSAGE = "Programarea se suprapune cu alte progrămari. Următorul interval disponibil e la ora";
	public static final String OVERLAP_INTERVAL_MESSAGE_SECOND = "Programarea se suprapune cu alte programări din fișa curentă. Încercați altă dată/oră/durată.";
	public static final String CLOSED_SCHEDULE = "inchis";
	public static final int NO_OF_WEEK_DAYS = 6;
	public static final String RANGE_HOURS = "08:00-21:00";
	public static final int HOUR_MIN_LIMIT = 9;
	public static final int HOUR_MAX_LIMIT = 20;

}
