package ro.evozon.tools.api;

import javax.json.*;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PayloadDataGenerator {
    public static String createJsonObjectForLocationRequestPayload(String dayMon, String dayTue, String dayWed, String dayThu, String dayFri, String daySat, String daySun, String address, String cityId, String cityName, String regionId, String phone, String name) {
        List<String> daysOfweekStaffList = new ArrayList<String>();
        List<String> daysOfweekList = new ArrayList<String>();
        daysOfweekStaffList.add(dayMon);
        daysOfweekStaffList.add(dayTue);
        daysOfweekStaffList.add(dayWed);
        daysOfweekStaffList.add(dayThu);
        daysOfweekStaffList.add(dayFri);
        daysOfweekStaffList.add(daySat);
        daysOfweekStaffList.add(daySun);
        daysOfweekList = daysOfweekStaffList.stream()
                .filter(k -> !k.contentEquals(ro.evozon.tools.Constants.CLOSED_SCHEDULE)).collect(Collectors.toList());
        List<String> startHoursList = new ArrayList<>();
        List<String> endHoursList = new ArrayList<>();
        for (int k = 0; k < daysOfweekList.size(); k++) {
            String[] hours = daysOfweekList.get(k).split("-");

            startHoursList.add(hours[0]);
            endHoursList.add(hours[1]);

        }
        JsonObjectBuilder factory = Json.createObjectBuilder();
        factory.add("address", address);
        factory.add("city_id", cityId);
        factory.add("city_name", cityName);
        factory.add("region_id", regionId);
        factory.add("phone",
                phone);
        factory.add("name", name);
        JsonArrayBuilder scheduleArr = Json.createArrayBuilder();
        JsonArrayBuilder hourContainerArr = Json.createArrayBuilder();
        for (int index = 0; index < daysOfweekList.size(); index++) {
            JsonArrayBuilder hourArr = Json.createArrayBuilder();
            hourArr.add(startHoursList.get(index));
            hourArr.add(endHoursList.get(index));
            hourContainerArr.add(hourArr);
            scheduleArr.add(Json.createObjectBuilder().add("day", index + 1).add("hours", hourContainerArr));
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
        return strWtr.toString();
    }
    public static String createJsonObjectForServicePostRequestPayload(String location_id, String domain_id, String serviceName,
                                                               String duration, String maxUsers, String price) {
        JsonObjectBuilder factory = Json.createObjectBuilder();
        factory.add("domain_id", domain_id);
        factory.add("duration", duration);
        factory.add("max_users", maxUsers);
        factory.add("name", serviceName);
        factory.add("price", price);

        JsonArrayBuilder locationArr = Json.createArrayBuilder();
        locationArr.add(location_id);

        factory.add("locations", locationArr);

        JsonObject empObj = factory.build();
        StringWriter strWtr = new StringWriter();
        JsonWriter jsonWtr = Json.createWriter(strWtr);
        jsonWtr.writeObject(empObj);
        jsonWtr.close();
        System.out.println("to send" + strWtr.toString());
        return strWtr.toString();

    }

    public static String createJsonObjectForUserPostRequestPayload(String dayMon, String dayTue, String dayWed, String dayThu, String dayFri, String daySat, String daySun, String email, String name, String phone,
                                                            String role, String service_id, String domain_id, String location_id) {
        List<String> daysOfweekStaffList = new ArrayList<String>();
        List<String> daysOfweekList = new ArrayList<String>();
        daysOfweekStaffList.add(dayMon);
        daysOfweekStaffList.add(dayTue);
        daysOfweekStaffList.add(dayWed);
        daysOfweekStaffList.add(dayThu);
        daysOfweekStaffList.add(dayFri);
        daysOfweekStaffList.add(daySat);
        daysOfweekStaffList.add(daySun);
        daysOfweekList = daysOfweekStaffList.stream()
                .filter(k -> !k.contentEquals(ro.evozon.tools.Constants.CLOSED_SCHEDULE)).collect(Collectors.toList());
        List<String> startHoursList = new ArrayList<>();
        List<String> endHoursList = new ArrayList<>();
        for (int k = 0; k < daysOfweekList.size(); k++) {
            String[] hours = daysOfweekList.get(k).split("-");

            startHoursList.add(hours[0]);
            endHoursList.add(hours[1]);

        }
        JsonObjectBuilder factory = Json.createObjectBuilder();
        factory.add("full_name", name);
        factory.add("email", email);
        factory.add("phone", phone);
        factory.add("role", role);
        JsonArrayBuilder scheduleArr = Json.createArrayBuilder();
        JsonArrayBuilder hourContainerArr = Json.createArrayBuilder();
        for (int index = 0; index < daysOfweekList.size(); index++) {
            JsonArrayBuilder hourArr = Json.createArrayBuilder();
            hourArr.add(startHoursList.get(index));
            hourArr.add(endHoursList.get(index));
            hourContainerArr.add(hourArr);
            scheduleArr.add(Json.createObjectBuilder().add("day", index + 1).add("hours", hourContainerArr));
        }
        JsonArray arr5 = scheduleArr.build();
        System.out.println("array " + arr5.toString());
        factory.add("schedules", arr5.toString());
        JsonArrayBuilder servicesArr = Json.createArrayBuilder();
        servicesArr.add(Json.createObjectBuilder().add("service", Integer.parseInt(service_id))
                .add("domain", Integer.parseInt(domain_id))
                .add("location", Integer.parseInt(location_id))
                .add("class", "staff-service").build());
        JsonArray arrService = servicesArr.build();
        factory.add("services", arrService.toString());

        JsonObject empObj = factory.build();
        StringWriter strWtr = new StringWriter();
        JsonWriter jsonWtr = Json.createWriter(strWtr);
        jsonWtr.writeObject(empObj);
        jsonWtr.close();
        System.out.println("to send array" + servicesArr.toString());
        System.out.println("to send" + strWtr.toString());
        return strWtr.toString();

    }
}
