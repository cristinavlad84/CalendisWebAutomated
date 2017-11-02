package ro.evozon.features.business.registration.api;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.steps.serenity.rest.RestSteps;
import ro.evozon.tests.BaseApiTest;
import ro.evozon.tests.BaseTest;
import ro.evozon.tools.*;
import ro.evozon.tools.models.CityModel;
import ro.evozon.tools.models.RegionModel;
import ro.evozon.tools.FieldGenerators.Mode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static com.jayway.awaitility.Awaitility.await;
@RunWith(SerenityRunner.class)
public class ApiTest extends BaseApiTest {
    @Steps
    public RestSteps restSteps;
    private int businessCategory;
    private String businessName;
    private String businessEmail;
    private String businessPhone;
    private String businessPassword;
    private int businessCounty;
    private String businessCity;
    private String businessAddress;
    private String businessCountyName;
    private String businessDomainName;
    private String businessLocationId;
    private String serviceName;
    private String maxUsers, servicePrice;
    private int serviceDuration;
    private String domainId;
    private String serviceId;
    private String specialistEmail, specialistName, specialistPhone;
    private String businessRegion = "Bacau";
    private String businessCityName = "Poiana";
    public static RequestSpecBuilder builder;
    public static RequestSpecification requestSpec;
    public ApiTest() {
        super();
        // TODO Auto-generated constructor stub
        this.businessCategory = Categories.getRandomCategory().getOption();
        this.businessName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
        this.businessEmail = "littlemarmais49@yopmail.com";
        // FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
        // +
        // FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.BUSINESS_FAKE_MAIL_DOMAIN);
        this.businessPhone = PhonePrefixGenerators.generatePhoneNumber();
        this.businessPassword = "Calendis";
        // FieldGenerators.generateRandomString(8, Mode.ALPHANUMERIC);

        Counties county = Counties.getRandomCounty();
        businessCountyName = county.toString();
        System.out.println("county name " + businessCountyName);
        this.businessCounty = county.getOption();
        System.out.println("county id " + businessCounty);
        this.businessDomainName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
        this.businessAddress = FieldGenerators.generateRandomString(6, Mode.ALPHA);
        this.serviceName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
        this.maxUsers = Integer.toString(FieldGenerators.getRandomIntegerBetween(1, 100));
        this.servicePrice = new DecimalFormat("#.00").format(
                FieldGenerators.getRandomDoubleBetween(Constants.MIN_SERVICE_PRICE, Constants.MAX_SERVICE_PRICE));
        this.serviceDuration = FieldGenerators.getRandomIntegerBetween(3, 12) * 5; // from
        // 3
        // for
        // client
        // preview
        // appoitment
        // in
        // calendar->
        // to
        // be
        // visible
        this.specialistEmail = FieldGenerators.generateRandomString(3, Mode.ALPHA).toLowerCase()
                + FieldGenerators.generateUniqueValueBasedOnDateStamp().concat(Constants.STAFF_FAKE_DOMAIN);
        this.specialistName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
        this.specialistPhone = PhonePrefixGenerators.generatePhoneNumber();
    }

    public  void setupRequestSpecBuilder(Cookies cck){
        builder = new RequestSpecBuilder();
        builder.addCookies(cck);
        requestSpec = builder.build();
    }

