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


@Narrative(text = {"In order to add new service to business account", "As business user ",
        "I want to be able to add new service and then see service is properly saved"})
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/output/datadriven/api/angajati.csv")
public class AddNewStaffDataDrivenAPIStory extends BaseApiTest {
    private String numeAngajat;
    private String emailAngajat;

    public void setNumeAngajat(String numeAngajat) {
        this.numeAngajat = numeAngajat;
    }

    public void setEmailAngajat(String emailAngajat) {
        this.emailAngajat = emailAngajat;
    }

    public void setTelefonAngajat(String telefonAngajat) {
        this.telefonAngajat = telefonAngajat;
    }

    public void setLuni(String luni) {
        this.luni = luni;
    }

    public void setMarti(String marti) {
        this.marti = marti;
    }

    public void setMiercuri(String miercuri) {
        this.miercuri = miercuri;
    }

    public void setJoi(String joi) {
        this.joi = joi;
    }

    public void setVineri(String vineri) {
        this.vineri = vineri;
    }

    public void setSambata(String sambata) {
        this.sambata = sambata;
    }

    public void setDuminica(String duminica) {
        this.duminica = duminica;
    }

    public void setServiciuAsignat(String serviciuAsignat) {
        this.serviciuAsignat = serviciuAsignat;
    }

    public void setServiciuId(String serviciuId) {
        this.serviciuId = serviciuId;
    }

    public void setDomeniuId(String domeniuId) {
        this.domeniuId = domeniuId;
    }

    public void setLocatieId(String locatieId) {
        this.locatieId = locatieId;
    }

    private String telefonAngajat;
    private String luni, marti, miercuri, joi, vineri, sambata, duminica;
    private String serviciuAsignat,serviciuId,domeniuId,locatieId;
    public static List<Staff> allStaffList;
    private static int noOfRuns;
    private static String csvFile;
    private static FileWriter writer;
    public String staffId;


    @Qualifier
    public String qualifier() {
        return numeAngajat + "=>" + emailAngajat + "=>" + telefonAngajat + "=>" + serviciuAsignat + "=>" +serviciuId + "=>" +domeniuId+"=>"+ locatieId;
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

    public AddNewStaffDataDrivenAPIStory() {
        super();

    }



        @After
    public void appendStaffId() {
        noOfRuns = noOfRuns - 1;
        String line = "";
        List<String> lineValuesList;

        /**
         * at last run write to csv file
         */
        System.out.println("now no of runs is " + noOfRuns);
        if (noOfRuns == 0) {
            //List<Location> locationL = myList.stream().filter(l->l.getNumeLocatie())

            List<String> headingList = new ArrayList<String>(Arrays.asList("Nume angajat", "Email angajat", "Telefon angajat", "Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica", "Serviciu asignat", "Serviciu id", "Domeniu id","Locatie id","Staff id"));
            CsvObject<Staff> myServiceObj = new CsvObject<Staff>(allStaffList);
            String csvFile = Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForStaff();
            try {
                writer = new FileWriter(csvFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            myServiceObj.writeToCsvFile(writer, headingList,allStaffList );


        }
            /**
             * add user name and user id as key value pairs in properties file
             */
            FileOutputStream fileOut = null;
            FileInputStream writer = null;
            try {

                String fileName = Constants.OUTPUT_PATH + ConfigUtils.getOutputFileNameForUsersIds();
                Properties props = new Properties();
                File file = new File(fileName);
                writer = new FileInputStream(file);
                props.load(writer);
                props.setProperty("userName", numeAngajat);
                props.setProperty("UserId", staffId);
                fileOut = new FileOutputStream(file);
                props.store(fileOut, " user name and their respective id ");
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

    }

    @Issue("#CLD-040")
    @Test
    public void add_new_staff_then_verify_saved()  {
        Cookies cck = businessLogin(businessEmail, businessPassword);
        AbstractApiSteps.setupRequestSpecBuilder(cck);
        /**
         * add  service -all lines in csv file
         */
        System.out.println(numeAngajat + "=>" + emailAngajat + "=>" + telefonAngajat + "=>" + serviciuAsignat + "=>" +serviciuId + "=>" +domeniuId+"=>"+ locatieId);
        /**
         * add  specialist
         */
        String userContent = createJsonObjectForUserPostRequestPayload(luni, marti, miercuri, joi, vineri, sambata, duminica, emailAngajat, numeAngajat, telefonAngajat,
                StaffType.EMPL.toString(), serviciuId, domeniuId, locatieId);
        System.out.println("content " + userContent);
        Response addStaffResponse = restSteps.addStaff(userContent);
        System.out.print("add staff response: " + addStaffResponse.prettyPrint());
        staffId = addStaffResponse.body().jsonPath().get("user_id");
        //myServicesList.forEach(f -> { if (f.getServiceName().contentEquals(serviciu)) {myStaffList.forEach(k->{ if (k.getServiciuAsignat().contentEquals(serviciu)) {k.setServiciuId(serviceId); k.setDomeniuId(f.getDomainId());k.setLocatieId();});}});
        allStaffList.forEach(f -> {
            if (f.getNumeAngajat().contentEquals(numeAngajat))
                {
                    f.setStaffId(staffId);
                     System.out.println("set staff id for " + numeAngajat + " being " + staffId);

                 }
            if (f.getNumeAngajat().contentEquals(businessFirstAddedSpecialist))
                {
                    f.setStaffId(businessFirstAddedSpecialistId);

                }
        });
        restSteps.assertAll();
    }


}