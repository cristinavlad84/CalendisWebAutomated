package ro.evozon;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.assertj.core.api.SoftAssertions;

public class AbstractApiSteps extends ScenarioSteps {

	private static final long serialVersionUID = 9210642586998408049L;
	private AbstractApiPage abstractApiPage;

	public SoftAssertions softly = new SoftAssertions();
	public static RequestSpecBuilder builder;
	public static RequestSpecification requestSpec;
	@Step
	public static void setupRequestSpecBuilder(Cookies cck) {
		builder = new RequestSpecBuilder();
		builder.addCookies(cck);
		requestSpec = builder.build();
	}

	@Step
	public void assertAll() {
		softly.assertAll();
	}






}
