package ro.evozon.steps.serenity.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractApiSteps;
import ro.evozon.AbstractSteps;
import ro.evozon.features.business.datadriven.api.deserializer.PermissionResponseData;
import ro.evozon.features.business.datadriven.api.serializer.Permissions;
import ro.evozon.pages.business.api.LoginApiPage;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Pair;
import ro.evozon.tools.Schedule;
import ro.evozon.tools.api.RestTestHelper;
import io.restassured.matcher.RestAssuredMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.assertj.core.api.SoftAssertions;

import static io.restassured.RestAssured.get;
import static net.serenitybdd.rest.RestRequests.given;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

public class RestSteps extends AbstractApiSteps {


    String baseUrl = "https://business2.calendis.ro";

    // public Set<Cookie> extractCookie(){
    // abstractPage.getDriver().get(baseUrl);
    // Set<Cookie> allMyCookies = abstractPage.getDriver().manage().getCookies();
    // return allMyCookies;
    // }

    @Step
    public Response registerNewUser(String categoryValue, String businessName, String businessEmail,
                                    String businessPhone) {
        Header header = new Header("Content-Type", "application/x-www-form-urlencoded");
        Response responsee = given().formParam("category", categoryValue).formParam("name", businessName)
                .formParam("email", businessEmail).formParam("phone", businessPhone).header(header).request()
                .post(ConfigUtils.getBaseUrl().concat(RestTestHelper.SUBSCRIBE_PATH));
        responsee.then().log().everything();
        responsee.then().contentType(ContentType.JSON).statusCode(200);
        int message = responsee.body().jsonPath().get("success");
        System.out.println("success param is " + message);
        softly.assertThat(message).as("success param from subscribe response").isEqualTo(1);
        // given().contentType("application/x-www-form-urlencoded").cookies(cck).body(jsonObject.toString()).log()
        // .body().baseUri(baseUrl).basePath("/user/subscribe").when().post()
        return responsee;
    }
    @Step
    public Response getAllPermissionIds(){
        Header header = new Header("Content-Type", "application/json");
        Response responsee = given().header(header).when()
                .get(ConfigUtils.getBaseUrl().concat(RestTestHelper.USER_PERMISSIONS_GET_PATH));

        return responsee;

    }
    @Step
    public Response addUserPermission(Permissions permissionData){
        Header header = new Header("Content-Type", "application/json");
        Response  respx = given().spec(requestSpec).header(header).body(permissionData).when()
                .post(ConfigUtils.getBaseUrl().concat(RestTestHelper.ADD_USER_PERMISSIONS_PATH));
        respx.then().log().everything();
        respx.then().body("id", notNullValue());
        respx.then().contentType(ContentType.JSON).statusCode(200);
        return respx;

    }

    @Step
    public Response createAccount(String businessName, String businessPhone, String categoryValue, String businessEmail,
                                  String password) {
        Header header = new Header("Content-Type", "application/x-www-form-urlencoded");
        Response responsee = given().formParam("name", businessName).formParam("phone", businessPhone)
                .formParam("category", categoryValue).formParam("email", businessEmail).formParam("psw", password)
                .formParam("cpsw", password).header(header).request()
                .post(ConfigUtils.getBaseUrl().concat(RestTestHelper.CREATE_ACCOUNT_PATH));
        responsee.then().log().everything();
        responsee.then().contentType(ContentType.JSON).statusCode(200);
        // System.out.println("status code" + responsee.getStatusCode() +
        // responsee.getBody().asString());
        return responsee;
    }

    @Step
    public Response login(String businessEmail, String password) {
        Header header = new Header("Content-Type", "application/x-www-form-urlencoded");
        Response responsee = given().formParam("email", businessEmail).formParam("password", password).header(header)
                .request().post(ConfigUtils.getBaseUrl().concat(RestTestHelper.LOGIN_ACCOUNT_PATH));
        responsee.then().log().everything();
        responsee.then().contentType(ContentType.JSON).statusCode(200);
        // System.out.println("status code" + responsee.getStatusCode() +
        // responsee.getBody().asString());
        int message = responsee.body().jsonPath().get("success");
        System.out.println("success param is " + message);
        softly.assertThat(message).as("success param from subscribe response").isEqualTo(1);
        return responsee;
    }

