package ro.evozon.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
	private final Map<String, String[]> rangeHours;
//	private static Map<String, String[]> emptyMap(){
//		return new HashMap
//	}
	public Schedule(String startHour, String endHour) {
		List<String> where = new ArrayList<String>();
		
		where.add(startHour);
		where.add(endHour);
		String[] simpleArray = new String[where.size()];
		String[] rangeH = where.toArray(simpleArray);
		
		rangeHours = new HashMap<String, String[]>();
		rangeHours.put("hours", rangeH);
		
	}

}
