package ro.evozon.features.business.registration.api;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.JsonConverter;
import net.thucydides.core.annotations.Steps;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.steps.serenity.rest.RestSteps;
import ro.evozon.tests.BaseTest;
import ro.evozon.tools.Categories;
import ro.evozon.tools.Cities;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.Counties;
import ro.evozon.tools.FieldGenerators;
import ro.evozon.tools.Pair;
import ro.evozon.tools.PhonePrefixGenerators;
import ro.evozon.tools.Tools;
import ro.evozon.tools.models.CityModel;
import ro.evozon.tools.FieldGenerators.Mode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@RunWith(SerenityRunner.class)
public class ApiTest extends BaseTest {
	private int businessCategory;
	private String businessName;
	private String businessEmail;
	private String businessPhone;
	private String businessPassword;
	private int businessCounty;
	private String businessCity;
	private String businessCountyName;
	private String businessDomainName;

	public ApiTest() {
		super();
		// TODO Auto-generated constructor stub
		this.businessCategory = Categories.getRandomCategory().getOption();
		this.businessName = FieldGenerators.generateRandomString(6, Mode.ALPHA);
		this.businessEmail = "blowingwins4@yopmail.com";
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

	}

	@Steps
	public RestSteps restSteps;

	@Test
	public void registerFlowAPICall() {
//		 Response response =
//		 restSteps.registerNewUser(Integer.toString(businessCategory), businessName,
//		 businessEmail,
//		 businessPhone);
//		
//		 System.out.print("data: " + response.getStatusCode());
//		 System.out.print("register response: " + response.prettyPrint());
		//
		// // aceesss email
		// Tools emailExtractor = new Tools();
		// String link = "";

		// Tools.RetryOnExceptionStrategy retry = new Tools.RetryOnExceptionStrategy();
		// while (retry.shouldRetry()) {
		// try {
		// link =
		// emailExtractor.getLinkFromEmails(Constants.BUSINESS_GMAIL_BASE_ACCOUNT_SUFFIX,
		// Constants.GMAIL_BUSINESS_BASE_PSW,
		// Constants.NEW_BUSINESS_ACCOUNT_SUCCESS_MESSAGE_SUBJECT,
		// Constants.LINK__BUSINESS_ACTIVATE, businessEmail);
		// break;
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// try {
		// System.out.println("in catch.....");
		// retry.errorOccured();
		// } catch (RuntimeException e1) {
		// throw new RuntimeException("Exception while searching email:", e);
		// } catch (Exception e1) {
		// throw new RuntimeException(e1);
		// }
		//
		// }
		// }
		// String link2 = emailExtractor.editBusinessActivationLink(link,
		// ConfigUtils.getBusinessEnvironment());
		// do create_account api call
		// System.out.println("password" + businessPassword);
//		 Response createAccountResponse = restSteps.createAccount(businessName,
//		 businessPhone,
//		 Integer.toString(businessCategory), businessEmail, businessPassword);
//		 System.out.print("create account response: " +
//		 createAccountResponse.prettyPrint());
		RequestSpecBuilder builder = new RequestSpecBuilder();
		Response loginResponse = restSteps.login(businessEmail, businessPassword);
		Cookies cck = loginResponse.getDetailedCookies();
		builder.addCookies(cck);
		RequestSpecification requestSpec = builder.build();
		Response addCountyResponse = restSteps.addCounty(Integer.toString(businessCounty));

		List<String> citiesNamesList = addCountyResponse.body().jsonPath().get("cities.name");
		List<String> citiesIdsList = addCountyResponse.body().jsonPath().get("cities.id");
		List<CityModel> mapData = new ArrayList<CityModel>();
		mapData = CityModel.createCityModelFrom(citiesNamesList, citiesIdsList);
		mapData.stream().forEach(k -> System.out.println("city name" + k.getName() + "city id " + k.getId()));

		//data test to be parameterized
		List<String> daysOfweekStaffList = new ArrayList<String>();
		List<String> daysOfweekList = new ArrayList<String>();
		daysOfweekStaffList.add("09:00-17:00");
		daysOfweekStaffList.add("10:00-17:30");
		daysOfweekStaffList.add("10:30-17:45");
		daysOfweekStaffList.add("11:00-18:00");
		daysOfweekStaffList.add("11:30-18:30");
		daysOfweekStaffList.add("12:00-19:00");
		daysOfweekStaffList.add("inchis");
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
		factory.add("address", "str Leul cel Mic 8");
		factory.add("city_id", "4185");
		factory.add("city_name", "Balta Alba");
		factory.add("region_id", "16");
		factory.add("phone", "0264555889");
		factory.add("name", "Kamigo77");
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
		 Response addLocationResponse =
		 restSteps.addLocationParameterized(cck,strWtr.toString() );
		// // Response addLocationResponse = restSteps
		// String businessLocationId=addLocationResponse.body().jsonPath().get("id");
		// String businessLocationId="2264";
		// System.out.println("business location id "+businessLocationId);
		// Response adDdomainResponse = restSteps.addDomain(cck,businessLocationId,
		// businessDomainName);
		// String domainId= adDdomainResponse.body().jsonPath().getString("id");
		// System.out.println("domain id "+domainId);
		restSteps.assertAll();
	}
}
