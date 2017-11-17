package ro.evozon.features.business.datadriven.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.UserStoryCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.AbstractApiSteps;
import ro.evozon.steps.serenity.rest.RestSteps;
import ro.evozon.tests.BaseApiTest;
import ro.evozon.tests.BaseTest;
import ro.evozon.tools.enums.Categories;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.enums.StaffType;
import ro.evozon.tools.models.CityModel;
import ro.evozon.tools.models.DataModel;
import ro.evozon.tools.models.RegionModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static ro.evozon.features.business.datadriven.ParseXlsxUtils.parseExcelFile;
import static ro.evozon.features.business.datadriven.ParseXlsxUtils.writeToPropertiesFile;
import static ro.evozon.tools.ConfigUtils.getOutputFileNameForNewBusinessApiFromXlsx;
import static ro.evozon.tools.api.PayloadDataGenerator.*;

@UserStoryCode("US01")
@Narrative(text = {"In order to use business platform", "As business user ",
        "I want to be able to register and activate account via email link then login into account and complete registration wizard"})
@RunWith(SerenityRunner.class)
public class CreateNewBusinessAccountWithRealTestDataAPIStory extends BaseTest {
    public static RequestSpecBuilder builder;
    public static RequestSpecification requestSpec;
    public static List<DataModel> modelData = new ArrayList<DataModel>();
    public String businessAddress;
    public String businessMainLocation;
    public String businessMainDomain;
    public String businessFirstService;
    public String businessFirstServicePrice;
    public String businessPhoneNo;
    public String firstAddedSpecialistName;
    public String firstAddedSpecialistPhone, firstServiceMaxPersons;
    public String firstAddedSpecialistEmail;
    public String firstAddedSpecialistPassword;
    public String businessName;
    public String businessEmail;
    public String businessPassword;
    public String businessCategory;
    public String businessFirstServiceDuration;
    public String businessMainLocationCounty;
    public String businessMainLocationCity;
    public String locationScheduleMon, locationScheduleTue, locationScheduleWed, locationScheduleThu,
            locationScheduleFri, locationScheduleSat, locationScheduleSun;
    public String staffScheduleMon, staffScheduleTue, staffScheduleWed, staffScheduleThu, staffScheduleFri,
            staffScheduleSat, staffScheduleSun;
    public String businessLocationId, businessDomainId, serviceId, staffId;
    @Steps
    public RestSteps restSteps;

    public CreateNewBusinessAccountWithRealTestDataAPIStory() {

    }

