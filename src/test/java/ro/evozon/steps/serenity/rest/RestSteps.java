package ro.evozon.steps.serenity.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import ro.evozon.tools.api.RestTestHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONException;
import org.json.JSONObject;

import static io.restassured.RestAssured.get;
import static net.serenitybdd.rest.RestRequests.given;

public class RestSteps {
	protected SoftAssertions softly = new SoftAssertions();

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
				.post(RestTestHelper.BUSINESS2_BASE_URL.concat(RestTestHelper.SUBSCRIBE_PATH));

		// responsee.then().contentType(ContentType.TEXT).statusCode(200);
		System.out.println("status code" + responsee.getStatusCode() + responsee.getBody().asString());

		int message = responsee.body().jsonPath().get("success");
		System.out.println("success param is " + message);
		softly.assertThat(message).as("success param from subscribe response").isEqualTo(1);
		// given().contentType("application/x-www-form-urlencoded").cookies(cck).body(jsonObject.toString()).log()
		// .body().baseUri(baseUrl).basePath("/user/subscribe").when().post()
		return responsee;
	}

	@Step
	public Response createAccount(String businessName, String businessPhone, String categoryValue, String businessEmail,
			String password) {
		Header header = new Header("Content-Type", "application/x-www-form-urlencoded");
		Response responsee = given().formParam("name", businessName).formParam("phone", businessPhone)
				.formParam("category", categoryValue).formParam("email", businessEmail).formParam("psw", password)
				.formParam("cpsw", password).header(header).request()
				.post(RestTestHelper.BUSINESS2_BASE_URL.concat(RestTestHelper.CREATE_ACCOUNT_PATH));

		// responsee.then().contentType(ContentType.TEXT).statusCode(200);
		System.out.println("status code" + responsee.getStatusCode() + responsee.getBody().asString());

		int message = responsee.body().jsonPath().get("success");
		System.out.println("success param is " + message);
		softly.assertThat(message).as("success param from subscribe response").isEqualTo(1);
		// given().contentType("application/x-www-form-urlencoded").cookies(cck).body(jsonObject.toString()).log()
		// .body().baseUri(baseUrl).basePath("/user/subscribe").when().post()
		return responsee;
	}
}
