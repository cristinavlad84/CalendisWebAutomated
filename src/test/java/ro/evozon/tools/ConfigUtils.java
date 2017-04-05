package ro.evozon.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Properties;

public class ConfigUtils {
	private static Properties prop = new Properties();
	private static InputStream input = null;

	public static String getBaseUrl() {
		return getProperty("baseUrl");
	}

	public static String getOutputFileNameForBusinessAccount() {
		return getProperty("outputFileName");
	}
	public static String getOutputFileNameForNewBusiness() {
		return getProperty("outputFileNameNewBusiness");
	}

	public static String getBrowserType() {
		return getProperty("browserType");
	}

	final static Collator instance = Collator.getInstance();

	public static  String removeAccents(String text) {
		return text == null ? null : Normalizer.normalize(text, Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	// public static String getChromePath() {
	// return getProperty("chromePath");
	// }
	public static int compareStringWithNoAccent(String s1, String s2) {

		instance.setStrength(Collator.NO_DECOMPOSITION);
		return instance.compare(s1, s2);

	}

	public static String getProperty(String propertyKey) {
		String result = "";
		String configFile = System.getProperty("configFile") == null ? "client"
				: System.getProperty("configFile");
		try {
			input = new FileInputStream(Constants.RESOURCES_PATH + configFile
					+ "-config.properties");
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

}
