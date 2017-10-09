package ro.evozon.steps.serenity.rest;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import ro.evozon.AbstractPage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.serenitybdd.rest.RestRequests.given;
public class RestSteps {

    AbstractPage abstractPage;

    String baseUrl = "https://business2.calendis.ro";

//    public Set<Cookie> extractCookie(){
//        abstractPage.getDriver().get(baseUrl);
//        Set<Cookie> allMyCookies = abstractPage.getDriver().manage().getCookies();
//        return allMyCookies;
//    }

    @Step
    public Response registerNewUser() {
            int id = Math.abs(new Random().nextInt());
            Map<String, Object> bodyData = new HashMap<>();
            bodyData.put("category", 7);
            bodyData.put("name", "vvv");
            bodyData.put("email", "alienvsraptor@automation.33mail.com");
            bodyData.put("phone", "0268444336");
            bodyData.put("voucher", "");
//
//            Set<Cookie> allMyCookies = extractCookie();
//            //Cookies cookies = new Cookies();
//
//            Map<String, Cookie> cookieMap = new HashMap<String,Cookie>();
//        for (Cookie cookieNow:allMyCookies) {
//            System.out.println("----" + cookieNow.getName() + ":" + cookieNow.getValue());
//            cookieMap.put(cookieNow.getName(), cookieNow);
//        }

           // Cookie cookieData = new Cookie();
//        Response resp = get("/business2.calendis.ro/inregistrare");
       // Map<String, String> allCookies =  get("/business2.calendis.ro/inregistrare").getDetailedCookies();
//        List<Cookies> myCookiesList = allCookies.getC
//        myCookiesList = allCookies.getValues();

        return given().contentType("application/json").accept("*/*")
                    .body(bodyData).log().body()
                    .baseUri(baseUrl)
                    .basePath("/user/subscribe")
                    .when().post();
    }
}
