package ro.evozon.tools.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import ro.evozon.tools.utils.Time24HoursValidator;

public class GsonProgram {

	public static void main(String... args) throws IOException {
		try {
			InputStream fis = new FileInputStream("src/test/java/ro/evozon/tools/api/" + "locatioPayload.json");
			JsonReader jsonReader = Json.createReader(fis);
			JsonArray jSonArray = jsonReader.readArray();

			jsonReader.close();
			fis.close();
			System.out.println(jSonArray.get(0));

			// reading arrays from json

			JsonObject[] list = new JsonObject[jSonArray.size()];
			// String [] prj = new String[jsonArrayData.size()];
			int index = 0;
			for (JsonValue value : jSonArray) {

				// JsonArray jSonNestedArray =

				System.out.println(value.toString());
				// System.out.println(jsonObj.getInt("city_name"));

			}
			for (int i = 0; i < jSonArray.size(); i++) {
				list[i] = jSonArray.getJsonObject(i);
				// System.out.println(jSonArray.getJsonObject(i).get("day"));
				// System.out.println(jSonArray.getJsonObject(i).get("hours"));
				JSONArray jsonArr2 = new JSONArray(jSonArray.getJsonObject(i).get("hours").toString());

				jsonArr2.forEach(k -> System.out.println(k));
				System.out.println("!!!" + jsonArr2.get(0));
				JSONArray jsonArr3 = new JSONArray(jsonArr2.get(0).toString());
				System.out.println(jsonArr3.get(0));
				System.out.println(jsonArr3.get(1));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> daysOfweekStaffList = new ArrayList<String>();
		List<String> daysOfweekList = new ArrayList<String>();
		daysOfweekStaffList.add("09:00-17:00");
		daysOfweekStaffList.add("10:00-17:30");
		daysOfweekStaffList.add("10:30-17:45");
		daysOfweekStaffList.add("11:00-18:00");
		daysOfweekStaffList.add("11:30-18:30");
		daysOfweekStaffList.add("12:00-19:00");
		daysOfweekStaffList.add("inchis");
		daysOfweekStaffList.stream().forEach(k -> System.out.println("orar " + k));
		System.out.println("size =" + daysOfweekStaffList.size());
		// for (int i = 0; i < daysOfweekStaffList.size(); i++) {
		// System.out.println("i = " + i);
		// if
		// (!daysOfweekStaffList.get(i).contentEquals(ro.evozon.tools.Constants.CLOSED_SCHEDULE))
		// {
		// daysOfweekList.add(daysOfweekStaffList.get(i));
		// }
		// }
		daysOfweekList = daysOfweekStaffList.stream()
				.filter(k -> !k.contentEquals(ro.evozon.tools.Constants.CLOSED_SCHEDULE)).collect(Collectors.toList());
		daysOfweekList.stream().forEach(l -> System.out.println("sdassds" + l));
		// String entireH =
		// daysOfweekList.stream().map(Object::toString).collect(Collectors.joining(","));
		// Collection<String> hoursCollection = Arrays.asList(entireH);
		// List<String> splitBy = Stream.of(entireH).map(w ->
		// w.split("-")).flatMap(Arrays::stream)
		// .collect(Collectors.toList());
		// splitBy.stream().forEach(k->System.out.println(k));
		List<String> startHoursList = new ArrayList<>();
		List<String> endHoursList = new ArrayList<>();
		for (int k = 0; k < daysOfweekList.size(); k++) {
			String[] hours = daysOfweekList.get(k).split("-");

			startHoursList.add(hours[0]);
			endHoursList.add(hours[1]);

		}
		JsonObjectBuilder factory = Json.createObjectBuilder();
		factory.add("address", "str ion creanga 7");
		factory.add("city_id", "4185");
		factory.add("city_name", "Balta Alba");
		factory.add("region_id", "16");
		factory.add("phone", "0264555889");
		factory.add("name", "robusta77");
		JsonArrayBuilder scheduleArr = Json.createArrayBuilder();
		JsonArrayBuilder hourContainerArr = Json.createArrayBuilder();
		for (int index = 0; index < daysOfweekList.size(); index++) {
			JsonArrayBuilder hourArr = Json.createArrayBuilder();
			hourArr.add(startHoursList.get(index));
			hourArr.add(endHoursList.get(index));
			hourContainerArr.add(hourArr);
			scheduleArr.add(Json.createObjectBuilder().add("day", index+1).add("hours", hourContainerArr));
		}
		JsonArray arr5 = scheduleArr.build();
		System.out.println("array " + arr5.toString());
		factory.add("schedule", arr5.toString());
		JsonObject empObj = factory.build();
		StringWriter strWtr = new StringWriter();
		JsonWriter jsonWtr = Json.createWriter(strWtr);
		jsonWtr.writeObject(empObj);
		jsonWtr.close();

		System.out.println(strWtr.toString());

	}
}
