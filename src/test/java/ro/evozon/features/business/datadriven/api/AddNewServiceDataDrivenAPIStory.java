package ro.evozon.features.business.datadriven.api;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.evozon.steps.serenity.business.*;
import ro.evozon.tests.BaseApiTest;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.models.Service;
import ro.evozon.tools.models.Staff;
import ro.evozon.tools.utils.fileutils.CSVUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ro.evozon.features.business.datadriven.api.DataPersistence.ServiceDataPersistence.processServiceInputFile;
import static ro.evozon.features.business.datadriven.api.DataPersistence.StaffDataPersistence.processInputFile;
import static ro.evozon.tools.api.PayloadDataGenerator.createJsonObjectForServicePostRequestPayload;


@Narrative(text = {"In order to add new service to business account", "As business user ",
        "I want to be able to add new service and then see service is properly saved"})
@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/output/datadriven/api/servicii.csv")
public class AddNewServiceDataDrivenAPIStory extends BaseApiTest {
    private String domeniulAsociat;
    private String serviciu;
    private String durataServiciu;
    private String pretServiciu;
    private String persoaneServiciu;
    private String domeniuId;
    public String locatieId;
    public static List<Staff> myStaffList;
    public static List<Service> allServicesList;
    private static int noOfRuns;
    private static String csvFile;
    private static FileWriter writer;



    @Qualifier
    public String qualifier() {
        return domeniulAsociat + "=>" + serviciu + "=>" + durataServiciu + "=>" + pretServiciu + "=>" +persoaneServiciu + "=>" +domeniuId+"=>"+ locatieId;
    }

    public void setDomeniulAsociat(String domeniulAsociat) {
        this.domeniulAsociat = domeniulAsociat;
    }

    public void setServiciu(String serviciu) {
        this.serviciu = serviciu;
    }

    public void setDurataServiciu(String durataServiciu) {
        this.durataServiciu = durataServiciu;
    }

    public void setPretServiciu(String pretServiciu) {
        this.pretServiciu = pretServiciu;
    }

    public void setPersoaneServiciu(String persoaneServiciu) {
        this.persoaneServiciu = persoaneServiciu;
    }

    public void setDomeniuId(String domeniuId) {
        this.domeniuId = domeniuId;
    }
    public void setLocatieId(String locatieId) {
        this.locatieId = locatieId;
    }



    /**
     * intialization block
     * this run once for each data driven iteration
     * static for not being attached to any object
     */
    static {

        myStaffList = processInputFile(Constants.OUTPUT_PATH_DATA_DRIVEN_API + ConfigUtils.getOutputFileNameForStaff());
        System.out.println("angajati size "+myStaffList.size());
        allServicesList=processServiceInputFile(Constants.OUTPUT_PATH_DATA_DRIVEN_API + ConfigUtils.getOutputFileNameForService());
        System.out.println("size is " + myStaffList.size());
        noOfRuns = CSVUtils.getFileNoOfRows(Constants.OUTPUT_PATH_DATA_DRIVEN_API + ConfigUtils.getOutputFileNameForService()) - 1; //-1 for headers row
    }

    public AddNewServiceDataDrivenAPIStory() {
        super();

    }



    @After
    public void appendServiceId() {
        noOfRuns = noOfRuns - 1;
        String line = "";
        List<String> lineValuesList;

        /**
         * at last run write to csv file
         */
        System.out.println("now no of runs is " + noOfRuns);
        if (noOfRuns == 0) {
            //List<Location> locationL = myList.stream().filter(l->l.getNumeLocatie())

            List<String> headingList = new ArrayList<String>(Arrays.asList("Nume angajat", "Email angajat", "Telefon angajat", "Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica", "Serviciu asignat", "Serviciu id", "Domeniu id","Locatie id"));
            CsvObject<Staff> myServiceObj = new CsvObject<Staff>(myStaffList);
            String csvFile = Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForStaff();
            try {
                writer = new FileWriter(csvFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            myServiceObj.writeToCsvFile(writer, headingList,myStaffList );


        }

    }

    @Steps
    public LoginBusinessAccountSteps loginStep;

    @Steps
    public AddItemToBusinessSteps addItemToBusinessSteps;
    @Steps
    public AddServiceToBusinessStep addServiceStep;
    @Steps
    BusinessWizardSteps businessWizardSteps;
    @Steps
    public AddStaffToBusinessStep addSpecialitsSteps;
    @Steps
    public AddAppointmentToBusinessStep addAppointmentToBusinessStep;

    @Issue("#CLD-040")
    @Test
    public void add_new_service_then_verify_saved()  {
        Cookies cck = businessLogin(businessEmail, businessPassword);
        restSteps.setupRequestSpecBuilder(cck);
        /**
         * add  service -all lines in csv file
         */
        System.out.println(domeniulAsociat + "=>" + serviciu + "=>" + durataServiciu + "=>" + pretServiciu + "=>" + persoaneServiciu + "=>" +domeniuId+"=>"+ locatieId);
        String serviceContent = createJsonObjectForServicePostRequestPayload(locatieId, domeniuId, serviciu,
                durataServiciu, persoaneServiciu, pretServiciu);
        Response addServiceResponse = restSteps.addService(serviceContent);
        System.out.print("add service response: " + addServiceResponse.prettyPrint());
        String serviceId = addServiceResponse.body().jsonPath().get("id");
        //myServicesList.forEach(f -> { if (f.getServiceName().contentEquals(serviciu)) {myStaffList.forEach(k->{ if (k.getServiciuAsignat().contentEquals(serviciu)) {k.setServiciuId(serviceId); k.setDomeniuId(f.getDomainId());k.setLocatieId();});}});
        myStaffList.forEach(f -> {
            if (f.getServiciuAsignat().contentEquals(serviciu))
                {
                    f.setServiciuId(serviceId);
                     System.out.println("set service id for " + serviciu + " being " + serviceId);
                    allServicesList.forEach(k ->
                    {
                        if (k.getServiceName().contentEquals(serviciu))
                        {
                            System.out.println("set location id for "+serviciu+" with "+k.getLocationId() );
                            System.out.println("set domain id for "+serviciu+" with "+k.getDomainId() );
                            f.setLocatieId((k.getLocationId()));
                            f.setDomeniuId(k.getDomainId());
                        }
                    });
                 }
            if (f.getServiciuAsignat().contentEquals(businessFirstService))
                {
                    f.setServiciuId(businessFirstServiceId);
                    allServicesList.forEach(k ->
                    {
                        if (k.getServiceName().contentEquals(businessFirstService))
                        {
                            f.setLocatieId(businessMainLocationId);
                            f.setDomeniuId(businessDomainId);
                        }
                    });
                }
        });
        addServiceStep.assertAll();
    }


}