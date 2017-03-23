package ro.evozon.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
	private static Properties prop = new Properties();
	private static InputStream input = null;

	public static String getBaseUrl() {
		return getProperty("baseUrl");
	}

	public static String getBrowserType() {
		return getProperty("browserType");
	}

//	public static String getChromePath() {
//		return getProperty("chromePath");
//	}

	public static String getProperty(String propertyKey) {
		String result = "";
		String configFile = System.getProperty("configFile") == null ? "dev" : System.getProperty("configFile");
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

}
