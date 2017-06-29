package ro.evozon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Outsie {
	public static String fox;
	public static String dog;
	
	
	
	public static void modify(String color, String blu){
		System.out.println("inside");
		System.out.println(fox);
		System.out.println(dog);
		fox= color;
		dog=blu;
	}
	public static void main(String[]args){
		Map<String, String> steps1 = new HashMap<>();
		steps1.put("key11", "value11");
		steps1.put("key12", "value12");

		Map<String, String> steps2 = new HashMap<>();
		steps2.put("key21", "value21");
		steps2.put("key22", "value22");

		List<Map<String, String>> steps = new ArrayList<>();
		steps.add(steps1);
		steps.add(steps2);

		Map<String, String> result = new HashMap<>();
		steps.stream().forEach(map -> {
		    result.putAll(map.entrySet().stream()
		        .collect(Collectors.toMap(entry -> entry.getKey(), entry -> (String) entry.getValue())));
		});
		System.out.println(result);
		
	}

}
