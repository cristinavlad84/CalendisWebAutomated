package ro.evozon.tools.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Time24HoursValidator {
	private Pattern pattern;
	private Matcher matcher;

	private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|)([^:]+:)([0-5][0-9])";

	public Time24HoursValidator() {
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
	}

	/**
	 * Validate time in 24 hours format with regular expression
	 * 
	 * @param time
	 *            time address for validation
	 * @return true valid time fromat, false invalid time format
	 */
	public boolean validate(final String time) {

		matcher = pattern.matcher(time);
		return matcher.matches();

	}

	public String getHourFromString(String time) {
		String str = "";
		matcher = pattern.matcher(time);
		if (matcher.find()) {
			str = matcher.group(1).concat(matcher.group(2).concat(matcher.group(3)));
		}
		return str;
	}

	
	public static void main(String[] args){
		
		Time24HoursValidator time24HoursValidator = new Time24HoursValidator();
		//String str = time24HoursValidator.getHourFromString("9:30-12:00");
		String str ="9:30-12:00";
		String[] hours = str.split("-");
		System.out.println("1'st hour is ="+hours[0]);
		System.out.println("2'nd hour is ="+hours[1]);
		String startHour = time24HoursValidator.getHourFromString(hours[0]);
		String endHour = time24HoursValidator.getHourFromString(hours[1]);
		//String strEnd = time24HoursValidator.getEndHourFromString("9:30-12:00");
		System.out.println("start hour is"+startHour);
		System.out.println("end hour is"+endHour);
	}
}
