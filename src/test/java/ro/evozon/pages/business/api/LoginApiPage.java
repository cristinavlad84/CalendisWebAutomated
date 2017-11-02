package ro.evozon.pages.business.api;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import net.serenitybdd.core.pages.WebElementFacade;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import ro.evozon.AbstractPage;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.api.RestTestHelper;

import java.util.List;

import static net.serenitybdd.rest.RestRequests.given;

public class LoginApiPage extends AbstractPage {
	protected SoftAssertions softly = new SoftAssertions();
	public Response login(String email, String password){
		Header header = new Header("Content-Type", "application/x-www-form-urlencoded");
		Response responsee = given().formParam("email", email).formParam("password", password).header(header)
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

}
