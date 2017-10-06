package ro.evozon.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.By;

public class ConfigUtils {
	private static Properties prop = new Properties();
	private static InputStream input = null;

	public static String getBaseUrl() {
		return getProperty("baseUrl");
	}

	public static String getBusinessPlatformUrl() {
		return getProperty("businessUrl");
	}

	public static String getClientPlatformUrl() {
		return getProperty("clientUrl");
	}

	public static String getBusinessEnvironment() {
		return getProperty("businessEnv");
	}

	public static String getOutputFileName() {
		return getProperty("outputFileName");
	}

	public static String getOutputFileNameForXlsxFile() {
		return getProperty("outputFileNameForXlsxFile");
	}

	public static String getOutputFileNameForLocation() {
		return getProperty("outputFileNameForLocation");
	}

	public static String getOutputFileNameForDomain() {
		return getProperty("outputFileNameForDomain");
	}
	public static String getOutputFileNameForAllDomains() {
		return getProperty("outputFileNameForAllDomains");
	}

	public static String getOutputFileNameForDomainAsociation() {
		return getProperty("outputFileNameForDomainAssociation");
	}

	public static String getOutputFileNameForService() {
		return getProperty("outputFileNameForService");
	}

	public static String getOutputFileNameForStaff() {
		return getProperty("outputFileNameForStaff");
	}
	public static String getOutputFileNameForPermission() {
		return getProperty("outputFileNameForPermission");
	}

	public static String getOutputFileNameForNewBusinessFromXlsx() {
		return getProperty("outputFileNameForNewBusinessFromXlsx");
	}

	public static String getOutputFileNameForExistingBusinessAccount() {
		return getProperty("outputFileNameForExistingBusiness");
	}

	public static String getOutputFileNameForSpecialist() {
		return getProperty("outputFileNameForSpecialist");
	}

	public static String getOutputFileNameForReceptionist() {
		return getProperty("outputFileNameForReceptionist");
	}
	public static String outputFileNameForReceptionistLogin() {
		return getProperty("outputFileNameForReceptionistLogin");
	}
	public static String getOutputFileNameForColaborator() {
		return getProperty("outputFileNameForColaborator");
	}

	public static String getOutputFileNameForFacebookClientAccount() {
		return getProperty("outputFileNameForFacebookClientAccount");
	}

	public static String getOutputFileNameForNewBusiness() {
		return getProperty("outputFileNameNewBusiness");
	}

	public static String getBrowserType() {
		return getProperty("browserType");
	}

	final static Collator instance = Collator.getInstance();

	public static String removeAccents(String text) {
		return text == null ? null
				: Normalizer.normalize(text, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	// public static String getChromePath() {
	// return getProperty("chromePath");
	// }
	public static int compareStringWithNoAccent(String s1, String s2) {

		instance.setStrength(Collator.NO_DECOMPOSITION);
		return instance.compare(s1, s2);

	}

	public static String formatMonthString(String month) {

		month = month.replaceAll("I", "J"); // replace Iun, Ian, Iul
											// with Jan, Jun, Jul
		month = month.replaceAll("i", "y"); // replace Mai with May
		return month;
	}

	public static String replaceMonthFromRoToEn(String month) {

		String month_En = "";
		switch (month) {
		case "Ianuarie":
			month_En = "Jan";
			break;
		case "Februarie":
			month_En = "Feb";
			break;
		case "Martie":
			month_En = "Mar";
			break;
		case "Aprilie":
			month_En = "Apr";
			break;
		case "Mai":
			month_En = "May";
			break;
		case "Iunie":
			month_En = "Jun";
			break;
		case "Iulie":
			month_En = "Jul";
			break;
		case "August":
			month_En = "Aug";
			break;
		case "Septembrie":
			month_En = "Sep";
			break;
		case "Octombrie":
			month_En = "Oct";
			break;
		case "Noiembrie":
			month_En = "Nov";
			break;
		case "Decembrie":
			month_En = "Dec";
			break;

		}
		return month_En;
	}

	public static String replaceLineBreaks(String inputString) {
		// inputString = inputString.replaceAll("\\t", "");
		inputString = inputString.replaceAll("\\n", "");
		// inputString = inputString.replaceAll(" ", "");
		return inputString;
	}

	public static String formatYearString(String year) {
		year = year.replaceAll("\'", "");// extract single quote
		return year;
	}

	public static String extractDayOfWeek(String day) {
		day = day.replaceAll("[^0-9?!\\.]", "");
		return day;
	}

	public static String getProperty(String propertyKey) {
		String result = "";
		String configFile = System.getProperty("configFile") == null ? "client" : System.getProperty("configFile");
		try {
			input = new FileInputStream(Constants.RESOURCES_PATH + configFile + "-config.properties");
			prop.load(input);
			result = prop.getProperty(propertyKey);
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
		System.out.println(result);
		return result;
	}

	public static String capitalizeFirstLetter(String inputStr) {
		String interim = inputStr.toLowerCase();
		String outputStr = interim.substring(0, 1).toUpperCase() + interim.substring(1);
		return outputStr;
	}

	public static String capitalizeFirstLetterOnly(String inputStr) {
		String interim = inputStr;
		String outputStr = inputStr.substring(0, 1).toUpperCase() + interim.substring(1);
		return outputStr;
	}

	public static double get_double_from_string(String str) {
		DecimalFormat df = new DecimalFormat();
		DecimalFormatSymbols sfs = new DecimalFormatSymbols();
		sfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(sfs);
		double valueToReturn = 0;
		try {
			valueToReturn = df.parse(str).doubleValue();
			System.out.println("double is: +" + valueToReturn);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valueToReturn;
	}

	public static BigDecimal convertStringToBigDecimalWithTwoDecimals(String value) {
		BigDecimal result = new BigDecimal(value);
		result = result.setScale(2, RoundingMode.HALF_UP);
		return result;
	}

	public static Map<String, String> convertListToMap(List<Map<String, String>> listMap) {
		Map<String, String> result = new HashMap<>();
		listMap.stream().forEach(map -> {
			result.putAll(map.entrySet().stream()
					.collect(Collectors.toMap(entry -> entry.getKey(), entry -> (String) entry.getValue())));
		});
		System.out.println("list of maps to single map:" + result);
		result.entrySet().forEach(System.out::println);
		return result;
	}

}
