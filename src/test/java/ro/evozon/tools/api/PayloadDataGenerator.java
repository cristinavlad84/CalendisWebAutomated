package ro.evozon.tools.api;

import javax.json.*;
import java.io.StringReader;
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
        return jsonObjectToString(empObj);

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
        return jsonObjectToString(empObj);


    }
    public static String createJsonObjectForUserSchedulePostRequestPayload(String dayMon, String dayTue, String dayWed, String dayThu, String dayFri, String daySat, String daySun,
                                                                    int location_id, int user_id ) {
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
        factory.add("location_id", location_id);
        factory.add("user_id", user_id);
        JsonArrayBuilder scheduleContainerArr = Json.createArrayBuilder();
        JsonArrayBuilder scheduleArr = Json.createArrayBuilder();
        JsonArrayBuilder hourContainerArr = Json.createArrayBuilder();
        for (int index = 0; index < daysOfweekList.size(); index++) {
            JsonArrayBuilder hourArr = Json.createArrayBuilder();
            hourArr.add(startHoursList.get(index));
            hourArr.add(endHoursList.get(index));
            hourContainerArr.add(hourArr);
            scheduleContainerArr.add(Json.createObjectBuilder().add("day", index + 1).add("hours", hourContainerArr));
        }
        scheduleArr.add(scheduleContainerArr);
        JsonArray arr5 = scheduleArr.build();
        System.out.println("array " + arr5.toString());
        factory.add("schedule", arr5.toString());
        JsonObject empObj = factory.build();

        return jsonObjectToString(empObj);

    }
    public static String jsonObjectToString(JsonObject obj){
        StringWriter strWtr = new StringWriter();
        JsonWriter jsonWtr = Json.createWriter(strWtr);
        jsonWtr.writeObject(obj);
        jsonWtr.close();
        System.out.println("to send" + strWtr.toString());
        return strWtr.toString();
    }
    public static String createJsonObjectForUserPostRequestPayload(String email, String name, String phone,
                                                            String role, String service_id, String domain_id, String location_id) {

        JsonObject serviceObject = Json.createObjectBuilder()
                .add("service", service_id)
                .add("domain", domain_id)
                .add("location", location_id)
                .add("class", "staff-service")
                .build();

        JsonArrayBuilder services = Json.createArrayBuilder()
                .add(serviceObject);
        JsonArray servArr = services.build();
        String specServices = servArr.toString();

        JsonObject specialistObject = Json.createObjectBuilder()
                .add("name", name)
                .add("full_name", name)
                .add("email", email)
                .add("phone", phone)
                .add("role", role)
                .add("services", specServices)
                .build();
        return jsonObjectToString(specialistObject);

    }
    public static String createJsonObjectForUserReceptionistPostRequestPayload(String email, String name, String phone,
                                                                   String role) {

        JsonObjectBuilder factory = Json.createObjectBuilder();
        factory.add("full_name", name);
        factory.add("email", email);
        factory.add("phone", phone);
        factory.add("role", role);

        JsonObject empObj = factory.build();
        return jsonObjectToString(empObj);

    }
    public static JsonObject jsonFromString(String jsonObjectStr) {

        JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        return object;
    }
    public static void main(String[] args){
        PayloadDataGenerator payloadData= new PayloadDataGenerator();
        System.out.println("sese");
        payloadData.createJsonObjectForUserSchedulePostRequestPayload("09:00-12:00","09:00-12:00","09:00-12:00","09:00-12:00","09:00-12:00","09:00-12:00","inchis",145,7474);
    }

}
