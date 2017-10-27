package ro.evozon.features.business.registration.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.evozon.tools.Pair;
import ro.evozon.tools.Schedule;

public class SSPlay {
	ArrayList<String>[] scheduleData = new ArrayList[2];
	// {"a","v"};
	// scheduleData={{"s","a","v","d"}};

	public static void main(String[] args) {
		String[][][] arr1 = { { { "day" }, { "1" }, { "09:00", "17:00" } },
				{ { "day" }, { "2" }, { "09:00", "17:00" } } };
		for (String[][] array2D : arr1) {
			for (String[] array1D : array2D) {
				for (String item : array1D) {
					System.out.println(item);
				}
			}
		}
		Pair<String, Integer> p = Pair.of("dss", 1);
		List<String> mList = new ArrayList<String>();
		mList.add("09:00");
		mList.add("17:00");
		Pair<String, List<String>> pair1 = Pair.of("rangeHours :", mList);
		List<String> fixedSizeList = Arrays.asList(new String[2]);
		fixedSizeList.add("09:00");
		fixedSizeList.add("17:00");

		Pair<String, List<String>> pair2 = Pair.of("rangeHours :", fixedSizeList);
		List<String> rangeHoursList = new ArrayList<String>();
		rangeHoursList.add("09:00");
		rangeHoursList.add("17:00");
		Map<String, Integer> dayOfWeekMap = new HashMap<String, Integer>();
		dayOfWeekMap.put("day:", 1);
		Map<String, List<String>> rangeHoursMap = new HashMap<String, List<String>>();
		rangeHoursMap.put("hours", rangeHoursList);
		Pair<Map<String, Integer>, Map<String, List<String>>> mPair = Pair.of(dayOfWeekMap, rangeHoursMap);
	}

}
