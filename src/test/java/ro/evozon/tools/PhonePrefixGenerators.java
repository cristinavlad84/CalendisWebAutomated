package ro.evozon.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhonePrefixGenerators {
	public static final int PHONE_LENGTH_RO_DIGIT = 6;
	public static final List<String> RO_PREFIX_LIST = Arrays.asList("358", "257",
			"248", "234", "259", "263", "231", "239", "268",  "238",
			"242", "255", "264", "241", "267", "245", "251", "236", "246",
			"253", "266", "254", "243", "232", "262", "252", "265", "233",
			"249", "244", "260", "261", "269", "230", "247", "256", "240",
			"250", "235", "237");

	// webdriver timeouts
	public static String generateRandomDigits(){
		String numerics = "1234567890";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < PHONE_LENGTH_RO_DIGIT; i++) {
			double index = Math.random() * numerics.length();
			buffer.append(numerics.charAt((int) index));
		}
		return buffer.toString();
		
	}
	public static String getRandomPrefix(){
		
		int randomPos = (int)(Math.random()*RO_PREFIX_LIST.size());
		String prefix =  RO_PREFIX_LIST.get(randomPos);
		return prefix;
	}
	
	public static String generatePhoneNumber(){
		String prefix = getRandomPrefix();
		String digits= generateRandomDigits();
		
		return ("0").concat(prefix).concat(digits);
	}
	
}
