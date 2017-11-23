package ro.evozon.features.business.datadriven.api;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.AbstractApiSteps;
import ro.evozon.tests.BaseApiTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.enums.StaffType;
import ro.evozon.tools.models.Staff;
import ro.evozon.tools.utils.fileutils.CSVUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static ro.evozon.features.business.datadriven.api.DataPersistence.StaffDataPersistence.processInputFile;
import static ro.evozon.tools.api.PayloadDataGenerator.createJsonObjectForUserPostRequestPayload;
import static ro.evozon.tools.api.PayloadDataGenerator.createJsonObjectForUserReceptionistPostRequestPayload;
import static ro.evozon.tools.api.PayloadDataGenerator.createJsonObjectForUserSchedulePostRequestPayload;


@Narrative(text = {"In order to add new service to business account", "As business user ",
        "I want to be able to add new service and then see service is properly saved"})
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/output/datadriven/api/receptie.csv")
public class AddNewReceptionistDataDrivenAPIStory extends BaseApiTest {
    private String numeReceptionist;
    private String emailReceptionist;

    public void setNumeReceptionis(String numeReceptionis) {
        this.numeReceptionist = numeReceptionis;
    }

    public void setEmailReceptionist(String emailReceptionist) {
        this.emailReceptionist = emailReceptionist;
    }

    public void setTelefonReceptionist(String telefonReceptionist) {
        this.telefonReceptionist = telefonReceptionist;
    }





    private String telefonReceptionist;

    public static List<Staff> allStaffList;
    private static int noOfRuns;
    private static String csvFile;
    private static FileWriter writer;
    public String staffId;


    @Qualifier
    public String qualifier() {
        return numeReceptionist + "=>" + emailReceptionist + "=>" + telefonReceptionist ;
    }





    /**
     * intialization block
     * this run once for each data driven iteration
     * static for not being attached to any object
     */
    static {

        allStaffList = processInputFile(Constants.OUTPUT_PATH_DATA_DRIVEN_API + ConfigUtils.getOutputFileNameForStaff());
        System.out.println("angajati size "+allStaffList.size());
        noOfRuns = CSVUtils.getFileNoOfRows(Constants.OUTPUT_PATH_DATA_DRIVEN_API + ConfigUtils.getOutputFileNameForService()) - 1; //-1 for headers row
    }

    public AddNewReceptionistDataDrivenAPIStory() {
        super();

    }




    @Issue("#CLD-040")
    @Test
    public void add_new_receptionist_then_verify_saved()  {
        Cookies cck = businessLogin(businessEmail, businessPassword);
        AbstractApiSteps.setupRequestSpecBuilder(cck);
        /**
         * add  service -all lines in csv file
         */
        System.out.println(numeReceptionist + "=>" + emailReceptionist + "=>" + telefonReceptionist );
        /**
         * add  specialist
         */
        String userContent = createJsonObjectForUserReceptionistPostRequestPayload( emailReceptionist, numeReceptionist, telefonReceptionist,
                StaffType.REC.toString());
        System.out.println("content " + userContent);
        Response addStaffResponse = restSteps.addStaff(userContent);
        System.out.print("add staff response: " + addStaffResponse.prettyPrint());
        restSteps.assertAll();
    }


}