    @Before
    public void readFromFile() {
        parseExcelFile(Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForApiXlsxFile(), Constants.OUTPUT_PATH_DATA_DRIVEN_API);
        String file = getOutputFileNameForNewBusinessApiFromXlsx();
        writeToPropertiesFile(Constants.OUTPUT_PATH, file);
        String fileName = Constants.OUTPUT_PATH + getOutputFileNameForNewBusinessApiFromXlsx();
        Properties props = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(fileName);
            props.load(input);
            businessName = props.getProperty("businessName", businessName);
            businessCategory = props.getProperty("businessCategory", businessCategory);
            businessPhoneNo = props.getProperty("businessPhoneNo", businessPhoneNo);
            businessEmail = props.getProperty("businessEmail", businessEmail);
            businessPassword = props.getProperty("businessPassword", businessPassword);
            businessAddress = props.getProperty("businessAddress", businessAddress);
            businessMainLocation = props.getProperty("businessMainLocation", businessMainLocation);
            businessMainLocationCounty = props.getProperty("businessMainLocationCounty", businessMainLocationCounty);
            businessMainLocationCity = props.getProperty("businessMainLocationCity", businessMainLocationCity);
            businessMainDomain = props.getProperty("businessMainDomain", businessMainDomain);
            businessFirstService = props.getProperty("businessFirstService", businessFirstService);
            businessFirstServicePrice = props.getProperty("businessFirstServicePrice", businessFirstServicePrice);
            businessFirstServiceDuration = props.getProperty("businessFirstServiceDuration",
                    businessFirstServiceDuration);
            firstServiceMaxPersons = props.getProperty("firstServiceMaxPersons", firstServiceMaxPersons);
            firstAddedSpecialistName = props.getProperty("firstAddedSpecialistName", firstAddedSpecialistName);
            firstAddedSpecialistEmail = props.getProperty("firstAddedSpecialistEmail", firstAddedSpecialistEmail);
            firstAddedSpecialistPhone = props.getProperty("firstAddedSpecialistPhone", firstAddedSpecialistPhone);
            firstAddedSpecialistPassword = props.getProperty("firstAddedSpecialistPassword",
                    firstAddedSpecialistPassword);
            locationScheduleMon = props.getProperty("orar_sediu_luni", locationScheduleMon);
            locationScheduleTue = props.getProperty("orar_sediu_marti", locationScheduleTue);
            locationScheduleWed = props.getProperty("orar_sediu_miercuri", locationScheduleWed);
            locationScheduleThu = props.getProperty("orar_sediu_joi", locationScheduleThu);
            locationScheduleFri = props.getProperty("orar_sediu_vineri", locationScheduleFri);
            locationScheduleSat = props.getProperty("orar_sediu_sambata", locationScheduleSat);
            locationScheduleSun = props.getProperty("orar_sediu_duminica", locationScheduleSun);
            staffScheduleMon = props.getProperty("orar_angajat_luni", locationScheduleMon);
            staffScheduleTue = props.getProperty("orar_angajat_marti", locationScheduleTue);
            staffScheduleWed = props.getProperty("orar_angajat_miercuri", locationScheduleWed);
            staffScheduleThu = props.getProperty("orar_angajat_joi", locationScheduleThu);
            staffScheduleFri = props.getProperty("orar_angajat_vineri", locationScheduleFri);
            staffScheduleSat = props.getProperty("orar_angajat_sambata", locationScheduleSat);
            staffScheduleSun = props.getProperty("orar_angajat_duminica", locationScheduleSun);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @After
    public void appendToPropertiesFile() {
        FileOutputStream fileOut = null;
        FileInputStream writer = null;
        try {

            String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForNewBusinessApiFromXlsx();
            Properties props = new Properties();
            File file = new File(fileName);
            writer = new FileInputStream(file);
            props.load(writer);
            props.setProperty("businessLocationId", businessLocationId);
            props.setProperty("businessDomainId", businessDomainId);
            props.setProperty("serviceId", serviceId);
            props.setProperty("staffId", staffId);
            fileOut = new FileOutputStream(file);
            props.store(fileOut, "business user details");
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /**
         * add user name and user id as key value pairs in properties file
         */
        OutputStream output = null;
        try {

            String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForUsersIds();
            Properties props = new Properties();
            File file = new File(fileName);
            if (file.exists()) {
                writer = new FileInputStream(file);
                props.load(writer);
                props.setProperty(firstAddedSpecialistName, staffId);
                fileOut = new FileOutputStream(file);
                props.store(fileOut, " user id  details");
                writer.close();
            }

            // writer.close();
            else { //if file doesn't exist,create one new
                output = new FileOutputStream(file);

                props.setProperty(firstAddedSpecialistName, staffId);
                // fileOut = new FileOutputStream(file);
                props.store(output, null);
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Issue("#CLD-025; CLD-027; CLD-028; CLD-026")
    @Test
    public void creating_new_account_as_business_real_test_data() {

        int categoryId = Categories.get(ConfigUtils.fromStringToConstant(ConfigUtils.removeAccents(businessCategory)));

        System.out.println("ID " + categoryId);

        Response newUserResponse = restSteps.registerNewUser(Integer.toString(categoryId), businessName, businessEmail,
                businessPhoneNo);
        System.out.print("register new user response: " + newUserResponse.prettyPrint());
        Response createAccountResponse = restSteps.createAccount(businessName, businessPhoneNo,
                Integer.toString(categoryId), businessEmail, businessPassword);
        System.out.print("create account response: " + createAccountResponse.prettyPrint());

        Response loginResponse = restSteps.login(businessEmail, businessPassword);
        System.out.print("login response: " + loginResponse.prettyPrint());
        Cookies cck = loginResponse.getDetailedCookies();
        /**
         * set coockies for all requests made from RestSteps
         */
        AbstractApiSteps.setupRequestSpecBuilder(cck);


        //get list of all region id's
        Response regionResponse = restSteps.addRegion();
        System.out.print("region response: " + regionResponse.prettyPrint());
        List<String> regionIdsList = regionResponse.body().jsonPath().get("regions.id");
        List<String> regionNamesList = regionResponse.body().jsonPath().get("regions.name");
        List<RegionModel> regionMapData = new ArrayList<>();
        regionMapData = RegionModel.createRegionModelFrom(regionNamesList, regionIdsList);
        Optional<RegionModel> businessRegionModel = regionMapData.stream().filter(p -> ConfigUtils.removeAccents(p.getName()).equalsIgnoreCase(businessMainLocationCounty)).findFirst();
        System.out.println("from excel region_id " + businessRegionModel.get().getId());

        /**
         * add region and city
         */

        Response addCountyResponse = restSteps.addCounty(businessRegionModel.get().getId());
        System.out.print("add county response: " + addCountyResponse.prettyPrint());
        /**
         * get cities list as object with names and ids from add county response
         */
        List<String> citiesNamesList = addCountyResponse.body().jsonPath().get("cities.name");
        List<String> citiesIdsList = addCountyResponse.body().jsonPath().get("cities.id");
        List<CityModel> mapData = new ArrayList<CityModel>();
        mapData = CityModel.createCityModelFrom(citiesNamesList, citiesIdsList);
        //  mapData.stream().forEach(k -> System.out.println("city name" + k.getName() + "city id " + k.getId()));
        Optional<CityModel> businessCityModel = mapData.stream().filter(p -> ConfigUtils.removeAccents(p.getName()).equalsIgnoreCase(businessMainLocationCity)).findFirst();
        //end

        System.out.println("from excel city_id " + businessCityModel.get().getId());
        /**
         * add location
         */
        String locationContent = createJsonObjectForLocationRequestPayload(locationScheduleMon, locationScheduleTue, locationScheduleWed, locationScheduleThu, locationScheduleFri, locationScheduleSat, locationScheduleSun, businessAddress, businessCityModel.get().getId(), businessCityModel.get().getName(), businessRegionModel.get().getId(), businessPhoneNo, businessMainLocation);
        Response addLocationResponse = restSteps.addLocationParameterized(locationContent.toString());
        System.out.print("add location response: " + addLocationResponse.prettyPrint());
        // // Response addLocationResponse = restSteps
        businessLocationId = addLocationResponse.body().jsonPath().get("id");
        System.out.println("business location id " + businessLocationId);
        /**
         * add domain for location
         */
        Response adDdomainResponse = restSteps.addDomain(businessLocationId, businessMainDomain);
        System.out.print("add domain response: " + adDdomainResponse.prettyPrint());
        businessDomainId = adDdomainResponse.body().jsonPath().getString("id");
        System.out.println("domain id " + businessDomainId);

        /**
         * add first service - first line in excel file
         */
        String serviceContent = createJsonObjectForServicePostRequestPayload(businessLocationId, businessDomainId, businessFirstService,
                businessFirstServiceDuration, firstServiceMaxPersons, businessFirstServicePrice);
        Response addServiceResponse = restSteps.addService(serviceContent);
        System.out.print("add service response: " + addServiceResponse.prettyPrint());
        serviceId = addServiceResponse.body().jsonPath().get("id");
        /**
         * add first specialist
         */
        String userContent = createJsonObjectForUserPostRequestPayload(staffScheduleMon, staffScheduleTue, staffScheduleWed, staffScheduleThu, staffScheduleFri, staffScheduleSat, staffScheduleSun, firstAddedSpecialistEmail, firstAddedSpecialistName, firstAddedSpecialistPhone,
                StaffType.EMPL.toString(), serviceId, businessDomainId, businessLocationId);
        System.out.println("content " + userContent);
        Response addStaffResponse = restSteps.addStaff(userContent);
        System.out.print("add staff response: " + addStaffResponse.prettyPrint());
        staffId = addStaffResponse.body().jsonPath().get("user_id");
        String userScheduleContent = createJsonObjectForUserSchedulePostRequestPayload(staffScheduleMon, staffScheduleTue, staffScheduleWed, staffScheduleThu, staffScheduleFri,staffScheduleSat, staffScheduleSun,Integer.parseInt(businessLocationId), Integer.parseInt(staffId));
        Response addStaffScheduleResponse = restSteps.addStaffSchedule(userScheduleContent);
        restSteps.assertAll();

    }


}