    @Step
    public Response addCounty(String county) {
        Header header = new Header("Content-Type", "application/json");
        Response responsee = given().queryParam("region_id", county).when()
                .get(ConfigUtils.getBaseUrl().concat(RestTestHelper.COUNTY_PATH + county));
        responsee.then().log().everything();
        responsee.then().contentType(ContentType.JSON).statusCode(200);

        // System.out.println("success param is " + message);
        return responsee;
    }

    @Step
    public Response addRegion() {
        Header header = new Header("Content-Type", "application/json");
        Response responsee = given().header(header).when()
                .get(ConfigUtils.getBaseUrl().concat(RestTestHelper.REGION_PATH));
        responsee.then().log().everything();
        responsee.then().contentType(ContentType.JSON).statusCode(200);

        // System.out.println("success param is " + message);
        return responsee;
    }



    @Step
    public Response addLocationParameterized(String content) {

        Header header = new Header("Content-Type", "application/json");
        Response respx = given().spec(requestSpec).header(header).body(content).when()
                .post(ConfigUtils.getBaseUrl().concat(RestTestHelper.LOCATION_PATH));

        respx.then().log().everything();

        respx.then().body("id", notNullValue());

        respx.then().contentType(ContentType.JSON).statusCode(200);

        return respx;

    }

    @Step
    public Response addDomain(String location_id, String domainName) {
        String[] services = {};
        Header header = new Header("Content-Type", "application/json");
        // Response response = given().cookies(cck).header(header).body(new
        // HashMap<String, String>() {
        // {
        // //put("domain_id","0");
        // put("location_id", location_id);
        // put("name", domainName);
        // //put("services",services);
        // }
        // }).when().post(RestTestHelper.BUSINESS2_BASE_URL.concat(RestTestHelper.DOMAIN_PATH));
        JsonBuilderFactory factory = Json.createBuilderFactory(Collections.emptyMap());
        JsonObject newObject = factory.createObjectBuilder().add("location_id", location_id).add("name", domainName)
                .build();
        Response response = given().spec(requestSpec).header(header).body(newObject.toString())
                .post(ConfigUtils.getBaseUrl().concat(RestTestHelper.DOMAIN_PATH));
        response.then().log().everything();
        response.then().body("id", notNullValue());
        response.then().contentType(ContentType.JSON).statusCode(200);
        return response;
    }

    @Step
    public Response addService(String content) {
        String[] services = {};
        Header header = new Header("Content-Type", "application/json");


        Response response = given().spec(requestSpec).header(header).body(content)
                .post(ConfigUtils.getBaseUrl().concat(RestTestHelper.SERVICE_PATH));
        response.then().log().everything();
        response.then().body("id", notNullValue());
        response.then().contentType(ContentType.JSON).statusCode(200);
        return response;
    }

    @Step
    public Response addStaff(String content) {
        Header header = new Header("Content-Type", "application/json");
        Response response = given().spec(requestSpec).header(header).body(content)
                .post(ConfigUtils.getBaseUrl().concat(RestTestHelper.USER_PATH));
        response.then().log().everything();
        response.then().body("id", notNullValue());
        response.then().contentType(ContentType.JSON).statusCode(200);
        return response;
    }
    @Step
    public Response addScheduleStaff(String content) {

        Header header = new Header("Content-Type", "application/json");
        ;
        Response respx = given().spec(requestSpec).header(header).body(content).when()
                .post(ConfigUtils.getBaseUrl().concat(RestTestHelper.SCHEDULE_PATH));

        respx.then().log().everything();

        respx.then().body("id", notNullValue());

        respx.then().contentType(ContentType.JSON).statusCode(200);

        return respx;

    }
    @Step
    public Response accessActivationLink(String url) {
        Response response = get(url);
        response.then().log().everything();
        response.then().contentType(ContentType.JSON).statusCode(200);
        // System.out.println("status code" + response.getStatusCode() +
        // response.getBody().asString());

        return response;
    }
}
