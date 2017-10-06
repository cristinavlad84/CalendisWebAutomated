package ro.evozon.tools.utils;

public class DaysOfWeekConverter
{
	public static String convertToDayOfWeek(int numericDay) {
		switch (numericDay) {
		case 0:
			return "Luni";
		case 1:
			return "Marti";
		case 2:
			return "Miercuri";
		case 3:
			return "Joi";
		case 4:
			return "Vineri";
		case 5:
			return "Sambata";
		case 6:
			return "Duminica";
		default:
			throw new AssertionError("There is no such thing " + numericDay);
		}
	}
}