    @Test
    public void registerFlowAPICall() {
        Response response = restSteps.registerNewUser(Integer.toString(businessCategory), businessName, businessEmail,
				businessPhone);
		Response createAccountResponse = restSteps.createAccount(businessName, businessPhone,
				Integer.toString(businessCategory), businessEmail, businessPassword);
		System.out.print("create account response: " + createAccountResponse.prettyPrint());
        RequestSpecBuilder builder = new RequestSpecBuilder();
        Response loginResponse = restSteps.login(businessEmail, businessPassword);
        Cookies cck = loginResponse.getDetailedCookies();
        setupRequestSpecBuilder(cck);


        //get list of all region id's
        Response regionResponse = restSteps.addRegion();
        List<String> regionIdsList = regionResponse.body().jsonPath().get("regions.id");
        List<String> regionNamesList = regionResponse.body().jsonPath().get("regions.name");
        List<RegionModel> regionMapData = new ArrayList<>();
        regionMapData = RegionModel.createRegionModelFrom(regionNamesList, regionIdsList);
        regionMapData.stream().forEach(k -> System.out.println("region name" + k.getName() + "region id " + k.getId()));
        RegionModel randomRegionModel = (RegionModel) FieldGenerators.getRandomOptionFrom(regionMapData);
        System.out.println("random region id " + randomRegionModel.getId());
        System.out.println("random city name " + randomRegionModel.getName());
        Optional<RegionModel> businessRegionModel = regionMapData.stream().filter(p -> ConfigUtils.removeAccents(p.getName()).equalsIgnoreCase(businessRegion)).findFirst();
        System.out.println("Bacau region "+businessRegionModel.get().getId());
        //end
        Response addCountyResponse = restSteps.addCounty(businessRegionModel.get().getId());

        List<String> citiesNamesList = addCountyResponse.body().jsonPath().get("cities.name");
        List<String> citiesIdsList = addCountyResponse.body().jsonPath().get("cities.id");
        List<CityModel> mapData = new ArrayList<CityModel>();
        mapData = CityModel.createCityModelFrom(citiesNamesList, citiesIdsList);
        mapData.stream().forEach(k -> System.out.println("city name" + k.getName() + "city id " + k.getId()));
        CityModel randomCityModel = (CityModel) FieldGenerators.getRandomOptionFrom(mapData);
        System.out.println("random city id " + randomCityModel.getId());
        System.out.println("random city name " + randomCityModel.getName());
        Optional<CityModel> businessCityModel = mapData.stream().filter(p -> ConfigUtils.removeAccents(p.getName()).equalsIgnoreCase(businessCityName)).findFirst();
        //end
        System.out.println("Poiana city id "+businessCityModel.get().getId());
        // data test to be parameterized
        String locationContent = createJsonObjectForLocationRequestPayload("09:00-17:00","09:25-17:30", "10:00-18:00","10:25-18:30","11:00-19:00", "11:25-19:30","inchis",businessAddress, businessCityModel.get().getId(), businessCityModel.get().getName(), businessRegionModel.get().getId(), businessPhone, businessName);
        Response addLocationResponse = restSteps.addLocationParameterized(locationContent.toString());
        // // Response addLocationResponse = restSteps
        businessLocationId = addLocationResponse.body().jsonPath().get("id");
        // String businessLocationId="2264";
        System.out.println("business location id " + businessLocationId);
        Response adDdomainResponse = restSteps.addDomain(businessLocationId, businessDomainName);
        domainId = adDdomainResponse.body().jsonPath().getString("id");
        System.out.println("domain id " + domainId);
        String serviceContent = createJsonObjectForServicePostRequestPayload(businessLocationId, domainId, serviceName,
                Integer.toString(serviceDuration), maxUsers, servicePrice);
        Response addServiceResponse = restSteps.addService(serviceContent);
        serviceId = addServiceResponse.body().jsonPath().get("id");
        String userContent = createJsonObjectForUserPostRequestPayload("09:00-17:00","09:30-17:30", "10:00-18:00","10:30-18:30","11:00-19:00", "11:30-19:30","inchis",specialistEmail, specialistName, specialistPhone,
                StaffType.EMPL.toString(), serviceId, domainId, businessLocationId);
        System.out.println("content " + userContent);
        Response addStaffResponse = restSteps.addStaff(userContent);
        String userId=addStaffResponse.body().jsonPath().get("user_id");

        restSteps.assertAll();
    }

    public String createJsonObjectForLocationRequestPayload(String dayMon, String dayTue, String dayWed, String dayThu, String dayFri, String daySat, String daySun,String address, String cityId, String cityName, String regionId, String phone, String name) {
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


    public String createJsonObjectForServicePostRequestPayload(String location_id, String domain_id, String serviceName,
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

    public String createJsonObjectForUserPostRequestPayload(String dayMon, String dayTue, String dayWed, String dayThu, String dayFri, String daySat, String daySun, String email, String name, String phone,
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
