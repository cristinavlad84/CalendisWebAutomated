package ro.evozon.features.business.registration;


import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.steps.serenity.rest.RestSteps;
import ro.evozon.tests.BaseTest;

import io.restassured.response.Response;

@RunWith(SerenityRunner.class)
public class ApiTest extends BaseTest{

    @Steps
    public RestSteps restSteps;

    @Test
    public void doTheAPICall(){
        Response response = restSteps.registerNewUser();

        System.out.print("data: " + response.getStatusCode());
        System.out.print("data: " + response.prettyPrint());
    }
}
