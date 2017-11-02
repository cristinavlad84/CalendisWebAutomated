package ro.evozon.tools.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.models.RegionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.serenitybdd.rest.RestRequests.given;

public class ExtractRegionsNamesAndIds {
    public static RequestSpecBuilder builder;
    public static RequestSpecification requestSpec;

    public static void setupRequestSpecBuilder(Cookies cck) {
        builder = new RequestSpecBuilder();
        builder.addCookies(cck);
        requestSpec = builder.build();
    }
    public static Response addRegion(){
        Header header = new Header("Content-Type", "application/json");
        Response responsee = given().header(header).when()
                .get(ConfigUtils.getBaseUrl().concat(RestTestHelper.REGION_PATH));
       // responsee.then().log().everything();
        responsee.then().contentType(ContentType.JSON).statusCode(200);
        return responsee;
    }
    public static Optional<RegionModel> getRegionsNamesAndIdsFromResponse(String businessLocationName){
        Response regionResponse = addRegion();
        System.out.print("region response: " + regionResponse.prettyPrint());
        List<String> regionIdsList = regionResponse.body().jsonPath().get("regions.id");
        List<String> regionNamesList = regionResponse.body().jsonPath().get("regions.name");
        List<RegionModel> regionMapData = new ArrayList<>();
        regionMapData = RegionModel.createRegionModelFrom(regionNamesList, regionIdsList);
        Optional<RegionModel> businessRegionModel = regionMapData.stream().filter(p -> ConfigUtils.removeAccents(p.getName()).equalsIgnoreCase(businessLocationName)).findFirst();
        System.out.println("from excel region_id " + businessRegionModel.get().getId());
        return businessRegionModel;

    }
}
